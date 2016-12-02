package com.example.iem.project_tub.controller;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by iem on 30/11/2016.
 */

public class WebServiceCaller extends AsyncTask<String, Void, Void> {

    String error = null;
    String content = null;
    TextView textView;
    JSONArray json = null;

    public WebServiceCaller(){}

    protected void onPreExecute() {
    }

    @Override
    protected Void doInBackground(String... urls) {

        HttpURLConnection connection = null;


        try {
            URL url = new URL(urls[0]);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-length", "0");
            connection.setUseCaches(false);
            connection.setAllowUserInteraction(false);
            connection.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line+"\n");
            }
            br.close();

            content = sb.toString();

        } catch (Exception e) {
            error = e.getMessage();
        } finally {
            try {
                connection.disconnect();
            } catch (Exception ex) {

            }
        }
        return null;
    }


    protected void onPostExecute(Void unused) {

    }

    public String getResult(){
        return content;
    }

}
