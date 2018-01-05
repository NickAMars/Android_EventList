package com.nicholausmarsden.eventlist.CRUD;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.nicholausmarsden.eventlist.DATABASE.EventContentProvider;
import com.nicholausmarsden.eventlist.DATABASE.FieldName.EventFieldName;


/**
 * Created by nicho on 12/28/2017.
 */

public class InsertService extends IntentService {
    private static final String TAG = "InsertService";
    public InsertService() {
        super("INSERT");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try {
            Thread.sleep(1000); // Wait 1s
            ContentValues cv = new ContentValues();
            cv.put(EventFieldName.COL_NAME, intent.getStringExtra("NAME")); // puts username with column name
            cv.put(EventFieldName.COL_DES, intent.getStringExtra("DESC"));
            cv.put(EventFieldName.COL_TIME, "");
            cv.put(EventFieldName.COL_FLAG, 0);

            Uri uri = Uri.parse(EventContentProvider.CONTENT_URI.toString());
            Log.d(TAG, uri.toString());
            getContentResolver().insert(EventContentProvider.CONTENT_URI, cv);
        }catch(Exception e){ Log.d(TAG, "Error for insert");}
    }
}
