package com.example.iem.project_tub.controller;

import com.example.iem.project_tub.models.Arret;
import com.example.iem.project_tub.models.Horaire;
import com.example.iem.project_tub.models.HoraireTrajet;
import com.example.iem.project_tub.models.Ligne;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by iem on 03/02/2017.
 */

public interface APIInterface {

    @GET("lines/listall")
    Call<List<Ligne>> getAllLignes();


    @GET("stops/listall")
    Call<List<Arret>> getAllArrets();

    @GET("stops/listallfromline")
    Call<List<Arret>> getArretsFromLigne(@Query("line") int idLigne);

    @GET("lines/nextstops")
    Call<List<Arret>> getArretsFromLigneNext(@Query("line_id") int idLigne, @Query("stop_id") int idStopDepart);


    @GET("linestop/getlinestopid")
    Call<Integer> getIdLineStop(@Query("idline") int idLigne, @Query("idstop") int idStop);


    @GET("hours/listallfromstop")
    Call<List<Horaire>> getHoraires(@Query("id") int idLigneArret);

    @GET("stops/gethour")
    Call<HoraireTrajet> getHoraireTrajet(@Query("idLine") int idLigne, @Query("idArretDepart") int idArretDepart, @Query("idArretArrivee") int idArretArrivee, @Query("heure") String heure);

}
