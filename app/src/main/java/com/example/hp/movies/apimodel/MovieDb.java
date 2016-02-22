package com.example.hp.movies.apimodel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hp on 20-02-2016.
 */
public class MovieDb implements Parcelable {
    private Results[] results;

    private String page;

    private String total_pages;

    private String total_results;

    public Results[] getResults ()
    {
        return results;
    }

    public void setResults (Results[] results)
    {
        this.results = results;
    }

    public String getPage ()
    {
        return page;
    }

    public void setPage (String page)
    {
        this.page = page;
    }

    public String getTotal_pages ()
    {
        return total_pages;
    }

    public void setTotal_pages (String total_pages)
    {
        this.total_pages = total_pages;
    }

    public String getTotal_results ()
    {
        return total_results;
    }

    public void setTotal_results (String total_results)
    {
        this.total_results = total_results;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [results = "+results+", page = "+page+", total_pages = "+total_pages+", total_results = "+total_results+"]";
    }

    protected MovieDb(Parcel in) {
        page = in.readString();
        total_pages = in.readString();
        total_results = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(page);
        dest.writeString(total_pages);
        dest.writeString(total_results);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<MovieDb> CREATOR = new Parcelable.Creator<MovieDb>() {
        @Override
        public MovieDb createFromParcel(Parcel in) {
            return new MovieDb(in);
        }

        @Override
        public MovieDb[] newArray(int size) {
            return new MovieDb[size];
        }
    };
}