package com.finder.prod.Models;

public class Website {
 
    public int termFrequency;
    public int titleFrequency;

    public int plainTextFrequency;

    public int headingsFrequency;

    public String url;

    Website(int termFrequency, int titleFrequency, int plainTextFrequency, int headingsFrequency, String url){
        this.termFrequency = termFrequency;
        this.titleFrequency = titleFrequency;
        this.plainTextFrequency = plainTextFrequency;
        this.headingsFrequency = headingsFrequency;
        this.url = url;
    }

}
