package com.example.iem.affichagelignebus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by iem on 21/10/2016.
 */

public class StopBDD {

    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "stops.sqlite";

    private static final String TABLE_STOPS = "table_stops";
    private static final String COL_ID = "ID";
    private static final int NUM_COL_ID = 0;
    private static final String COL_NAME = "NAME";
    private static final int NUM_COL_NAME = 1;
    private static final String COL_LAT = "LAT";
    private static final int NUM_COL_LAT = 2;
    private static final String COL_LONG = "LONG";
    private static final int NUM_COL_LONG = 3;
    private static final String COL_IDLINE = "ID LINE";
    private static final int NUM_COL_IDLINE = 4;

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
        values.put(COL_IDLINE, String.valueOf(stop.getIdLine()));

        return db.insert(TABLE_STOPS,null,values);
    }

    public int modStop(int id, Stop stop){
        ContentValues values = new ContentValues();
        values.put(COL_NAME, stop.getName());
        values.put(COL_LAT, String.valueOf(stop.getLat()));
        values.put(COL_LONG, String.valueOf(stop.getLong()));
        values.put(COL_IDLINE, String.valueOf(stop.getIdLine()));

        return db.update(TABLE_STOPS, values, String.format("%s = %s",COL_ID, id), null);
    }

    public int removeStop(int id) {
        return db.delete(TABLE_STOPS, String.format("%s = %s", COL_ID, id), null);
    }

    public Stop getStopWithName(String name) {
        Cursor c = db.query(TABLE_STOPS, new String[] {COL_ID, COL_NAME, COL_LAT, COL_LONG, COL_IDLINE}, COL_NAME + " LIKE \"" + name +"\"", null, null, null, null);
        return cursorToStop(c);
    }

    private Stop cursorToStop(Cursor c){
        if(c.getCount() == 0){
            return null;
        }

        c.moveToFirst();

        Stop stop = new Stop();

        stop.setId(c.getInt(NUM_COL_ID));
        stop.setName(c.getString(NUM_COL_NAME));
        stop.setCoord(c.getDouble(NUM_COL_LAT), c.getDouble(NUM_COL_LONG));
        //stop.setIdLine(c.get); parser la liste dans la bdd avec des , pour split la liste et la recreer ici

        c.close();

        return stop;
    }
}
