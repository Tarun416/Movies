package com.example.hp.movies.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.hp.movies.R;
import com.example.hp.movies.activity.MainActivity;
import com.example.hp.movies.adapter.ReviewAdapter;
import com.example.hp.movies.adapter.TrailerAdapter;
import com.example.hp.movies.apimodel.Results;
import com.example.hp.movies.apimodel.ReviewModel;
import com.example.hp.movies.apimodel.ReviewResults;
import com.example.hp.movies.apimodel.TrailerModel;
import com.example.hp.movies.apimodel.TrailerResults;
import com.example.hp.movies.generator.NetworkApiGenerator;
import com.example.hp.movies.interfaces.MovieServiceInterface;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * Created by rahul on 22/02/16.
 */
public class MovieDetailsFragment extends Fragment {

    @Bind(R.id.noreview)
    TextView noreview;

    @Bind(R.id.image)
    ImageView iamge;
    @Bind(R.id.movietitle)
    TextView tv;
    @Bind(R.id.rating)
    TextView rating;
    @Bind(R.id.ratingbar)
    RatingBar ratingBar;
    @Bind(R.id.releasedate)
    TextView releasedate;
    @Bind(R.id.over)
    TextView overview;
    @Bind(R.id.movieImageView)
    ImageView mMovieImageView;
    @Bind(R.id.trailerslist)
    RecyclerView trailerslist;
    @Bind(R.id.reviewslist)
    RecyclerView reviewList;
    @Bind(R.id.notrailertext)
    TextView notrailertext;


    private MovieServiceInterface movieServiceInterface;
    private TrailerModel mtrailermodel;
    private TrailerAdapter trailerAdapter;
    private List<TrailerResults> mTrailerResults;
    private Results results;
    private ReviewModel mReviewModel;
    private List<ReviewResults> mReviewResults;
    private ReviewAdapter reviewAdapter;
    private LinearLayoutManager linearLayoutManager;






    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // return super.onCreateView(inflater, container, savedInstanceState);

      View view=inflater.inflate(R.layout.movie_details_fragment, container, false);
        ButterKnife.bind(this, view);


            //    ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);


                ((MainActivity) getActivity()).getSupportActionBar().setTitle("Movie Details");

        Bundle args=getArguments();



        results=args.getParcelable("key");
        String url=NetworkApiGenerator.IMAGE_BASE_URL+results.getPoster_path();
        Picasso.with(getContext()).load(url).into(iamge);
        Picasso.with(getActivity()).load(NetworkApiGenerator.IMAGE_BASE_URL + results.getBackdrop_path())
                .fit()
                .centerCrop()
                .placeholder(ContextCompat.getDrawable(getActivity(), android.R.color.holo_blue_dark))
                .error(ContextCompat.getDrawable(getActivity(), android.R.color.holo_red_dark))
                .into(mMovieImageView);


        tv.setText(results.getOriginal_title());
        rating.setText(results.getVote_average() + "/" + "10");

        float d=Float.parseFloat(results.getVote_average());
//        ratingBar.setNumStars(Integer.parseInt(results.getVote_average()));
        ratingBar.setRating(d);
        releasedate.setText(results.getRelease_date());
        overview.setText(results.getOverview());


        movieServiceInterface=NetworkApiGenerator.createService(MovieServiceInterface.class);
        movieServiceInterface.getTrailers(results.getId(), new retrofit.Callback<TrailerModel>() {
            @Override
            public void success(TrailerModel trailerModel,Response response) {

                notrailertext.setVisibility(View.GONE);
                trailerslist.setVisibility(View.VISIBLE);

                if(trailerModel.getResults().size()==0)
                {
                    trailerslist.setVisibility(View.GONE);
                    notrailertext.setVisibility(View.VISIBLE);


                }

                mtrailermodel = trailerModel;
                mTrailerResults = trailerModel.getResults();
                Log.d("trailerimages", mtrailermodel.toString());
                Log.d("ff", mtrailermodel.getId().toString());
                trailerAdapter = new TrailerAdapter(getActivity(), mTrailerResults, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                        watchYoutubeVideo(mTrailerResults.get(position).getKey());


                    }
                });
                linearLayoutManager=new org.solovyev.android.views.llm.LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
            //    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                trailerslist.setLayoutManager(linearLayoutManager);
                trailerslist.setAdapter(trailerAdapter);


            }




            @Override
            public void failure(RetrofitError error) {

            }});





           getReviews();







        return  view;
    }

    private void watchYoutubeVideo(String key) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + key));
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + key));
            startActivity(intent);
        }
    }


    public void getReviews()
    {
        movieServiceInterface= NetworkApiGenerator.createService(MovieServiceInterface.class);
        movieServiceInterface.getReviews(results.getId(), new Callback<ReviewModel>() {
            @Override
            public void success(ReviewModel reviewModel, Response response) {
                mReviewModel=reviewModel;

                mReviewResults= mReviewModel.getResults();
                if(mReviewResults.size()!=0) {
                    noreview.setVisibility(View.GONE);
                    reviewAdapter = new ReviewAdapter(getActivity(), mReviewResults);
                }
                else
                {

                    noreview.setVisibility(View.VISIBLE);
                }

             //   linearLayoutManager=new org.solovyev.android.views.llm.LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                linearLayoutManager=new org.solovyev.android.views.llm.LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);






                reviewList.setLayoutManager(linearLayoutManager);
                reviewList.setAdapter(reviewAdapter);
             //   reviewList.setNestedScrollingEnabled(false);










            }

            @Override
            public void failure(RetrofitError error) {
              //  noreview.setVisibility(View.VISIBLE);

            }
        });
    }








}
