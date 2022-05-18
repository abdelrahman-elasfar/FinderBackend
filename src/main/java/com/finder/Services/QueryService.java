package com.finder.prod.Services;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.finder.prod.Models.Suggestion;
import com.finder.prod.Models.Webpage;
import com.finder.prod.Models.Website;
import com.finder.prod.Models.Word;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import opennlp.tools.stemmer.PorterStemmer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class QueryService {

    @Autowired
    private MongoOperations mongoOperations;

    public ResponseEntity<?> getAllWebsitesByQuery(String query, int pageSize, int pageNum){


        MongoClient mongoClient = MongoClients.create("mongodb+srv://admin:admin@searchengine.g39y2.mongodb.net/SearchEngine?retryWrites=true&w=majority");
        MongoDatabase db = mongoClient.getDatabase("SearchEngine");
        MongoCollection<Document> suggestionsCollection = db.getCollection("Suggestions");
        Document updateResult = suggestionsCollection.findOneAndUpdate(Filters.eq("query", query), Updates.inc("frequency", 1));
        if(updateResult==null){
              
            Document Doc = new Document();
        
            Doc.put("query", query);
            Doc.put("frequency", 1);
            suggestionsCollection.insertOne(Doc);
        }
        List<Word> queryWords = new ArrayList<>();
        String [] rawWords = query.split(" ");
        for (int i = 0 ; i < rawWords.length ; i++) {
            rawWords[i] = Stemmer.stem(rawWords[i]);
            Word word = mongoOperations.findOne( Query.query(Criteria.where("word").is(rawWords[i])), Word.class, "indexer");
            queryWords.add(word);
        }
        MongoCollection<Document> webpagesCollection = db.getCollection("webpages");
        long docCount =  webpagesCollection.countDocuments();


        List<Webpage> websites = Ranker.rank(queryWords, docCount);
        int size = websites.size();

        List<Webpage> websitesPaginated = new ArrayList<>();
           
        // if data size allows for this page number (starting from 0)
        if(pageSize!=0 && size > pageSize*pageNum){
            // if left data is more than page size send only page size
            if( size > pageSize*(pageNum+1)){
                websitesPaginated = websites.subList(pageSize*pageNum, pageSize*pageNum+pageSize);
            }
            // else send all left data
            else{
                websitesPaginated = websites.subList(pageSize*pageNum, size);
            }
        }
           
            if (!websitesPaginated.isEmpty()) {
                return new ResponseEntity<>(websitesPaginated, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.OK);
            }
            
        }


    public ResponseEntity<?> getSuggestions(String query){
        

        List<Suggestion> suggestions = mongoOperations.find( Query.query(Criteria.where("query").regex('^'+query)), Suggestion.class, "Suggestions");
        
        // Sort suggestions by frequency
        Collections.sort(suggestions, new Comparator<Suggestion>() {
            @Override
            public int compare(Suggestion lhs, Suggestion rhs) {
                return lhs.frequency > rhs.frequency ? -1 : (lhs.frequency < rhs.frequency) ? 1 : 0;
            }
        });

        if(!suggestions.isEmpty()){   
            return new ResponseEntity<>(suggestions, HttpStatus.OK);
            
        }else{
            return new ResponseEntity<>(null,HttpStatus.OK);
        }
        
        
       
    }

}

class Stemmer{
    public static String stem(String word){
        PorterStemmer porterStemmer = new PorterStemmer();
        String stem = porterStemmer.stem(word);
        return stem;
    }
}

class Ranker{
    public static ArrayList<Webpage> rank (List<Word> queryWords, long docCount)
    {
        // Create a dictionary with all the urls that where the query words appeared
        for(int i=0; i< queryWords.length; i++) {
            // Create an array that has the documents of each word
            ArrayList<Webpage> webpages = queryWords[i].getMetadata();

            // Calculate IDF for each word
            double df = Double.parseDouble(String.valueOf(queryWords[i].getDf()));
            double idf = Math.log10(docCount/df);
            // double idf = Math.log(docCount/df);

            // Loop over documents and Calculate TF for each word,document
            for(Webpage webpage: webpages){
                // Calculate TF
                double tf = webpage.termFreq;
                double score = webpage.score;

                // Calculate score multiplier based on location of appearance
                double multiplier = 1;
                if(webpage.titleFreq > 0) multiplier *= 3;
                if(webpage.headingsFreq > 0) {
                    multiplier *= (1 + 0.25 * webpage.headingsFreq);
                }

                webpage.score = webpage.score + idf * tf * multiplier;
        }
        return webpages;
    }
}
}