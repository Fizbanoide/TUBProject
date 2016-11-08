package com.example.iem.affichagelignebus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iem on 21/10/2016.
 */

public class StopBDD {

    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "TUB_app_DB.sqlite";

    private static final String TABLE_STOPS = "arrets";
    private static final String COL_ID = "ID";
    private static final int NUM_COL_ID = 0;
    private static final String COL_NAME = "NAME";
    private static final int NUM_COL_NAME = 1;
    private static final String COL_LAT = "LAT";
    private static final int NUM_COL_LAT = 2;
    private static final String COL_LONG = "LONG";
    private static final int NUM_COL_LONG = 3;

    private SQLiteDatabase db;

    private MySQLite mySQLiteBase;

    public StopBDD(Context context){
        mySQLiteBase = new MySQLite(context);
    }

    public void open() {
        db = mySQLiteBase.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public long addStop(Stop stop) {
        ContentValues values = new ContentValues();
        values.put(COL_NAME, stop.getName());
        values.put(COL_LAT, String.valueOf(stop.getLat()));
        values.put(COL_LONG, String.valueOf(stop.getLong()));

        return db.insert(TABLE_STOPS,null,values);
    }

    public int modStop(int id, Stop stop){
        ContentValues values = new ContentValues();
        values.put(COL_ID, stop.getId());
        values.put(COL_NAME, stop.getName());
        values.put(COL_LAT, stop.getLat());
        values.put(COL_LONG, stop.getLong());

        return db.update(TABLE_STOPS, values, String.format("%s = %s",COL_ID, id), null);
    }

    public int removeStop(int id) {
        return db.delete(TABLE_STOPS, String.format("%s = %s", COL_ID, id), null);
    }

    public Stop getStopWithName(String name) {
        Cursor c = db.rawQuery("SELECT * FROM "+ TABLE_STOPS + " WHERE " + COL_NAME + " LIKE '" + name + "'", null);
        c.moveToFirst();
        return cursorToStop(c);
    }

    public Stop getStopWithId(Integer id) {
        Cursor c = db.rawQuery("SELECT * FROM "+ TABLE_STOPS + " WHERE " + COL_ID + " LIKE '" + id + "'", null);
        c.moveToFirst();
        return cursorToStop(c);
    }

    public List<Stop> getLine() {
        Cursor c = db.rawQuery("SELECT * FROM "+ TABLE_STOPS , null);
        c.moveToFirst();
        List<Stop> stopsLine = new ArrayList<>();

        while(c.moveToNext()){
            stopsLine.add(cursorToStop(c));
        }

        c.close();

        return stopsLine;
    }

    private Stop cursorToStop(Cursor c){
        if(c.getCount() == 0){
            return null;
        }



        Stop stop = new Stop();

        stop.setId(c.getInt(NUM_COL_ID));
        stop.setName(c.getString(NUM_COL_NAME));
        stop.setCoord(c.getDouble(NUM_COL_LAT), c.getDouble(NUM_COL_LONG));

        List<Integer> lineId = new ArrayList<>();

        stop.setIdLine(lineId); //parser la liste dans la bdd avec des , pour split la liste et la recreer ici



        return stop;
    }
}
