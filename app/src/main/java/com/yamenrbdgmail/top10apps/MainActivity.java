package com.yamenrbdgmail.top10apps;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private Button btnParse;
    private ListView listApps;
    private String mFileContent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnParse = (Button) findViewById(R.id.btnParse);


        btnParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseApplication parseApplication = new ParseApplication(mFileContent);
                parseApplication.process();
                ArrayAdapter<Application> arrayAdapter = new ArrayAdapter<Application>(
                        MainActivity.this,R.layout.list_item,parseApplication.getApplications());
                listApps.setAdapter(arrayAdapter);

            }
        });
        listApps = (ListView) findViewById(R.id.xmlListView);
        DawnlodData dawnlod = new DawnlodData();
        dawnlod.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml");




    }


    private class DawnlodData extends AsyncTask<String, Void, String> {
        private String mFileContent;


        protected String doInBackground(String... params) {
            mFileContent = downloadXMLFile(params[0]);
            if (mFileContent == null) {
                Log.d("downloadData", "error dawnloading");

            }
            return mFileContent;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d("downloadData","result was "+result);

        }

        private String downloadXMLFile(String urlPath) {
            StringBuilder tempBuffer = new StringBuilder();
            try {
                URL url = new URL(urlPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                int response = connection.getResponseCode();
                Log.d("downloadData", "the response code is " + response);
                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);

                int charRead;
                char[] inputBuffer = new char[500];
                while (true) {
                    charRead = isr.read(inputBuffer);
                    if (charRead <= 0) {
                        break;
                    }
                    tempBuffer.append(String.copyValueOf(inputBuffer, 0, charRead));

                }
                return tempBuffer.toString();

            } catch (IOException e) {
                Log.d("downloadData", "error dawnloding " + e.getMessage());
            }catch (SecurityException e){
                Log.d("dawnlodData","securty exception . need premission"+e.getMessage());
            }
            return null;
        }


    }
}

