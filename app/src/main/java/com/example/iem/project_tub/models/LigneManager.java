package com.example.iem.project_tub.models;

/**
 * Created by iem on 02/11/2016.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.iem.project_tub.controller.MySQLite;

import java.util.ArrayList;
import java.util.List;

public class LigneManager {

    private static LigneManager sInstance;
    private SQLiteDatabase db;
    private MySQLite maBaseSQLite;

    private static final String TABLE_NAME = "lignes";
    public static final String KEY_ID_LIGNE = "id";
    public static final String KEY_NUMERO_LIGNE = "numero";
    public static final String KEY_DIRECTION_LIGNE = "direction";


    // Singleton
    public static synchronized LigneManager getInstance(Context context) {
        if (sInstance == null) { sInstance = new LigneManager(context); }
        return sInstance;
    }


    private LigneManager(Context context) {
        maBaseSQLite = MySQLite.getInstance(context);

        //on ouvre la table en lecture seule
        db = maBaseSQLite.getReadableDatabase();
    }

    public void close(){
        db.close();
    }


    public List<Ligne> getLigneList() {
        List<Ligne> ligneList = new ArrayList<>();

        Cursor allLignes = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        try {
            while (allLignes.moveToNext()) {
                ligneList.add(new Ligne(
                        allLignes.getInt(allLignes.getColumnIndex(KEY_ID_LIGNE)),
                        allLignes.getString(allLignes.getColumnIndex(KEY_NUMERO_LIGNE)),
                        allLignes.getString(allLignes.getColumnIndex(KEY_DIRECTION_LIGNE))));
            }
        } finally {
            allLignes.close();
        }

        return ligneList;
    }
}