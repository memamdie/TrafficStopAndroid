package com.example.hxr.trafficstop;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Isabel on 6/15/17.
 */

public class DBProfileHelper extends SQLiteOpenHelper {
    //Constants for db name and version
    private static final String DATABASE_NAME = "profile.db";
    private static final int DATABASE_VERSION = 1;

    //Constants for identifying table and columns
    public static final String TABLE_PROFILE = "profile";
    public static final String PROFILE_ID = "_id";
    public static final String PROFILE_FIRST_NAME = "firstName";
    public static final String PROFILE_LAST_NAME = "lastName";
    public static final String PROFILE_DRIVER_ID = "driverID";
    public static final String PROFILE_ADDR1 = "Addr1";
    public static final String PROFILE_ADDR2 = "Addr2";
    public static final String PROFILE_CITY = "city";
    public static final String PROFILE_STATE = "state";
    public static final String PROFILE_ZIP = "zip";

    public static final String[] ALL_COLUMNS = {PROFILE_ID, PROFILE_FIRST_NAME, PROFILE_LAST_NAME, PROFILE_DRIVER_ID, PROFILE_ADDR1, PROFILE_ADDR2, PROFILE_CITY, PROFILE_STATE, PROFILE_ZIP};
    //SQL to create table
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_PROFILE + " (" +
                    PROFILE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    PROFILE_FIRST_NAME + " TEXT, " +
                    PROFILE_LAST_NAME + " TEXT, " +
                    PROFILE_DRIVER_ID + " TEXT, " +
                    PROFILE_ADDR1 + " TEXT, " +
                    PROFILE_ADDR2 + " TEXT, " +
                    PROFILE_CITY + " TEXT, " +
                    PROFILE_STATE + " TEXT, " +
                    PROFILE_ZIP + " TEXT " +
                    ")";

    public DBProfileHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS "+TABLE_PROFILE);
        onCreate(db);
    }
}
