package com.nicholausmarsden.eventlist.RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.nicholausmarsden.eventlist.R;


/**
 * Created by nicho on 12/27/2017.
 */

public class RVHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
    private static final String TAG = "RecyclerView Holder";
    public final TextView name;
    public final TextView description;
    ClickListener EventClickListener;
    LongClickListener EventsLongClickListener;
   /* public final ImageView image;*/

    public RVHolder(View view){
        super(view);
        Log.d(TAG, "Constuctor of RVHolder");
        name = (TextView) view. findViewById(R.id.card_title);
        description = (TextView) view. findViewById(R.id.card_description);
     /*   image = (ImageView) view. findViewById(R.id.card_image);*/
     view.setOnClickListener(this);

     view.setOnLongClickListener(this);
     
    /* view.setOnLongClickListener(new View.OnLongClickListener() {
         @Override
         public boolean onLongClick(View view) {
             Toast.makeText(view.getContext(),"long click",Toast.LENGTH_SHORT).show();
             return false;
         }
     });*/
    }


    @Override
    public void onClick(View view) {
        this.EventClickListener.onEventClick();
    }


    void setItemOnClickListener (ClickListener EventClickListener, LongClickListener EventsLongClickListener) {
      this.EventClickListener= EventClickListener;
      this.EventsLongClickListener =EventsLongClickListener;
    }

    @Override
    public boolean onLongClick(View view) {
        this.EventsLongClickListener.onLongClick();
        return false;
    }
}
