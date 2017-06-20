package com.example.hxr.trafficstop;


import android.app.Activity;
import android.app.LauncherActivity;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;

/**
 * Created by Isabel on 6/16/17.
 */

public class RegisterActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {
    private CursorAdapter cursorAdapter;
    private EditText firstName;
    private EditText lastName;
    private EditText driversLicense;
    private EditText Address1;
    private EditText Address2;
    private EditText City;
    private EditText State;
    private EditText Zip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        driversLicense = (EditText) findViewById(R.id.driversLicense);
        Address1 = (EditText) findViewById(R.id.Address1);
        Address2 = (EditText) findViewById(R.id.Address2);
        City = (EditText) findViewById(R.id.City);
        State = (EditText) findViewById(R.id.State);
        Zip = (EditText) findViewById(R.id.Zip);


        String[] from = {DBProfileHelper.PROFILE_FIRST_NAME,DBProfileHelper.PROFILE_LAST_NAME,DBProfileHelper.PROFILE_DRIVER_ID,DBProfileHelper.PROFILE_ADDR1,DBProfileHelper.PROFILE_ADDR2,DBProfileHelper.PROFILE_CITY,DBProfileHelper.PROFILE_STATE,DBProfileHelper.PROFILE_ZIP};
        int [] to = {android.R.id.text1};

        cursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, null, from, to, 0);
        ContentValues values = new ContentValues();
//        values.put(DBProfileHelper.PROFILE_LAST_NAME, lastName.getText().toString().trim());
//        Uri profileUri = getContentResolver().insert(ProfileProvider.CONTENT_URI, values);
//        Log.d("Profile", "Inserted person: " + profileUri.getLastPathSegment());

//        EditText lastName = (EditText) findViewById(R.id.lastName);


        getLoaderManager().initLoader(0,null,this);
    }

    public void insert(View view) {
        ContentValues values = new ContentValues();
        values.put(DBProfileHelper.PROFILE_FIRST_NAME, firstName.getText().toString().trim());
        values.put(DBProfileHelper.PROFILE_LAST_NAME, lastName.getText().toString().trim());
        values.put(DBProfileHelper.PROFILE_DRIVER_ID, driversLicense.getText().toString().trim());
        values.put(DBProfileHelper.PROFILE_ADDR1, Address1.getText().toString().trim());
        values.put(DBProfileHelper.PROFILE_ADDR2, Address2.getText().toString().trim());
        values.put(DBProfileHelper.PROFILE_CITY, City.getText().toString().trim());
        values.put(DBProfileHelper.PROFILE_STATE, State.getText().toString().trim());
        values.put(DBProfileHelper.PROFILE_ZIP, Zip.getText().toString().trim());
        Uri profileUri = getContentResolver().insert(ProfileProvider.CONTENT_URI, values);
        Log.d("Profile", "Inserted person: " + profileUri.getLastPathSegment());
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, ProfileProvider.CONTENT_URI,
                null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        cursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        cursorAdapter.swapCursor(null);
    }
}
