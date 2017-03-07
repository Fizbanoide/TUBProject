package com.example.iem.project_tub.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by iem on 07/03/2017.
 */

public class HoraireTrajet {

    @SerializedName("depart")
    @Expose
    private String heureDepart;
    @SerializedName("arrivee")
    @Expose
    private String heureArrivee;

    public HoraireTrajet(String heureDepart, String heureArrivee) {
        this.heureDepart = heureDepart;
        this.heureArrivee = heureArrivee;
    }

    public String getDepart() {
        return heureDepart;
    }

    public String getArrivee() {
        return heureArrivee;
    }
}
