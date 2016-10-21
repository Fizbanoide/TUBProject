package com.example.iem.affichagelignebus;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by iem on 21/10/2016.
 */

public class StopBDD {

    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "stops.db";

    private static final String TABLE_STOPS = "table_stops";
    private static final String COL_NAME = "NAME";
    private static final int NUM_COL_NAME = 0;
    private static final String COL_ID = "ID";
    private static final int NUM_COL_ID = 1;
    private static final String COL_COORD = "COORD";
    private static final int NUM_COL_COORD = 2;
    private static final String COL_IDLINE = "ID LINE";
    private static final int NUM_COL_IDLINE = 3;

    private SQLiteDatabase bdd;

    private MySQLite mySQLiteBase;

    public StopBDD(Context context){
        mySQLiteBase = new MySQLite(context);
    }
}
