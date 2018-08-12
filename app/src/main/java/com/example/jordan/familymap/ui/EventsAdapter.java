package com.example.jordan.familymap.ui;

import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jordan.familymap.R;
import com.example.jordan.familymap.model.MainModel;

import java.util.ArrayList;
import java.util.Comparator;

import model.Event;
import model.Person;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {
    private ArrayList<Event> lifeEvents;
    Context mContext;
    Comparator<Event> eventComparator = new Comparator<Event>() {
        public int compare(Event e1, Event e2) {
            return e1.getYear() - e2.getYear();
        }
    };
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public LinearLayout mListener;
        public LinearLayout mLinearLayout;
        public TextView firstLine;
        public TextView secondLine;

        public ViewHolder(LinearLayout v) {
            super(v);
            mListener = (LinearLayout) v.findViewById(R.id.eventAdaptersTextView);
            firstLine = v.findViewById(R.id.topTextAdapter);
            secondLine = v.findViewById(R.id.bottomTextAdapter);
            mLinearLayout = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public EventsAdapter(ArrayList<Event> lifeEventsTmp, Context contextTmp) {
        lifeEvents = lifeEventsTmp;
        mContext = contextTmp;
        lifeEvents.sort(eventComparator);//sort
    }

    // Create new views (invoked by the layout manager)
    @Override
    public EventsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_events, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(EventsAdapter.ViewHolder holder, final int position) {
        final Event e = lifeEvents.get(position);
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Person p = MainModel.getPersonIDtoPerson().get(e.getPersonID());

        holder.firstLine.setText(e.getEventType() + ": " + e.getCity() + ", " + e.getCountry() + " (" + e.getYear() + ")");
        holder.secondLine.setText(p.getFirstName() + " " + p.getLastName());

        holder.mListener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //use the
                System.out.println("CLICKED AN EVENT");
                if(e != null){
                    Intent intent = new Intent(mContext, EventActivity.class);
                    intent.putExtra("currentEvent", e.getEventID());
                    mContext.startActivity(intent);
                }
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return lifeEvents.size();
    }
}