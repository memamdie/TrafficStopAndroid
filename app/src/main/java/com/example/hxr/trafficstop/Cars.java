package com.example.hxr.trafficstop;

/**
 * Created by Isabel on 6/15/17.
 */

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
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
import android.widget.AdapterView;
import android.widget.ListView;

public class Cars extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor>
{
    private CursorAdapter cursorAdapter;
    private static final int EDITOR_REQUEST_CODE = 1001;


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cars, container, false);

        Cursor cursor = getContext().getContentResolver().query(CarsProvider.CONTENT_URI, DBCarsHelper.ALL_COLUMNS,null,null,null,null);
        String[] from = {DBCarsHelper.CARS_NICKNAME};
        int [] to = {android.R.id.text1};
        cursorAdapter = new SimpleCursorAdapter(getContext(), android.R.layout.simple_list_item_1, cursor, from, to, 0);
        getLoaderManager().initLoader(0, null, this);

        ListView list = (ListView) rootView.findViewById(R.id.cars);
        list.setAdapter(cursorAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), AddCarsActivity.class);
                Uri uri = Uri.parse(CarsProvider.CONTENT_URI + "/" + id);
                intent.putExtra(CarsProvider.CONTENT_ITEM_TYPE, uri);
                startActivityForResult(intent, EDITOR_REQUEST_CODE);
            }
        });

        return rootView;
    }

    private void insert() {
        ContentValues values = new ContentValues();
        values.put(DBCarsHelper.CARS_NICKNAME, "My Baby");
        Uri carsUri = getContext().getContentResolver().insert(CarsProvider.CONTENT_URI, values);
        Log.d("Cars", "Inserted car: " + carsUri.getLastPathSegment());
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(), CarsProvider.CONTENT_URI, null, null, null, null);
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
