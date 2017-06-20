package com.example.hxr.trafficstop;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Isabel on 6/13/17.
 */

public class CarsProvider extends ContentProvider {

    private static final String AUTHORITY = "com.example.hxr.trafficstop.carsprovider";
    private static final String BASE_PATH = "notes";
    public static final Uri CONTENT_URI =
            Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH );

    // Constant to identify the requested operation
    private static final int READ_DATA = 1;
    private static final int READ_ONE_DATA = 2;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    public static final String CONTENT_ITEM_TYPE = "Car";

    static {
        uriMatcher.addURI(AUTHORITY, BASE_PATH, READ_DATA);
        uriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", READ_ONE_DATA);
    }

    private  SQLiteDatabase carsdatabase;

    @Override
    public boolean onCreate() {
        DBCarsHelper carshelper = new DBCarsHelper(getContext());
        carsdatabase = carshelper.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        if(uriMatcher.match(uri) == READ_ONE_DATA){
            selection = DBCarsHelper.CARS_ID + "=" + uri.getLastPathSegment();
        }
        Cursor newcursor = carsdatabase.query(DBCarsHelper.TABLE_CARS, DBCarsHelper.ALL_COLUMNS, selection, null, null, null, DBCarsHelper.CARS_ID);

        return newcursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long id = carsdatabase.insert(DBCarsHelper.TABLE_CARS,null, values);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return carsdatabase.delete(DBCarsHelper.TABLE_CARS, selection, selectionArgs);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return carsdatabase.update(DBCarsHelper.TABLE_CARS, values, selection, selectionArgs);
    }
}
