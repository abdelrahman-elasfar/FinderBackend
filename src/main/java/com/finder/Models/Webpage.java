package com.finder.prod.Models;

public class Webpage {
 
    public int termFreq;
    public int titleFreq;
    public int textFreq;
    public int headingsFreq;
    public int normalFreq;
    public double score;

    public String url;

    public Webpage(int termFreq, int titleFreq, int textFreq, int headingsFreq, int normalFreq, String url)
    {
        this.termFreq = termFreq;
        this.titleFreq = titleFreq;
        this.textFreq = textFreq;
        this.headingsFreq = headingsFreq;
        this.normalFreq = normalFreq;
        this.url = url;
        this.score = 0.0;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

}
