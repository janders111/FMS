package com.example.jordan.familymap.ui;

import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jordan.familymap.R;
import com.example.jordan.familymap.model.MainModel;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.ArrayList;
import java.util.Comparator;

import model.Event;
import model.Person;

/**
 * Adapter that can be used for Persons when we have to print out relationships (mother, father, etc.)
 * Otherwise, just use the search adapter which is universal.
 */
public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {
    private ArrayList<Person> family;
    private ArrayList<String> relationshipTypes;
    Context mContext;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public LinearLayout mListener;
        public LinearLayout mLinearLayout;
        public TextView firstLine;
        public TextView secondLine;
        public ImageView genderIconView;

        public ViewHolder(LinearLayout v) {
            super(v);
            genderIconView = (ImageView) v.findViewById(R.id.genderImagePersonAdapter);
            mListener = (LinearLayout) v.findViewById(R.id.personAdapter);
            firstLine = v.findViewById(R.id.topTextPersonAdapter);
            secondLine = v.findViewById(R.id.bottomTextPersonAdapter);
            mLinearLayout = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public PersonAdapter(ArrayList<Person> familyTmp, ArrayList<String> relationshipTypesTmp, Context contextTmp) {
        relationshipTypes = relationshipTypesTmp;
        family = familyTmp;
        mContext = contextTmp;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PersonAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_person, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(PersonAdapter.ViewHolder holder, final int position) {
        final Person p = family.get(position);
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.firstLine.setText(p.getFirstName() + " " + p.getLastName());
        holder.secondLine.setText(relationshipTypes.get(position));

        //set male female icon. DOES NOT WORK BECAUSE THE GETVIEWBYID RETURNS NULL
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

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return family.size();
    }
}