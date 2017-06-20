package com.example.hxr.trafficstop;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Isabel on 6/15/17.
 */

public class ProfileProvider extends ContentProvider {

    private static final String AUTHORITY = "com.example.hxr.trafficstop.profileprovider";
    private static final String BASE_PATH = "profile";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

    // Constant to identify the requested operation
    private static final int READ_DATA = 1;
    private static final int READ_ONE_DATA = 2;

    public static String fName;
    public static String lName;
    public static String dLicense;
    public static String Addr1;
    public static String Addr2;
    public static String City;
    public static String State;
    public static String Zip;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, BASE_PATH, READ_DATA);
        uriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", READ_ONE_DATA);
    }

    private SQLiteDatabase profiledatabase;

    @Override
    public boolean onCreate() {
        DBProfileHelper profileHelper = new DBProfileHelper(getContext());
        profiledatabase = profileHelper.getWritableDatabase();

        fName = getLastName(DBProfileHelper.PROFILE_FIRST_NAME );
        lName = getLastName(DBProfileHelper.PROFILE_LAST_NAME );
        dLicense = getLastName(DBProfileHelper.PROFILE_DRIVER_ID );
        Addr1 = getLastName(DBProfileHelper.PROFILE_ADDR1 );
        Addr2 = getLastName(DBProfileHelper.PROFILE_ADDR2 );
        City = getLastName(DBProfileHelper.PROFILE_CITY );
        State = getLastName(DBProfileHelper.PROFILE_STATE );
        Zip = getLastName(DBProfileHelper.PROFILE_ZIP );


        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor newcursor = profiledatabase.query(DBProfileHelper.TABLE_PROFILE, DBProfileHelper.ALL_COLUMNS, selection, null, null, null, DBProfileHelper.PROFILE_ID);

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
        long id = profiledatabase.insert(DBProfileHelper.TABLE_PROFILE, null, values);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return profiledatabase.delete(DBProfileHelper.TABLE_PROFILE, selection, selectionArgs);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return profiledatabase.update(DBProfileHelper.TABLE_PROFILE, values, selection, selectionArgs);
    }

    public String getLastName(String ProfileCol) {
        String colName;
        String[] col = {ProfileCol};
        Cursor newcursor2 = profiledatabase.query(DBProfileHelper.TABLE_PROFILE, DBProfileHelper.ALL_COLUMNS, null, null, null, null, DBProfileHelper.PROFILE_ID);

        int index = newcursor2.getColumnIndex(ProfileCol);
        Log.d("Profile", "CURSOR NUM: " + newcursor2.getCount());
        Log.d("Profile", "INDEX: " + index);
        int i = 0;
        colName = "name";
        while (i != newcursor2.getCount()) {
            newcursor2.moveToNext();
            Log.d("Profile", "name: " + newcursor2.getString(index));
            if (i == newcursor2.getCount() - 1) {
                String name = newcursor2.getString(index);
                if (name != null) {
                    colName = name;
                } else {
                    colName = "name1";
                }
            } else {
                colName = "name2";
            }
            i++;
        }
        return colName;
    }
}
