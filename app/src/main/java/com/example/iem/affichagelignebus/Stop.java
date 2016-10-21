package com.example.iem.affichagelignebus;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by iem on 21/10/2016.
 */

public class Stop {

    private String mName;
    private int id;
    private LatLng mCoord;
    private List<Integer> idLine;


    public Stop() {}

    public Stop(String name, int id, LatLng coord, List<Integer> idLine){
        this.mName = name;
        this.id = id;
        this.mCoord = coord;
        this.idLine = idLine;
    }

    public String getName(){
        return this.mName;
    }

    public int getId(){
        return this.id;
    }

    public LatLng getCoord(){
        return this.mCoord;
    }

    public Double getLat() { return mCoord.latitude; }

    public Double getLong() { return mCoord.longitude; }

    public List<Integer> getIdLine() { return this.idLine; }

    public void setName(String name){
        this.mName = name;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setCoord(Double coord1, Double coord2){
        this.mCoord = new LatLng(coord1,coord2);
    }

    public void setIdLine(List<Integer> idLine) { this.idLine = idLine; }


}
