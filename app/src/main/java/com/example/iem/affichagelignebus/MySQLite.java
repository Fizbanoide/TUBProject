package com.example.iem.affichagelignebus;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by iem on 21/10/2016.
 */

public class MySQLite extends SQLiteOpenHelper {

    private static final String TABLE_STOPS = "table_stops";
    private static final String COL_NAME = "NAME";
    private static final String COL_ID = "ID";
    private static final String COL_COORD = "COORD";
    private static final String COL_IDLINE = "ID LINE";

    private static final String CREATE_BDD = "CREATE TABLE" + TABLE_STOPS + "(" + COL_NAME + "TEXT NOT NULL, " + COL_ID + " INTER PRIMARY KEY AUTOINCREMENT, " + COL_COORD + " LATLONG TYPE, " + COL_IDLINE + "INTEGER LIST);";

    public MySQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_BDD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE " + TABLE_STOPS + ";");
            onCreate(db);
    }
}
