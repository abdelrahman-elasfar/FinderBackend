package com.finder.Controllers;

import com.finder.prod.Services.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class WordController {

    private WordService wordService;

    @Autowired
    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{word}")
    public ResponseEntity<?> getAllReservations(@PathVariable("word") String word, @RequestParam("pageSize") int pageSize, @RequestParam("pageNum") int pageNum) {
        System.out.println("PAGE NUMBER : "+pageNum);
        System.out.println("PAGE SIZE : "+pageSize);
        word = word.toLowerCase();
        
        return wordService.getAllWebsitesByWord(word, pageSize, pageNum);       

    }


    @RequestMapping(method = RequestMethod.GET, value = "/suggestions/{word}")
    public ResponseEntity<?> getSuggestions(@PathVariable("word") String word) {
        word = word.toLowerCase();
        return wordService.getSuggestions(word);       

    }

}

