package com.example.hp.movies.fragment;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
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
import android.widget.Button;
import android.widget.ImageButton;
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

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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
    @Bind(R.id.favouritebtn)
    ImageButton favouritebutton;
    @Bind(R.id.kk)
    TextView kannad;


    private MovieServiceInterface movieServiceInterface;
    private TrailerModel mtrailermodel;
    private TrailerAdapter trailerAdapter;
    private List<TrailerResults> mTrailerResults;
    private Results results;
    private ReviewModel mReviewModel;
    private List<ReviewResults> mReviewResults;
    private ReviewAdapter reviewAdapter;
    private LinearLayoutManager linearLayoutManager;
    private String responseString;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // return super.onCreateView(inflater, container, savedInstanceState);

      View view=inflater.inflate(R.layout.movie_details_fragment, container, false);
        ButterKnife.bind(this, view);

        //Typeface customfont=Typeface.createFromAsset(getActivity().getAssets(), "fonts/Lohit-Kannada.ttf");
        //kannad.setTypeface(customfont);

        String text=Translate("Tarun", "kn");
        kannad.setText(text);




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




        favouritebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onToggleStar(v);
            }
        });







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



    public void onToggleStar(View v)
    {
        boolean isFavourite=readState();

        if(isFavourite)
        {
            favouritebutton.setImageResource(R.drawable.ic_favorite_selected);
            isFavourite=false;
            saveState(isFavourite);

        }
        else
        {
            favouritebutton.setImageResource(R.drawable.ic_favorite_normal);
            isFavourite=true;
            saveState(isFavourite);

        }

    }

    private void saveState(boolean isFavourite) {
        SharedPreferences sp=getActivity().getSharedPreferences("Favourite", Context.MODE_PRIVATE);
        SharedPreferences.Editor spe= sp.edit();
        spe.putBoolean("State", isFavourite);
        spe.commit();


    }

    private boolean readState() {
        SharedPreferences sp=  getActivity().getSharedPreferences("Favourite", Context.MODE_PRIVATE);
        return sp.getBoolean("State", true);

    }


    private String Translate(String text, String l)
    {

        try {
            URL url=new URL("http://translate.google.com/translate_s?hl=en&clss=&q=" +
                    text.replace(" ", "+") + "&tq=&sl=en&tl=" + l);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
      //  "http://translate.google.com/translate_s?hl=en&clss=&q=" +
              //  text.replace(" ", "+") + "&tq=&sl=en&tl=" + l
      //  https://translate.google.com/#auto/kn/tarun
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response = null;
        try {
            response = httpclient.execute(new HttpGet( "https://translate.google.com/#auto/kn/tarun"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        StatusLine statusLine = response.getStatusLine();
        if(statusLine.getStatusCode() == HttpStatus.SC_OK){
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try {
                response.getEntity().writeTo(out);
            } catch (IOException e) {
                e.printStackTrace();
            }
             responseString = out.toString();
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //..more logic
        } else{
            //Closes the connection.
            try {
                response.getEntity().getContent().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                throw new IOException(statusLine.getReasonPhrase());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String translated=null;
        int rawlength1 = responseString.indexOf("<span id=otq><b>");
        String rawStr1 = responseString.substring(rawlength1);
        int rawlength2 = rawStr1.indexOf("</b>");
        String rawstr2 = rawStr1.substring(0, rawlength2);
        translated = rawstr2.replace("<span id=otq><b>", "");

        return translated;







        /*String translated = null;



        HttpWebRequest hwr = (HttpWebRequest)HttpWebRequest.Create
                ("http://translate.google.com/translate_s?hl=en&clss=&q=" +
                        text.replace(" ", "+") + "&tq=&sl=en&tl=" + l);
        HttpWebResponse res = (HttpWebResponse)hwr.GetResponse();
        StreamReader sr = new StreamReader(res.GetResponseStream());
        String html = sr.ReadToEnd();
        int rawlength1 = html.IndexOf("<span id=otq><b>");
        String rawStr1 = html.Substring(rawlength1);
        int rawlength2 = rawStr1.IndexOf("</b>");
        String rawstr2 = rawStr1.substring(0, rawlength2);
        translated = rawstr2.replace("<span id=otq><b>", "");
        tbStringToTranslate.Text = text;
        return translated;*/
    }




}
