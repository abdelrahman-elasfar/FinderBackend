package com.finder.prod.Models;

public class Suggestion {
    public int frequency;
    public String query;
    
    Suggestion(int frequency, String query){
        this.frequency = frequency;
        this.query = query;
    }
}
