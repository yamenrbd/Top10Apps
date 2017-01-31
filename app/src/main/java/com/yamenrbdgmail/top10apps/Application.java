/**
 * Created by German Center on 26/01/2017.
 */
package com.yamenrbdgmail.top10apps;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Application {
    private String name;
    private String releaseDate;
    private String artist;

    public void setName(String name) {
        this.name = name;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getName() {
        return name;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getArtist() {
        return artist;
    }

    @Override
    public String toString() {
        return "name : "+getName()+"\n"+"artist : "+getArtist()+"\n"+"release date : "+getReleaseDate()+"\n";

    }
}
