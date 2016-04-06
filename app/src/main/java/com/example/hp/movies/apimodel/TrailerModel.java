package com.example.hp.movies.apimodel;

/**
 * Created by rahul on 06/04/16.
 */
public class TrailerModel
{
    private String id;

    private Results[] results;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public Results[] getResults ()
    {
        return results;
    }

    public void setResults (Results[] results)
    {
        this.results = results;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", results = "+results+"]";
    }
}

