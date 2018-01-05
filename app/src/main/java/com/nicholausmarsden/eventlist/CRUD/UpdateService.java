package com.nicholausmarsden.eventlist.CRUD;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.nicholausmarsden.eventlist.DATABASE.EventContentProvider;
import com.nicholausmarsden.eventlist.DATABASE.FieldName.EventFieldName;


/**
 * Created by nicho on 12/28/2017.
 */

public class UpdateService extends IntentService{
    private static final String TAG = "UpdateService" ;

    public UpdateService() {
        super("UPDATE");
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String name = intent.getExtras().getString("NAME");
        String des = intent.getExtras().getString("DESC");
        String time = intent.getExtras().getString("TIME");
        int id_event =  intent.getExtras().getInt("ID");
        int flag = intent.getExtras().getInt("FLAG");
        ResultReceiver resultReceiver = intent.getParcelableExtra("receiver");
        try {
            Thread.sleep(500);
            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(des)) {
                ContentValues cv = new ContentValues();
                cv.put(EventFieldName.COL_NAME, name);
                cv.put(EventFieldName.COL_DES, des);
                cv.put(EventFieldName.COL_TIME, time);
                cv.put(EventFieldName.COL_FLAG, flag);
                Uri uri = Uri.parse(EventContentProvider.CONTENT_URI + "/" + id_event);
                Log.d(TAG,uri.toString());
                getContentResolver().update(uri, cv, null, null);
            }
        }catch (Exception e){e.printStackTrace();}
        Bundle bundle = new Bundle();
        bundle.putString("result","Success full store item");
        bundle.putString("NAME", name);
        bundle.putString("DESC", des);
        bundle.putString("TIME", time);
        bundle.putInt("FLAG", flag);
        resultReceiver.send(10, bundle);
    }
}


