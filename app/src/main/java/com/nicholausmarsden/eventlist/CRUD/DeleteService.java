package com.nicholausmarsden.eventlist.CRUD;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.util.Log;

import com.nicholausmarsden.eventlist.DATABASE.EventContentProvider;

/**
 * Created by nicho on 12/28/2017.
 */

public class DeleteService extends IntentService {
    private static final String TAG = "DeleteService";
    public DeleteService() {
        super("DELETE");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        int id =  intent.getExtras().getInt("ID");
        ResultReceiver resultReceiver = intent.getParcelableExtra("receiver");

        try {
            Thread.sleep(1000); // wait 1s
            Uri uri = Uri.parse(EventContentProvider.CONTENT_URI + "/" + id);
            getContentResolver().delete(uri, null, null);

        }catch (Exception e){Log.d(TAG, " Error with Delete");}

        Bundle bundle = new Bundle();
        bundle.putString("result","Success full delete item");
        Log.d(TAG," find out : " + resultReceiver);
        if (resultReceiver != null) {
            resultReceiver.send(20, bundle);
        }
    }
}
