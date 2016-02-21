package com.example.hp.movies.interfaces;

import com.example.hp.movies.apimodel.MovieDb;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by hp on 20-02-2016.
 */
public interface MovieServiceInterface {

    @GET("/discover/movie")
    void getMovieList(@Query("sort_by") String filterstring,Callback<MovieDb> callback);

}