package com.example.hxr.trafficstop;

/**
 * Created by Isabel on 6/15/17.
 */

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class Profile extends Fragment
    implements LoaderManager.LoaderCallbacks<Cursor>
{
    private CursorAdapter cursorAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
//        insert();

        String[] from = {DBProfileHelper.PROFILE_FIRST_NAME,DBProfileHelper.PROFILE_LAST_NAME,DBProfileHelper.PROFILE_DRIVER_ID,DBProfileHelper.PROFILE_ADDR1,DBProfileHelper.PROFILE_ADDR2,DBProfileHelper.PROFILE_CITY,DBProfileHelper.PROFILE_STATE,DBProfileHelper.PROFILE_ZIP};
        int [] to = {android.R.id.text1};


        cursorAdapter = new SimpleCursorAdapter(getContext(), android.R.layout.simple_list_item_1, null, from, to, 0);
        EditText firstName = (EditText) rootView.findViewById(R.id.firstName);
        EditText lastName = (EditText) rootView.findViewById(R.id.lastName);
        EditText driversLicense = (EditText) rootView.findViewById(R.id.driversLicense);
        EditText Address1 = (EditText) rootView.findViewById(R.id.Address1);
        EditText Address2 = (EditText) rootView.findViewById(R.id.Address2);
        EditText City = (EditText) rootView.findViewById(R.id.City);
        EditText State = (EditText) rootView.findViewById(R.id.State);
        EditText Zip = (EditText) rootView.findViewById(R.id.Zip);



        firstName.setText(ProfileProvider.fName);
        lastName.setText(ProfileProvider.lName);
        driversLicense.setText(ProfileProvider.dLicense);
        Address1.setText(ProfileProvider.Addr1);
        Address2.setText(ProfileProvider.Addr2);
        City.setText(ProfileProvider.City);
        State.setText(ProfileProvider.State);
        Zip.setText(ProfileProvider.Zip);

        getLoaderManager().initLoader(0, null, this);

        return rootView;
    }

    private void insert() {
        ContentValues values = new ContentValues();
        values.put(DBProfileHelper.PROFILE_LAST_NAME, "My Last Name");
        Uri profileUri = getContext().getContentResolver().insert(ProfileProvider.CONTENT_URI, values);
        Log.d("Profile", "Inserted person: " + profileUri.getLastPathSegment());
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(), ProfileProvider.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        cursorAdapter.swapCursor(data);
        cursorAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        cursorAdapter.swapCursor(null);
    }
}
