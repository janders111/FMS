package com.example.jordan.familymap.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jordan.familymap.R;
import com.example.jordan.familymap.model.MainModel;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import java.util.ArrayList;

import model.Event;
import model.Person;

/**
 * Adapter that can be used for Events, and Persons as long as we do not have to print out relationships (mother, father, etc.)
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private ArrayList<Object> resultsArray = new ArrayList<>();
    Context mContext;
    //static public LinearLayout layout;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout mListener;
        public TextView firstLine;
        public TextView secondLine;
        public ImageView genderIconView;
        // each data item is just a string in this case
        public LinearLayout mLayout;
        public ViewHolder(LinearLayout v) {
            super(v);
            mListener = (LinearLayout) v.findViewById(R.id.eventAdaptersTextView);
            firstLine = v.findViewById(R.id.topTextAdapter);
            secondLine = v.findViewById(R.id.bottomTextAdapter);
            genderIconView = (ImageView) v.findViewById(R.id.eventIcon);
            mLayout = v;
            //layout = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public SearchAdapter(ArrayList<Object> resultsArrayTmp, Context contextTmp) {
        resultsArray = resultsArrayTmp;
        mContext = contextTmp;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_events, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //holder.mTextView.setText(mDataset[position]);
        Object result = resultsArray.get(position);
        if(result.getClass().equals(Event.class)) {
            final Event e = (Event) result;
            Person p = MainModel.getPersonIDtoPerson().get(e.getPersonID());

            holder.firstLine.setText(e.getEventType() + ": " + e.getCity() + ", " + e.getCountry() + " (" + e.getYear() + ")");
            holder.secondLine.setText(p.getFirstName() + " " + p.getLastName());

            Drawable eventIcon;
            eventIcon = new IconDrawable(mContext, FontAwesomeIcons.fa_location_arrow)
                    .colorRes(R.color.gray).sizeDp(40);
            holder.genderIconView.setImageDrawable(eventIcon);


            holder.mListener.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(e != null){
                        Intent intent = new Intent(mContext, EventActivity.class);
                        intent.putExtra("currentEvent", e.getEventID());
                        mContext.startActivity(intent);
                    }
                }
            });
        }
        if(result.getClass().equals(Person.class)) {
            final Person p = (Person) result;

            holder.firstLine.setText(p.getFirstName() + " " + p.getLastName());
            holder.secondLine.setText("Family Member");

            Drawable genderIcon;
            if(p.getGender().equals("m")) {
                genderIcon = new IconDrawable(mContext, FontAwesomeIcons.fa_male)
                        .colorRes(R.color.colorMale).sizeDp(40);
            }
            else {
                genderIcon = new IconDrawable(mContext, FontAwesomeIcons.fa_female)
                        .colorRes(R.color.colorFemale).sizeDp(40);
            }
            holder.genderIconView.setImageDrawable(genderIcon);

            holder.mListener.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(p != null){
                        Intent intent = new Intent(mContext, PersonInfoActivity.class);
                        intent.putExtra("currentPerson", p.getPersonID());
                        mContext.startActivity(intent);
                    }
                }
            });
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return resultsArray.size();
    }
}