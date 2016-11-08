package com.example.iem.affichagelignebus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iem on 19/10/2016.
 */

public class LigneBDD {

    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "TUB_app_DB.sqlite";

    private static final String TABLE_LIGNES = "lignes";
    private static final String COL_ID = "ID";
    private static final int NUM_COL_ID = 0;
    private static final String COL_NUMBER = "NUMBER";
    private static final int NUM_COL_NUMBER = 1;
    private static final String COL_DIR = "DIRECTION";
    private static final int NUM_COL_DIR = 2;

    private SQLiteDatabase db;

    private MySQLite mySQLiteBase;

    public LigneBDD(Context context){
        mySQLiteBase = new MySQLite(context);
    }

    public void open() {
        db = mySQLiteBase.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public long addLigne(Ligne ligne) {
        ContentValues values = new ContentValues();
        values.put(COL_NUMBER, ligne.getId_Ligne());
        values.put(COL_DIR, ligne.getDir_ligne());

        return db.insert(TABLE_LIGNES,null,values);
    }

    public int modLigne(int id, Ligne ligne){
        ContentValues values = new ContentValues();
        values.put(COL_ID, ligne.getId());
        values.put(COL_NUMBER, ligne.getId_Ligne());
        values.put(COL_DIR, ligne.getDir_ligne());


        return db.update(TABLE_LIGNES, values, String.format("%s = %s",COL_ID, id), null);
    }

    public int removeStop(int id) {
        return db.delete(TABLE_LIGNES, String.format("%s = %s", COL_ID, id), null);
    }

}
