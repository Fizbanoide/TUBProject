package com.example.iem.project_tub.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Date;

/**
 * Created by iem on 28/02/2017.
 */

public class Horaire {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("id_line_stop")
    @Expose
    private int idLineStop;
    @SerializedName("hour")
    @Expose
    private String heure;

    public Horaire(int id, int idLineStop, String heure) {
        this.id = id;
        this.idLineStop = idLineStop;
        this.heure = heure;
    }

    public int getId() {
        return id;
    }

    public int getIdLineStop() {
        return idLineStop;
    }

    public String getHeure() {
        return heure;
    }
}
