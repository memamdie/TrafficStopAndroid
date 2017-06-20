package com.example.hxr.trafficstop;

/**
 * Created by Isabel on 6/15/17.
 */

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class Home extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor>
{
    private CursorAdapter cursorAdapter;
    private MyCustomObjectListener callback;
    public ListView list;
    public View rootView;
    private static final int EDITOR_REQUEST_CODE = 1001;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_home, container, false);


        setObjects();

        cursorAdapter.notifyDataSetChanged();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), SendPhotosActivity.class);
                Uri uri = Uri.parse(CarsProvider.CONTENT_URI + "/" + id);
                intent.putExtra(CarsProvider.CONTENT_ITEM_TYPE, uri);
                startActivityForResult(intent, EDITOR_REQUEST_CODE);
            }
        });

//        Button b = (Button) rootView.findViewById(R.id.updateList);
//        b.setOnClickListener(this);

        return rootView;
    }

    private void setObjects() {
        Cursor cursor = getContext().getContentResolver().query(CarsProvider.CONTENT_URI, DBCarsHelper.ALL_COLUMNS,null,null,null,null);
        String[] from = {DBCarsHelper.CARS_NICKNAME};
        int [] to = {android.R.id.text1};
        cursorAdapter = new SimpleCursorAdapter(getContext(), android.R.layout.simple_list_item_1, cursor, from, to, 0);
        getLoaderManager().initLoader(0, null, this);

        list = (ListView) rootView.findViewById(R.id.cars);
        list.setAdapter(cursorAdapter);


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


    public void RefreshList(View view) {
        Log.d("Home Fragment", "In refresh list");
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.updateList:
//                Log.d("Home Fragment", "In refresh list 2");
//                setObjects();
//                break;
//        }
//    }
//    @Override
//    public void RefershList() {
//        Log.d("Home Fragment", "In refresh list");
//    }
}
