package com.app.lenovo.summerproject;

public class SuggestionModel {
    public int count;
    public String path;
    public SuggestionModel()
    {

    }
    public SuggestionModel( String p, int c)
    {
        this.count=c+1;
        this.path=p;
    }
}
