package com.example.hp.movies.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hp.movies.R;
import com.example.hp.movies.apimodel.TrailerModel;
import com.example.hp.movies.apimodel.TrailerResults;
import com.example.hp.movies.generator.NetworkApiGenerator;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by rahul on 07/04/16.
 */
public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    private Context mContext;
    private List<TrailerResults> mTrailerResults;
    private AdapterView.OnItemClickListener mOnItemClickListenerr;



    public TrailerAdapter(Context context, List<TrailerResults> results , AdapterView.OnItemClickListener mOnItemClickListener)
    {
        this.mContext=context;
       this. mTrailerResults=results;
        this.mOnItemClickListenerr=mOnItemClickListener;
     //   mOnItemClickListener=mOnItemClickListene;

    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

      View grid=  LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_list_row,parent,false);
        return new TrailerViewHolder(grid,parent.getContext());
       // return null;
    }

    @Override
    public void onBindViewHolder(final TrailerViewHolder holder, int position) {

      String url=  NetworkApiGenerator.YOU_TUBE_TRAILER_BASE_URL+mTrailerResults.get(position).getKey()+"/0.jpg";
        Log.d("trailerurl",url );

        Picasso.with(holder.mcontext)
                .load(url)
                .placeholder(ContextCompat.getDrawable(holder.mcontext, android.R.color.holo_blue_dark))
               .error(ContextCompat.getDrawable(mContext, android.R.color.holo_red_dark))

                .fit()
                .centerCrop()
                .into(holder.trailerPoster/*, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                       holder.trailerPoster.setVisibility(View.GONE);
                        holder.noTrailerText.setVisibility(View.VISIBLE);
                        holder.noTrailerText.setText("No Trailers");
                    }
                }*/);


     //   Picasso.with(getA)






    }

    @Override
    public int getItemCount() {
        return mTrailerResults.size();
    }


    public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private ImageView trailerPoster;
        private Context mcontext;
        private TextView noTrailerText;

        public  TrailerViewHolder(View itemView, Context context)
        {
            super(itemView);
            itemView.setOnClickListener(this);
            this.mcontext=context;
            trailerPoster=(ImageView) itemView.findViewById(R.id.trailerimageview);
            noTrailerText=(TextView) itemView.findViewById(R.id.notrailertext);



        }

        @Override
        public void onClick(View v) {


                 mOnItemClickListenerr.onItemClick(null,v,getAdapterPosition(),0);

          //  mOnItemClickListener.onItemClick(v,getAdapterPosition());


        }
    }


}


