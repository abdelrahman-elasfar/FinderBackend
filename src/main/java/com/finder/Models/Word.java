package com.finder.prod.Models;


import java.util.ArrayList;

import com.finder.prod.Models.Webpage;

import org.springframework.data.annotation.Id;


public class Word {  
    @Id  
    private String _id;

    private int df;
    
    private String word;

    private ArrayList<Webpage> metadata;


    public Word(String _id, int df, String word, ArrayList<Webpage> metadata){
        this._id = _id;
        this.df = df;
        this.word = word;
        this.metadata = metadata;
    }
    
    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getDf() {
        return df;
    }

    public void setWord(int df) {
        this.df = df;
    }

    public ArrayList<Webpage> getMetadata() {
        return metadata;
    }

    public void addMetadata(Webpage website) {
        this.metadata.add(website);
    }


    @Override
    public String toString() {
        return String.format(
            "Word[id=%s, word='%s', links='%s']",
            _id, word, metadata.get(0).url);
    }

}

