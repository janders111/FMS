package com.example.jordan.familymap.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jordan.familymap.R;
import com.example.jordan.familymap.model.FilterManager;

import java.util.ArrayList;

public class FiltersAdapter extends RecyclerView.Adapter<FiltersAdapter.ViewHolder> {
    private ArrayList<String> eventTypes;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public RelativeLayout mRelativeLayout;
        public Switch showType;
        TextView filterText;

        public ViewHolder(RelativeLayout v) {
            super(v);
            filterText = v.findViewById(R.id.filterText);
            showType = v.findViewById(R.id.filterSwitch);
            mRelativeLayout = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public FiltersAdapter(ArrayList<String> eventTypesTmp) {
        eventTypes = eventTypesTmp;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FiltersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_filters, parent, false);

        FiltersAdapter.ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(FiltersAdapter.ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //holder.mRelativeLayout.setText(mDataset[position]);
        holder.filterText.setText("Show " + eventTypes.get(position));
        holder.showType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(buttonView.getContext(), "test " + position, Toast.LENGTH_LONG).show();
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return eventTypes.size();
    }
}