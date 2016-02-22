package com.example.hp.movies.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // return super.onCreateView(inflater, container, savedInstanceState);

      View view=inflater.inflate(R.layout.movie_details_fragment, container, false);
        ButterKnife.bind(this,view);


                ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);


                ((MainActivity) getActivity()).getSupportActionBar().setTitle("MovieDetails");

        Bundle args=getArguments();



       Results results=args.getParcelable("key");
        String url=NetworkApiGenerator.IMAGE_BASE_URL+results.getPoster_path();
        Picasso.with(getContext()).load(url).into(iamge);




        return  view;
    }
}
