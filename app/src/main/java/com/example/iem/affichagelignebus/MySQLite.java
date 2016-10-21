package com.example.iem.affichagelignebus;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by iem on 21/10/2016.
 */

public class MySQLite extends SQLiteOpenHelper {

    private final Context myContext;
    private static MySQLite sInstance;

    private static final String DATABASE_NAME = "stops.sqlite";
    private static final int DATABASE_VERSION = 1;
    private String DATABASE_PATH;

    public static synchronized MySQLite getsInstance(Context context){
        if (sInstance == null) { sInstance = new MySQLite(context); }
        return sInstance;
    }

    public MySQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.myContext = context;
        String filesDir = context.getFilesDir().getPath();
        DATABASE_PATH = filesDir.substring(0, filesDir.lastIndexOf("/")) + "/databases";

        if (!checkdatabase()) {
            copydatabase();
        }

    }

    private boolean checkdatabase() {
        File dbfile = new File(DATABASE_PATH + DATABASE_NAME);
        return dbfile.exists();
    }

    private void copydatabase() {
        final String outFileName = DATABASE_PATH + DATABASE_NAME;

        InputStream myInput;
        try {
            myInput = myContext.getAssets().open(DATABASE_NAME);

            File pathFile = new File(DATABASE_PATH);
            if(!pathFile.exists()) {
                if(!pathFile.mkdirs()) {
                    Toast.makeText(myContext, "Erreur : copydatabase(), pathFile.mkdirs()", Toast.LENGTH_SHORT);
                    return;
                }
            }

            OutputStream myOutPut = new FileOutputStream(outFileName);

            byte[] buffer = new byte[1024];
            int length;
            while((length = myInput.read(buffer)) > 0) {
                myOutPut.write(buffer, 0, length);
            }

            myOutPut.flush();
            myOutPut.close();
            myInput.close();;
        }
        catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(myContext, "Erreur : copydatabase()", Toast.LENGTH_SHORT);
        }

        try{
            SQLiteDatabase checkdb = SQLiteDatabase.openDatabase(DATABASE_PATH + DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE);
            checkdb.setVersion(DATABASE_VERSION);
        }
        catch(SQLException e){
            //db doesnt exist
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (oldVersion < newVersion){
                myContext.deleteDatabase(DATABASE_NAME);
                copydatabase();
            }
    }
}
