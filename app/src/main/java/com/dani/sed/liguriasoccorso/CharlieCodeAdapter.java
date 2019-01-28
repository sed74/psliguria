package com.dani.sed.liguriasoccorso;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by federico.marchesi on 13/04/2017.
 */

public class CharlieCodeAdapter extends RecyclerView.Adapter<CharlieCodeAdapter.ViewHolder> {
    private ArrayList<CharlieCode> mCharlieCodes;

    // Provide a suitable constructor (depends on the kind of dataset)
    public CharlieCodeAdapter(ArrayList<CharlieCode> charlieCodes) {
        mCharlieCodes = charlieCodes;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CharlieCodeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // create a new view
        ConstraintLayout v = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_charlie_code_element, parent, false);

        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mCharlieCode.setText(mCharlieCodes.get(position).getCode());
        holder.mCharlieCodeDesc.setText(mCharlieCodes.get(position).getDescr());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mCharlieCodes.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mCharlieCode;
        public TextView mCharlieCodeDesc;

        public ViewHolder(View v) {
            super(v);
            mCharlieCode = (TextView) v.findViewById(R.id.charlie_code);
            mCharlieCodeDesc = (TextView) v.findViewById(R.id.charlie_code_description);
        }
    }
}
