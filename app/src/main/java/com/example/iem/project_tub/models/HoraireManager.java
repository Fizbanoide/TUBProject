package com.example.iem.project_tub.models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.iem.project_tub.controller.MySQLite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iem on 08/11/2016.
 */

public class HoraireManager {

    private static HoraireManager sInstance;
    private SQLiteDatabase db;
    private MySQLite maBaseSQLite;

    private static final String TABLE_NAME_ASSOC = "ligne_arret";
    public static final String KEY_ID_ASSOC = "id_assoc";

    private static final String TABLE_NAME_HORAIRES = "horaires";
    public static final String KEY_ID_HORAIRE = "horaire";


    // Singleton
    public static synchronized HoraireManager getInstance(Context context) {
        if (sInstance == null) { sInstance = new HoraireManager(context); }
        return sInstance;
    }


    private HoraireManager(Context context) {
        maBaseSQLite = MySQLite.getInstance(context);

        //on ouvre la table en lecture seule
        db = maBaseSQLite.getReadableDatabase();
    }

    public void close(){
        db.close();
    }

    // retourne l'id d'association d'une ligne et d'un arret
    public int getIdAssoc(int idLigne, int idArret){

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_ASSOC + " WHERE id_ligne = " + idLigne + " AND id_arret = " + idArret , null);
        int idAssoc = 0;

        try {
            if (cursor.moveToNext()) {
                idAssoc = cursor.getInt(cursor.getColumnIndex(KEY_ID_ASSOC));
            }
        } finally {
            cursor.close();
        }

        return idAssoc;
    }

    public List<String> getHorairesByAssoc(int idAssoc){
        List<String> horaireList = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_HORAIRES + " WHERE id_assoc = " + idAssoc , null);

        try {
            while (cursor.moveToNext()) {
                horaireList.add(cursor.getString(cursor.getColumnIndex(KEY_ID_HORAIRE)));
            }
        } finally {
            cursor.close();
        }

        return horaireList;
    }
}
