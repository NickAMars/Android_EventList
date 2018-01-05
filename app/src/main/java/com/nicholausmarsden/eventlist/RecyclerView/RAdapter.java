package com.nicholausmarsden.eventlist.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nicholausmarsden.eventlist.CRUD.DeleteService;
import com.nicholausmarsden.eventlist.DATABASE.FieldName.EventFieldName;
import com.nicholausmarsden.eventlist.DATABASE.Object.Event;
import com.nicholausmarsden.eventlist.R;
import com.nicholausmarsden.eventlist.Single_Event;


/**
 * Created by nicho on 12/27/2017.
 */

public class RAdapter extends RecyclerView.Adapter<RVHolder> {
    private static final String TAG = "RAdapter";
    private Cursor cursor;
    private Context context;
    private String name;

    public RAdapter(Cursor cursor, Context context) {
        Log.d(TAG, "Constructor for RAapter");
        this.cursor = cursor;
        this.context = context;
    }

    @Override
    public RVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_event, parent, false));
    }

    @Override
    public void onBindViewHolder(RVHolder holder, int position) {
        final Event card_events = getEvents(position);
        holder.name.setText(card_events.getEventName());
        holder.description.setText(card_events.getEventDesc());
       // holder.image.setImageBitmap();



        holder.setItemOnClickListener(new ClickListener(){
                                          @Override
                                          public void onEventClick() {
                                              Toast.makeText(context, "small click", Toast.LENGTH_SHORT).show();
                                              Intent toSingle = new Intent(context, Single_Event.class);
                                              toSingle.putExtra("NAME", card_events.getEventName());
                                              toSingle.putExtra("DESC", card_events.getEventDesc());
                                              toSingle.putExtra("ID", card_events.getId());
                                              toSingle.putExtra("TIME", card_events.getEventTime());
                                              toSingle.putExtra("FLAG", card_events.getEventFlag());
                                              context.startActivity(toSingle);
                                          }
                                      },
                                       new LongClickListener() {
                                          @Override
                                          public void onLongClick() {
                                              Toast.makeText(context, "Long click", Toast.LENGTH_SHORT).show();
                                              Intent toDelete = new Intent(context, DeleteService.class);
                                              toDelete.putExtra("ID",card_events.getId());
                                              context.startService(toDelete);
                                          }
                                      }
        );

    }

    @Override
    public int getItemCount() {
        if (cursor != null) {
            return cursor.getCount();
        }
        return 0;
    }


    private Event getEvents(int position){
        cursor.moveToPosition(position);
        Event events = new Event();
        events.setId(cursor.getInt(cursor.getColumnIndex(EventFieldName.COL_ID)));
        events.setEventName(cursor.getString(cursor.getColumnIndex(EventFieldName.COL_NAME)));
        events.setEventDesc(cursor.getString(cursor.getColumnIndex(EventFieldName.COL_DES)));
        events.setEventFlag(cursor.getInt(cursor.getColumnIndex(EventFieldName.COL_FLAG)));
        events.setEventTime(cursor.getString(cursor.getColumnIndex(EventFieldName.COL_TIME)));
        return events;
    }

}
