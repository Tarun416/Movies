package com.example.hp.movies.apimodel;

import java.util.List;

/**
 * Created by rahul on 06/04/16.
 */
public class TrailerModel
{
    private String id;

    private List<TrailerResults> results;

   // private Results[] results;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public List<TrailerResults> getResults ()
    {
        return results;
    }

    public void setResults (List<TrailerResults> results)
    {
        this.results = results;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", results = "+results+"]";
    }
}

