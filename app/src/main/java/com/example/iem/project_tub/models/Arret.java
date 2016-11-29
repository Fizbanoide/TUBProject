package com.example.iem.project_tub.models;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by iem on 02/11/2016.
 */

public class Arret {

    private int id;
    private String nom;
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

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public LatLng getCoord(){
        return this.coord;
    }

    public Double getLat() { return coord.latitude; }

    public Double getLong() { return coord.longitude; }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setCoord(Double coord1, Double coord2){
        this.coord = new LatLng(coord1,coord2);
    }
}
