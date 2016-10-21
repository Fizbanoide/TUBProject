package com.example.iem.affichagelignebus;

/**
 * Created by iem on 19/10/2016.
 */

public class Ligne {

    private String mName_ligne;
    private int mId_Ligne;

    public Ligne(String name, int id) {
        this.mName_ligne = name;
        this.mId_Ligne = id;
    }

    public int getId_Ligne() {
        return this.mId_Ligne;
    }

    public String getName_ligne() {
        return this.mName_ligne;
    }

    public void setName_ligne(String name){
        this.mName_ligne = name;
    }

    public void setId_Ligne(int id){
        this.mId_Ligne = id;
    }
}
