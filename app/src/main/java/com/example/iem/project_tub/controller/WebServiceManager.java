package com.example.iem.project_tub.controller;

import android.content.Context;
import android.os.Handler;

import com.example.iem.project_tub.models.Arret;
import com.example.iem.project_tub.models.Ligne;
import com.google.android.gms.cast.JoinOptions;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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



    private List myRunnable(List myList, String url, MyThread runnable){
        WebServiceCaller wsc = new WebServiceCaller();
        wsc.execute(url);
        String result = wsc.getResult();

        JSONArray jsonResult = null;
        try {
            jsonResult = new JSONArray(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for(int i=0 ; i < jsonResult.length() ; i++){
            JSONObject jsonObject = null;

            try {
                jsonObject = jsonResult.getJSONObject(i);

                runnable.setJsonObject(jsonObject);
                myList.add(new Handler().post(runnable));

            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }

        return myList;
    }

    private class MyThread implements Runnable {
        protected JSONObject jsonObject;

        public MyThread() {}

        private void setJsonObject(JSONObject object){
            jsonObject = object;
        }

        @Override
        public void run() {}
    }



    // Retourne toutes les lignes
    public List getLignes(){

        List<Ligne> lignes = new ArrayList<>();

        String url = "";

        MyThread myThread = new MyThread() {
            @Override
            public void run() {
                try {
                    new Ligne(jsonObject.getInt("id"), jsonObject.getString("nom"), jsonObject.getString("idLigne"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        return myRunnable(lignes, url, myThread);
    }


    // Retourne tous les arrets
    public List getArrets(){

        List<Arret> arrets = new ArrayList<>();

        String url = "mysilexmvc.local/index.php/stops/listall";

        MyThread myThread = new MyThread() {
            @Override
            public void run() {
                try {
                    new Arret(jsonObject.getInt("id"), jsonObject.getString("nom"), new LatLng(jsonObject.getDouble("latitude"), jsonObject.getDouble("latitude")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };


        return myRunnable(arrets, url, myThread);
    }


    // Retourne les arrets d'une ligne
    public List getArretsWithLigne(int idLigne){

        List<Arret> arrets = new ArrayList<>();

        String url = "http://mysilexmvc.local/index.php/stops/listallfromline?line=" + idLigne;

        MyThread myThread = new MyThread() {
            @Override
            public void run() {
                try {
                    new Arret(jsonObject.getInt("id"), jsonObject.getString("nom"), new LatLng(jsonObject.getDouble("latitude"), jsonObject.getDouble("latitude")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        return myRunnable(arrets, url, myThread);
    }


    // Retourne un itinéraire (liste des arrets) entre 2 arrets
    public List getArretsItineraire(int idArretDepart, int idArretArrivee, int idLigne){

        List<Arret> arrets = new ArrayList<>();

        String url = "http://mysilexmvc.local/index.php/stops/listallfromline?idArretDepart=" + idArretDepart + "&idArretArrivee=" + idArretArrivee + "&idLigne=" + idLigne;

        MyThread myThread = new MyThread() {
            @Override
            public void run() {
                try {
                    new Arret(jsonObject.getInt("id"), jsonObject.getString("nom"), new LatLng(jsonObject.getDouble("latitude"), jsonObject.getDouble("latitude")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        return myRunnable(arrets, url, myThread);
    }

    // Retourne l'horaire de départ et d'arrivée d'un trajet
    public List getHorairesItineraire(Date dateDepart){
        List<Date> horaires = new ArrayList<>();

        String url = "http://mysilexmvc.local/index.php/";

        WebServiceCaller wsc = new WebServiceCaller();
        wsc.execute(url);
        String result = wsc.getResult();

        JSONObject jsonResult = null;
        try {
            jsonResult = new JSONObject(result);
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            horaires.add(sdf.parse(jsonResult.getString("depart")));
            horaires.add(sdf.parse(jsonResult.getString("arrivee")));

        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }




        return horaires;
    }
}
