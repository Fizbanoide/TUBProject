package com.example.iem.affichagelignebus;

import java.util.List;

/**
 * Created by iem on 19/10/2016.
 */

public class Ligne {

    private String mDir_ligne;
    private int mId, mId_Ligne;
    private List<Stop> stops;

    public Ligne(String dir, int id,int id_Ligne, List<Stop> stops) {
        this.mDir_ligne = dir;
        this.mId = id;
        this.mId_Ligne = id_Ligne;
        this.stops = stops;
    }

    public int getId_Ligne() {
        return this.mId_Ligne;
    }

    public String getDir_ligne() {
        return this.mDir_ligne;
    }

    public int getId() {
        return this.mId;
    }

    public void setDir_ligne(String dir){
        this.mDir_ligne = dir;
    }

    public void setId_Ligne(int id){
        this.mId_Ligne = id;
    }

    public void setmId(int id) {
        this.mId = id;
    }




}
