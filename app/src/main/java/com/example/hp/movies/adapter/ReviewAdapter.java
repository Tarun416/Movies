package com.example.hp.movies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hp.movies.R;
import com.example.hp.movies.apimodel.ReviewResults;

import java.util.List;

/**
 * Created by hp on 09-04-2016.
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private Context mContext;
    private List<ReviewResults> reviewList;

    public ReviewAdapter(Context context,List<ReviewResults> reviewResults)
    {
        this.mContext=context;
        this.reviewList=reviewResults;
    }
    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View grid= LayoutInflater.from(parent.getContext()).inflate(R.layout.review_list_row,parent,false);
        return new ReviewViewHolder(grid,parent.getContext());
    }

    @Override
    public void onBindViewHolder(ReviewAdapter.ReviewViewHolder holder, int position) {

        holder.authorname.setText(reviewList.get(position).getAuthor());
        holder.content.setText(reviewList.get(position).getContent());
        holder.link.setText(reviewList.get(position).getUrl());



    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder
    {
        private Context mContext;
        private TextView authorname;
        private TextView content;
        private TextView link;
        public ReviewViewHolder(View view, Context context)
        {
            super(view);
            authorname=(TextView)  view.findViewById(R.id.authorname);
            content=(TextView)  view.findViewById(R.id.content);
            link=(TextView)  view.findViewById(R.id.link);
            this.mContext=context;

        }
    }
}
