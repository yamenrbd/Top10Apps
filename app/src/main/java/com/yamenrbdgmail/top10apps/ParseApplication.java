
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
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;


/**
 * Created by German Center on 26/01/2017.
 */

public class ParseApplication {
    private String xmlData;
    private ArrayList<Application> applications;

    public ParseApplication(String xmlData) {
        this.xmlData = xmlData;
        applications = new ArrayList<>();
    }

    public ArrayList<Application> getApplications() {
        return applications;
    }

    public boolean process() {
        boolean status = true;
        boolean inEntry = false;
        String textValue = "";
        Application currentRecord =null;

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(this.xmlData));
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = xpp.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                      //  Log.d("ParserAoolication ", "starting tag for " + tagName);
                        if (tagName.equalsIgnoreCase("Entry")) {
                            inEntry = true;
                            currentRecord = new Application();

                        }break;
                    case XmlPullParser.TEXT:
                        textValue=xpp.getText();
                        break;

                    case XmlPullParser.END_TAG:
                       // Log.d("ParserApplication", "ending tag for " + tagName);
                        if(inEntry){
                            if(tagName.equalsIgnoreCase("entry")){
                                applications.add(currentRecord);
                                inEntry=false;
                            }else if(tagName.equalsIgnoreCase("name")){
                                currentRecord.setName(textValue);
                            }else if(tagName.equalsIgnoreCase("artist")){
                                currentRecord.setArtist(textValue);
                            }else if(tagName.equalsIgnoreCase("releasDate")){
                                currentRecord.setReleaseDate(textValue);
                            }
                        }
                        break;

                    default:
                        //nothing else;
                }
                eventType = xpp.next();
            }
            return true;
        } catch (Exception e) {
            status = false;
            e.printStackTrace();

        }
        for(Application app : applications){
            Log.d("ParseApplication ","******************************");
            Log.d("ParseApplication ","application name "+app.getName());
            Log.d("ParseApplication ", "application artist "+app.getArtist());
            Log.d("ParseApllication ", "application releas date "+ app.getReleaseDate());

        }
        return true;
    }

}
