package com.example.iem.project_tub.models;

/**
 * Created by iem on 18/10/2016.
 */

public class Ligne {

    private int id;
    private String numero;
    private String direction;

    public Ligne(int id, String numero, String direction) {
        this.id = id;
        this.numero = numero;
        this.direction = direction;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "Ligne{" +
                "id=" + id +
                ", numero='" + numero + '\'' +
                ", direction='" + direction + '\'' +
                '}';
    }
}
