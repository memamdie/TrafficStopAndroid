package com.example.hxr.trafficstop;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Isabel on 6/15/17.
 */

public class DBCarsHelper extends SQLiteOpenHelper{
    //Constants for db name and version
    private static final String DATABASE_NAME = "cars.db";
    private static final int DATABASE_VERSION = 1;

    //Constants for identifying table and columns
    public static final String TABLE_CARS = "cars";
    public static final String CARS_ID = "_id";
    public static final String CARS_LICENSE = "license";
    public static final String CARS_MAKE = "make";
    public static final String CARS_MODEL = "model";
    public static final String CARS_YEAR = "year";
    public static final String CARS_COLOR = "color";
    public static final String CARS_NICKNAME = "nickname";

    public static final String[] ALL_COLUMNS = {CARS_ID, CARS_LICENSE, CARS_MAKE, CARS_MODEL, CARS_YEAR, CARS_COLOR, CARS_NICKNAME};

    //SQL to create table
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_CARS + " (" +
                    CARS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CARS_LICENSE + " TEXT, " +
                    CARS_MAKE + " TEXT, " +
                    CARS_MODEL + " TEXT, " +
                    CARS_YEAR + " TEXT, " +
                    CARS_COLOR + " TEXT, " +
                    CARS_NICKNAME + " TEXT" +
                    ")";

    public DBCarsHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_CARS);
        onCreate(db);
    }
}
