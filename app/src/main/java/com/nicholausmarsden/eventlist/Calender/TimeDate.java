package com.nicholausmarsden.eventlist.Calender;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;

/**
 * Created by nicho on 12/28/2017.
 */

public class TimeDate {
    private static final String TAG =  "TimeDate";
    DateFormat formatDateTime = DateFormat.getDateTimeInstance();
    private Context mContext;
    private Calendar calender;
    public TimeDate(Context mContext) {Log.d(TAG, " Constructor");
    this.mContext = mContext;
        calender =Calendar.getInstance();
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            calender.set(Calendar.YEAR, year);
            calender.set(Calendar.MONTH, month);
            calender.set(Calendar.DAY_OF_MONTH, day);
            setText(); // update date
        }
    };
    // selecting on time
    TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener(){

        @Override
        public void onTimeSet(TimePicker timePicker, int h, int min) {
            calender.set(Calendar.HOUR_OF_DAY, h);
            calender.set(Calendar.MINUTE, min);
            calender.set(Calendar.SECOND, 0);
            setText();    // update time
        }
    };
    public void DateClick(){
        new DatePickerDialog(mContext,date,calender.get(Calendar.YEAR), calender.get(Calendar.MONTH),calender.get(Calendar.DAY_OF_MONTH)).show();
    }
    public void TimeClick(){
        new TimePickerDialog(mContext, time, calender.get(Calendar.HOUR_OF_DAY), calender.get(Calendar.MINUTE), false).show();

    }

    // return whats currently on the calender you select
    public Calendar getCalender(){
        return calender;
    }
    public String setText(){
      return  formatDateTime.format(calender.getTime()).toString();
    }





}
