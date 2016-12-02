package com.example.iem.project_tub.controller;

import android.content.Context;

import com.example.iem.project_tub.models.Ligne;
import com.example.iem.project_tub.models.LigneManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by iem on 01/12/2016.
 */

public class WebServiceManager {

    private static WebServiceManager sInstance;

    // Singleton
    public static synchronized WebServiceManager getInstance(Context context) {
        if (sInstance == null) { sInstance = new WebServiceManager(context); }
        return sInstance;
    }

    private WebServiceManager(Context context) {}




    public List getLignes(){

        List<Ligne> lignes = new ArrayList<>();

        WebServiceCaller wsc = new WebServiceCaller();
        wsc.execute("");
        String result = wsc.getResult();

        JSONArray jsonLignes = null;
        try {
            jsonLignes = new JSONArray(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Ligne l = null;

        for(int i=0 ; i < jsonLignes.length() ; i++){
            JSONObject jsonObject = null;

            try {
                jsonObject = jsonLignes.getJSONObject(i);

                l = new Ligne(jsonObject.getInt("id"), jsonObject.getString("nom"), jsonObject.getString("idLigne"));

            } catch (JSONException e1) {
                e1.printStackTrace();
            }

            lignes.add(l);
        }

        return lignes;
    }
}
