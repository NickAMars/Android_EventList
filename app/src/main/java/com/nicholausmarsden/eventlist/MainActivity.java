package com.nicholausmarsden.eventlist;


import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.nicholausmarsden.eventlist.CRUD.InsertService;
import com.nicholausmarsden.eventlist.DATABASE.EventContentProvider;
import com.nicholausmarsden.eventlist.RecyclerView.RAdapter;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = " MainActivity";
    TextInputEditText NameEditText;
    TextInputEditText DescEditText;
    Button SaveBtn;
    Button CancelBtn;
    private RecyclerView recyclerview;
    private RAdapter recAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerview = (RecyclerView) findViewById(R.id.recycle_view);
        loadData();
    }

    public void OnClick(View v) {
        switch (v.getId()){
            case R.id.fab_dialog:   ActivateDialog();   break;
            default: break;
        }
    }


    public void ActivateDialog(){
        final AlertDialog.Builder Alert_dialog = new AlertDialog.Builder(MainActivity.this);
        final View dialog_view = getLayoutInflater().inflate(R.layout.dialog_add_card,null);
        Alert_dialog.setView(dialog_view);
        final AlertDialog dialog = Alert_dialog.create();


        NameEditText =(TextInputEditText) dialog_view.findViewById(R.id.dialog_name_et);
        DescEditText =(TextInputEditText) dialog_view.findViewById(R.id.dialog_description_et);
        SaveBtn = (Button) dialog_view.findViewById(R.id.dialog_save_button);
        CancelBtn = (Button) dialog_view.findViewById(R.id.dialog_cancel_button);

        SaveBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"SAVE", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), InsertService.class);
                intent.putExtra("NAME",NameEditText.getText().toString());
                intent.putExtra("DESC",DescEditText.getText().toString());
                startService(intent);
                NameEditText.setText("");
                DescEditText.setText("");
            }
        });

        CancelBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                dialog.cancel();
                Toast.makeText(getApplicationContext(),"Cancel", Toast.LENGTH_SHORT).show();
            }
        });
        // Very important
        dialog.show();
    }









    public void loadData(){
        getSupportLoaderManager().initLoader(0,null,this);
    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d(TAG, "onCreateLoader");
        CursorLoader cursorLoader = new CursorLoader(this, EventContentProvider.CONTENT_URI,null,null,null,null);
        return cursorLoader;
    }
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        recAdapter = new RAdapter(data, this);
        recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        recyclerview.setAdapter(recAdapter);
    }
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        recyclerview.setAdapter(null);
    }


}
