package com.example.iem.project_tub.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by iem on 18/10/2016.
 */

public class Ligne {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("number")
    @Expose
    private int numero;
    @SerializedName("terminuses")
    @Expose
    private String terminus;
    @SerializedName("way")
    @Expose
    private int direction;

    public Ligne(int id, int numero, String terminus, int direction) {
        this.id = id;
        this.numero = numero;
        this.terminus = terminus;
        this.direction = direction;
    }

    public int getId() {
        return id;
    }

    public int getNumero() {
        return numero;
    }

    public String getTerminus() {
        return terminus.split("/")[direction];
    }

    @Override
    public String toString(){
        return "Ligne " + getNumero() + ", direction " + getTerminus();
    }

}
