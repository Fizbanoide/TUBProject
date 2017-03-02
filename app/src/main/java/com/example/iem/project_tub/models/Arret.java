package com.example.iem.project_tub.models;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by iem on 02/11/2016.
 */

public class Arret {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String nom;
    @SerializedName("Coordonales")
    @Expose
    private LatLng coord;

    public Arret() {}

    public Arret(int id, String nom, LatLng coord) {
        this.id = id;
        this.nom = nom;
        this.coord = coord;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public LatLng getCoord(){
        return this.coord;
    }

    public Double getLat() { return coord.latitude; }

    public Double getLong() { return coord.longitude; }
}
