package com.example.hp.movies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.hp.movies.R;
import com.example.hp.movies.apimodel.Results;
import com.example.hp.movies.generator.NetworkApiGenerator;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by hp on 20-02-2016.
 */
public class MovieGridAdapter extends RecyclerView.Adapter<MovieGridAdapter.ViewHolder> {

    private Results[] results;


    public MovieGridAdapter(Results [] results) {

        this.results=results;

    }

    @Override
    public int getItemCount() {
        return results.length;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
     View v=   LayoutInflater.from(parent.getContext()).inflate( R.layout.movie_grid_item,parent,false);

        ViewHolder vh=new ViewHolder(v,parent.getContext() );
        return  vh;
       // return null;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String url= NetworkApiGenerator.IMAGE_BASE_URL+results[position].getPoster_path();

        Picasso.with(holder.context).load(url).into(holder.poster);



    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        public ImageView poster;
        public Context context;
        public ViewHolder(View itemView, Context context) {
            super(itemView);
            this.context=context;
            poster=(ImageView)itemView.findViewById(R.id.poster);
        }
    }



}
