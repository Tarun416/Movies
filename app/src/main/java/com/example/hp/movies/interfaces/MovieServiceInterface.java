package com.example.hp.movies.interfaces;

import com.example.hp.movies.apimodel.MovieDb;
import com.example.hp.movies.apimodel.ReviewModel;
import com.example.hp.movies.apimodel.TrailerModel;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by hp on 20-02-2016.
 */
public interface MovieServiceInterface {

    @GET("/discover/movie")
    void getMovieList(@Query("sort_by") String filterstring,Callback<MovieDb> callback);

    @GET("/movie/{id}/videos")
    void getTrailers(@Path("id") String id, Callback<TrailerModel> callback);

    @GET("/movie/{id}/reviews")
    void getReviews(@Path("id") String id, Callback<ReviewModel> callback);

}
