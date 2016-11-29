package com.example.iem.project_tub.models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.iem.project_tub.controller.MySQLite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iem on 02/11/2016.
 */

public class ArretManager {

    private static ArretManager sInstance;
    private SQLiteDatabase db;
    private MySQLite maBaseSQLite;

    private static final String TABLE_NAME = "arrets";
    private static final String TABLE_NAME_ASSOCIATION = "ligne_arret";
    public static final String KEY_ID_ARRET = "id";
    public static final String KEY_NOM_ARRET = "nom";
    public static final String KEY_LONGITUDE_ARRET = "longitude";
    public static final String KEY_LATITUDE_ARRET = "latitude";




    public static synchronized ArretManager getInstance(Context context) {
        if (sInstance == null) { sInstance = new ArretManager(context); }
        return sInstance;
    }


    private ArretManager(Context context) {
        maBaseSQLite = MySQLite.getInstance(context);

        //on ouvre la table en lecture seule
        db = maBaseSQLite.getReadableDatabase();
    }

    public void close(){
        db.close();
    }

    public List<Arret> getArrets(){
        Cursor arretsCursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        List<Arret> arretList = new ArrayList<>();
        try {
            while (arretsCursor.moveToNext()) {
                Arret arrettmp = new Arret();
                arrettmp.setId(arretsCursor.getInt(arretsCursor.getColumnIndex(KEY_ID_ARRET)));
                arrettmp.setNom(arretsCursor.getString(arretsCursor.getColumnIndex(KEY_NOM_ARRET)));
                arrettmp.setCoord(arretsCursor.getDouble(arretsCursor.getColumnIndex(KEY_LATITUDE_ARRET)), arretsCursor.getDouble(arretsCursor.getColumnIndex(KEY_LONGITUDE_ARRET)));

                arretList.add(arrettmp);


            }
        } finally {
            arretsCursor.close();
        }

        return arretList;
    }

    public List<Arret> getArretsByLigne(int idLigne) {

        Cursor arretsCursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " l, " + TABLE_NAME_ASSOCIATION + " la " +
                "WHERE l.id = la.id_arret " +
                "AND la.id_ligne = " + idLigne,
                null);

        List<Arret> arretList = new ArrayList<>();


        try {

            while (arretsCursor.moveToNext()) {

                Arret arrettmp = new Arret();

                arrettmp.setId(arretsCursor.getInt(arretsCursor.getColumnIndex(KEY_ID_ARRET)));
                arrettmp.setNom(arretsCursor.getString(arretsCursor.getColumnIndex(KEY_NOM_ARRET)));
                arrettmp.setCoord(arretsCursor.getDouble(arretsCursor.getColumnIndex(KEY_LATITUDE_ARRET)), arretsCursor.getDouble(arretsCursor.getColumnIndex(KEY_LONGITUDE_ARRET)));

                arretList.add(arrettmp);

            }
        } finally {
            arretsCursor.close();
        }

        return arretList;

    }
}
