package com.nicholausmarsden.eventlist.DATABASE;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.nicholausmarsden.eventlist.DATABASE.FieldName.EventFieldName;

/**
 * Created by nicho on 12/27/2017.
 */

public class DBHelper extends SQLiteOpenHelper{
    private static final String TAG= "DBHelper";
    static final String TABLE_NAME= "table_name";
    static final String DATABASE_NAME= "database_name";
    private static final int DB_VERSION = 1;
    private static DBHelper mInstance;
    private final Context mContext;

    private DBHelper(Context mContext) {
        super(mContext, DATABASE_NAME, null, DB_VERSION);
        this.mContext = mContext;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d( TAG, "database successfully created");
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                + EventFieldName.COL_ID   + " INTEGER PRIMARY KEY,"
                + EventFieldName.COL_FLAG  + " INTERGER,"
                + EventFieldName.COL_TIME + " TEXT,"
                + EventFieldName.COL_DES + " TEXT,"
                + EventFieldName.COL_NAME + " TEXT)"
               );
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public static DBHelper getInstance(Context applicationContext) { // gets the context of the content provider
        if (mInstance == null)
            mInstance = new DBHelper(applicationContext);  //android.app.Application@beb897c
        return mInstance;
    }
}
