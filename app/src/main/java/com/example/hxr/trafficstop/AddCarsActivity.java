package com.example.hxr.trafficstop;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class AddCarsActivity extends AppCompatActivity{

    private String action;

    private String carsFilter;
    private String oldlicensePlate;
    private String oldmake;
    private String oldmodel;
    private String oldyear;
    private String oldcolor;
    private String oldnickName;

    private EditText licensePlate;
    private EditText make;
    private EditText model;
    private EditText year;
    private EditText color;
    private EditText nickName;

    private ListView list;
    private CursorAdapter cursorAdapter;

    public MyCustomObjectListener mCallback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cars);

        licensePlate = (EditText) findViewById(R.id.licensePlate);
        make = (EditText) findViewById(R.id.make);
        model = (EditText) findViewById(R.id.model);
        year = (EditText) findViewById(R.id.year);
        color = (EditText) findViewById(R.id.color);
        nickName = (EditText) findViewById(R.id.nickName);

        Intent intent = getIntent();
        Uri uri = intent.getParcelableExtra(CarsProvider.CONTENT_ITEM_TYPE);

        if (uri == null){
            action = Intent.ACTION_INSERT;
            setTitle(getString(R.string.add_new_car));
        }
        else{
            action = Intent.ACTION_EDIT;
            setTitle(getString(R.string.update_car));
            carsFilter = DBCarsHelper.CARS_ID + "=" + uri.getLastPathSegment();

            Cursor cursor = getContentResolver().query(uri, DBCarsHelper.ALL_COLUMNS, carsFilter, null, null);
            cursor.moveToFirst();
            oldlicensePlate = cursor.getString(cursor.getColumnIndex(DBCarsHelper.CARS_LICENSE));
            oldmake = cursor.getString(cursor.getColumnIndex(DBCarsHelper.CARS_MAKE));
            oldmodel = cursor.getString(cursor.getColumnIndex(DBCarsHelper.CARS_MODEL));
            oldyear = cursor.getString(cursor.getColumnIndex(DBCarsHelper.CARS_YEAR));
            oldcolor = cursor.getString(cursor.getColumnIndex(DBCarsHelper.CARS_COLOR));
            oldnickName = cursor.getString(cursor.getColumnIndex(DBCarsHelper.CARS_NICKNAME));

            licensePlate.setText(oldlicensePlate);
            make.setText(oldmake);
            model.setText(oldmodel);
            year.setText(oldyear);
            color.setText(oldcolor);
            nickName.setText(oldnickName);

            licensePlate.requestFocus();
            make.requestFocus();
            model.requestFocus();
            year.requestFocus();
            color.requestFocus();
            nickName.requestFocus();
        }

        list = (ListView) findViewById(R.layout.fragment_home);

//        mCallback = new MyCustomObjectListener() {
//            @Override
//            public void RefreshList() {
//
//            }
//        };
    }

    public void setOnEventListener(MyCustomObjectListener listener) {
        mCallback = listener;
    }

//    public interface MyCustomObjectListener {
//         void RefreshList();
//    }



    public void addCars(View view){
        String newLicense = licensePlate.getText().toString().trim();
        String newmake= make.getText().toString().trim();
        String newmodel = model.getText().toString().trim();
        String newyear = year.getText().toString().trim();
        String newcolor= color.getText().toString().trim();
        String newnickName= nickName.getText().toString().trim();

        switch (action){
            case Intent.ACTION_INSERT:
                if (newLicense.length() == 0 || newmake.length() == 0 || newmodel.length() == 0 || newyear.length() == 0 || newcolor.length() == 0 || newnickName.length() == 0){
                    setResult(RESULT_CANCELED);
                }
                else{
                    insertNote(newLicense, newmake, newmodel, newyear, newcolor, newnickName );
                }
                break;
            case Intent.ACTION_EDIT:
                if (newLicense.length() == 0 && newmake.length() == 0 && newmodel.length() == 0 && newyear.length() == 0 && newcolor.length() == 0 && newnickName.length() == 0){
                    deleteNote();
                }
                else if (oldlicensePlate.equals(newLicense) && oldmake.equals(newmake) && oldmodel.equals(newmodel) && oldyear.equals(newyear) && oldcolor.equals(newcolor) && oldnickName.equals(newnickName)){
                    setResult(RESULT_CANCELED);
                }
                else{
                    updateNote(newLicense, newmake, newmodel, newyear, newcolor, newnickName);
                }
        }



//        CursorAdapter ca =  (CursorAdapter) list.getAdapter();
//        ca.notifyDataSetChanged();
//        ((BaseAdapter)list.getAdapter()).notifyDataSetChanged();


        Intent intent = new Intent();
        setResult(RESULT_OK, intent);

//        mCallback.RefreshList();

        finish();
    }

    private void updateNote(String newLicense, String newmake, String newmodel, String newyear, String newcolor, String newnickName) {
        ContentValues values = new ContentValues();
        values.put(DBCarsHelper.CARS_LICENSE, newLicense);
        values.put(DBCarsHelper.CARS_MAKE, newmake);
        values.put(DBCarsHelper.CARS_MODEL, newmodel);
        values.put(DBCarsHelper.CARS_YEAR, newyear);
        values.put(DBCarsHelper.CARS_COLOR, newcolor);
        values.put(DBCarsHelper.CARS_NICKNAME, newnickName);
        getContentResolver().update(CarsProvider.CONTENT_URI, values, carsFilter, null);
        setResult(RESULT_OK);
    }

    private void insertNote(String newLicense, String newmake, String newmodel, String newyear, String newcolor, String newnickName) {
        ContentValues values = new ContentValues();
        values.put(DBCarsHelper.CARS_LICENSE, newLicense);
        values.put(DBCarsHelper.CARS_MAKE, newmake);
        values.put(DBCarsHelper.CARS_MODEL, newmodel);
        values.put(DBCarsHelper.CARS_YEAR, newyear);
        values.put(DBCarsHelper.CARS_COLOR, newcolor);
        values.put(DBCarsHelper.CARS_NICKNAME, newnickName);
        getContentResolver().insert(CarsProvider.CONTENT_URI, values);
        setResult(RESULT_OK);
    }

    private void deleteNote() {
        getContentResolver().delete(CarsProvider.CONTENT_URI, carsFilter, null);
        setResult(RESULT_OK);
        finish();
    }




//    public void addCars(View view) {
//        ContentValues values = new ContentValues();
//        values.put(DBCarsHelper.CARS_LICENSE, licensePlate.getText().toString());
//        values.put(DBCarsHelper.CARS_MAKE, make.getText().toString());
//        values.put(DBCarsHelper.CARS_MODEL, model.getText().toString());
//        values.put(DBCarsHelper.CARS_YEAR, year.getText().toString());
//        values.put(DBCarsHelper.CARS_COLOR, color.getText().toString());
//        values.put(DBCarsHelper.CARS_NICKNAME, nickName.getText().toString());
//        Uri carsUri = getContentResolver().insert(CarsProvider.CONTENT_URI, values);
//        Log.d("Cars", "AddCars" );
//    }
}
