package com.example.hp.movies.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hp.movies.R;
import com.example.hp.movies.activity.MainActivity;

/**
 * Created by rahul on 22/02/16.
 */
public class MovieDetailsFragment extends Fragment {



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // return super.onCreateView(inflater, container, savedInstanceState);

      View view=inflater.inflate(R.layout.movie_details_fragment,container,false);



        (( MainActivity) getActivity()).getSupportActionBar().setTitle("MovieDetails");
        return  view;
    }
}
