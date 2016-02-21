package com.example.hp.movies.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.hp.movies.R;
import com.example.hp.movies.adapter.MovieGridAdapter;
import com.example.hp.movies.apimodel.MovieDb;
import com.example.hp.movies.apimodel.Results;
import com.example.hp.movies.generator.NetworkApiGenerator;
import com.example.hp.movies.interfaces.MovieServiceInterface;

import javax.security.auth.callback.Callback;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * Created by hp on 20-02-2016.
 */
public class MovieGridFragment extends Fragment {

    @Bind(R.id.rv)
    RecyclerView mRecyclerView;
    @Bind(R.id.progressLayout)
    RelativeLayout mProgressLayout;

    private GridLayoutManager mGridLayoutManager;
    private MovieServiceInterface mMovieServiceInterface;
    private MovieGridAdapter mMovieGridAdapter;
    private Results[] results;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      //  return super.onCreateView(inflater, container, savedInstanceState);

      View root=  inflater.inflate(R.layout.movie_grid_fragment,container,false);
        ButterKnife.bind(this,root);
        setHasOptionsMenu(true);

        mGridLayoutManager=new GridLayoutManager(getActivity(),getResources().getInteger(R.integer.gridcolumn));
        mRecyclerView.setLayoutManager(mGridLayoutManager);
      //  mRecyclerView.setAdapter(mMovieGridAdapter);

     mMovieServiceInterface  = NetworkApiGenerator.createService(MovieServiceInterface.class);

        mMovieServiceInterface.getMovieList("popularity.desc", new retrofit.Callback<MovieDb>() {
            @Override
            public void success(MovieDb movieDb, Response response) {
                mProgressLayout.setVisibility(View.GONE);
                Log.d("aaa",movieDb.toString());
               results= movieDb.getResults();
                mMovieGridAdapter=new MovieGridAdapter(results);
                mRecyclerView.setAdapter(mMovieGridAdapter);
            }

            @Override
            public void failure(RetrofitError error) {

                Log.d("lll",error.toString());
                mProgressLayout.setVisibility(View.GONE);

            }
        });


        return root;
    }
}




