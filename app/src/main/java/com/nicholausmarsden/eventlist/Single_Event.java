package com.nicholausmarsden.eventlist;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.nicholausmarsden.eventlist.CRUD.DeleteService;
import com.nicholausmarsden.eventlist.CRUD.UpdateService;
import com.nicholausmarsden.eventlist.Calender.TimeDate;

import java.util.Calendar;


/**
 * Created by nicho on 12/27/2017.
 */

public class Single_Event  extends AppCompatActivity {
    private static final String TAG = "Single Event";
    private Handler handler = new Handler();
    EditText eventTitle;
    EditText eventDesc;
    CheckBox eventFlag;
    TextView eventCalender;
    TimeDate timeDate;
    int id;
    int AlamCode = 7;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    String currentTitle;
    String currentDescription;
//    int flag;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_event);
        Initialize();
    }
    public void Initialize(){
        eventTitle = (EditText) findViewById(R.id.event_title);
        eventDesc = (EditText) findViewById(R.id.event_description_et);
      eventFlag = (CheckBox) findViewById(R.id.event_check_box);
        eventCalender = (TextView) findViewById(R.id.event_date_time_cal);
        timeDate= new TimeDate(this);
        setField();
    }
    public void setField(){
      Intent intent = this.getIntent() ;
        eventTitle.setText(   intent.getExtras().getString("NAME"));
        eventDesc.setText(intent.getExtras().getString("DESC"));
        eventCalender.setText(intent.getExtras().getString("TIME"));
        id= intent.getExtras().getInt("ID");
 //       flag =intent.getExtras().getInt("FLAG");

//if (flag == 1 ) eventFlag.setEnabled(true);
    }
    public void onClick(View view){
        ResultReceiver mResultReceiver = new Single_Event.CrudReceiver(null);
        switch(view.getId()){
            case R.id.event_save:
                currentDescription= eventDesc.getText().toString();
                currentTitle = eventTitle.getText().toString();

                Intent intent = new Intent( getApplicationContext() , UpdateService.class);
                intent.putExtra("NAME", currentTitle);
                intent.putExtra("DESC", currentDescription);
                intent.putExtra("TIME", timeDate.setText().toString());
                intent.putExtra("ID", id );
                intent.putExtra("receiver" , mResultReceiver);
                AlarmState();
                startService(intent);

                eventCalender.setText(timeDate.setText());
                Toast.makeText(this, "Save", Toast.LENGTH_SHORT).show();
            break;
            case R.id.event_delete:
                Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();
                Intent toDelete = new Intent(getApplicationContext(), DeleteService.class);
                toDelete.putExtra("ID",id);
                toDelete.putExtra("receiver" , mResultReceiver);
                startService(toDelete);
            break;
            case R.id.event_calender:
                timeDate.DateClick();
                Toast.makeText(this, "Calender", Toast.LENGTH_SHORT).show();
            break;
            case R.id.event_time:
                timeDate.TimeClick();
                Toast.makeText(this, "Time", Toast.LENGTH_SHORT).show();
            break;
            case R.id.event_check_box:
                if(eventFlag.isChecked()){
     //               flag = 1;
                }else {
     //              flag = 0;
                }
                break;
            default: break;
        }
    }
    private class CrudReceiver extends ResultReceiver{
        public CrudReceiver(Handler handler) {
            super(handler);
        }
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            Log.d(TAG, "Crud Receiver");
            if(resultCode == 10 && resultData != null){
                final String updateresult = resultData.getString("result");
                final String nameservice = resultData.getString("NAME");
                final String descservice = resultData.getString("DESC");
                final String timeservice = resultData.getString("TIME");
                final int flagservice = resultData.getInt("FLAG");
                handler.post(new Runnable() {
                    @Override
                    public void run() { // main
                        Log.d(TAG,"In HandlerforUpdate");
                        // set and times in main thread
                        eventCalender.setText(timeservice);
                        eventDesc.setText(descservice);
                        eventTitle.setText(nameservice);

                        if (flagservice == 1){
                            eventFlag.setChecked(true);
                        } else{
                            eventFlag.setChecked(false);
                        }
                    } // store item in main thread
                });
            }

            if(resultCode == 20 && resultData != null){
              final String deleteresult = resultData.getString("result");

                handler.post(new Runnable() {
                    @Override
                    public void run() { // main
                        Log.d(TAG,  deleteresult);
                        Single_Event.this.finish();
                    }
                });
            }
        }
    }
    //  SET ALARM
    private void setAlarm(Calendar date){
        Log.d(TAG,"setAlarm in ToDo");
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("DESC",currentDescription); // want the event save name
        intent.putExtra("NAME",currentTitle);
        pendingIntent = PendingIntent.getBroadcast(this, AlamCode,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager =(AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, date.getTimeInMillis(), pendingIntent);
    }

    public void AlarmState(){
        // set the alarm clock
        Calendar calInitial = Calendar.getInstance();
        if(timeDate.getCalender().compareTo(calInitial) <= 0) { // compare current to pick
            Toast.makeText(getApplicationContext(),
                    "You didn't set an appropriate date/time",
                    Toast.LENGTH_LONG).
                    show();
        }else{
            setAlarm(timeDate.getCalender());
            Toast.makeText(getApplicationContext(),
                    "alarm has been set",
                    Toast.LENGTH_SHORT).
                    show();
        }
    }


}
