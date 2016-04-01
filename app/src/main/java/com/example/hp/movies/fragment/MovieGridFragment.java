package com.example.hp.movies.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hp.movies.R;
import com.example.hp.movies.activity.MainActivity;
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
    @Bind(R.id.txtLayout)
    RelativeLayout mtext;

    private GridLayoutManager mGridLayoutManager;
    private MovieServiceInterface mMovieServiceInterface;
    private MovieGridAdapter mMovieGridAdapter;
    private Results[] results;
    private String pfilteredString="popularity.desc";
    private String hfilteredString="vote_average.desc";



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //  return super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.movie_grid_fragment, container, false);
        ButterKnife.bind(this, root);
        setHasOptionsMenu(true);
        (( MainActivity)getActivity()).getSupportActionBar().setTitle("Movies");
     //   (( MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);


        mGridLayoutManager = new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.gridcolumn));
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        getData(pfilteredString);
        return root;
    }
        //  mRecyclerView.setAdapter(mMovieGridAdapter);

    public void getData(String filteredString) {
        //results=null;

        mMovieServiceInterface = NetworkApiGenerator.createService(MovieServiceInterface.class);

        mMovieServiceInterface.getMovieList(filteredString, new retrofit.Callback<MovieDb>() {
            @Override
            public void success(MovieDb movieDb, Response response) {
                mRecyclerView.setVisibility(View.VISIBLE);
                mtext.setVisibility(View.GONE);
                mProgressLayout.setVisibility(View.GONE);
                Log.d("aaa", movieDb.toString());
                results = movieDb.getResults();

                mMovieGridAdapter = new MovieGridAdapter(getActivity(),results);
                mRecyclerView.setAdapter(mMovieGridAdapter);
            }

            @Override
            public void failure(RetrofitError error) {

                Log.d("lll", error.toString());


                mProgressLayout.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.GONE);
                mtext.setVisibility(View.VISIBLE);

            }
        });


        // return root;
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_main,menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();

        if(id==R.id.most_popular)
        {
            results=null;
            if(mMovieGridAdapter!=null) {
                mMovieGridAdapter.notifyDataSetChanged();
            }
            mProgressLayout.setVisibility(View.VISIBLE);

            getData(pfilteredString);
        }
        if(id==R.id.highest_rated)
        {
            results=null;
            if(mMovieGridAdapter!=null) {
                mMovieGridAdapter.notifyDataSetChanged();
            }
            mProgressLayout.setVisibility(View.VISIBLE);
            getData(hfilteredString);
        }

        return true;
       // return super.onOptionsItemSelected(item);
    }
}




