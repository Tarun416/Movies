package com.example.hp.movies.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.hp.movies.R;
import com.example.hp.movies.activity.MainActivity;
import com.example.hp.movies.apimodel.Results;
import com.example.hp.movies.generator.NetworkApiGenerator;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by rahul on 22/02/16.
 */
public class MovieDetailsFragment extends Fragment {

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




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // return super.onCreateView(inflater, container, savedInstanceState);

      View view=inflater.inflate(R.layout.movie_details_fragment, container, false);
        ButterKnife.bind(this,view);


                ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);


                ((MainActivity) getActivity()).getSupportActionBar().setTitle("Movie Details");

        Bundle args=getArguments();



       Results results=args.getParcelable("key");
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




        return  view;
    }
}
