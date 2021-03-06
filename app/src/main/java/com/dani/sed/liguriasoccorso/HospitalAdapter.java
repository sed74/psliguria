/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dani.sed.liguriasoccorso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * An {@link HospitalAdapter} knows how to create a list item layout for each earthquake
 * in the data source (a list of {@link Hospital} objects).
 *
 * These list item layouts will be provided to an adapter view like ListView
 * to be displayed to the user.
 */
public class HospitalAdapter extends ArrayAdapter<Hospital> {

    /**
     * The part of the location string from the USGS service that we use to determine
     * whether or not there is a location offset present ("5km N of Cairo, Egypt").
     */
    private static final String LOCATION_SEPARATOR = " of ";
    Context mContext;

    /**
     * Constructs a new {@link HospitalAdapter}.
     *
     * @param context of the app
     * @param hospitals is the list of earthquakes, which is the data source of the adapter
     */
    public HospitalAdapter(Context context, List<Hospital> hospitals) {
        super(context, 0, hospitals);
        mContext = context;
    }

    /**
     * Returns a list item view that displays information about the earthquake at the given position
     * in the list of earthquakes.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.hospital_entry_constraint2, parent, false);
        }

        // Find the earthquake at the given position in the list of earthquakes
        Hospital currentHospital = getItem(position);

        // Find the TextView with view ID magnitude
        TextView name = (TextView) listItemView.findViewById(R.id.hospital_name);
        name.setText(currentHospital.getName());

        TextView lastUpdated = (TextView) listItemView.findViewById(R.id.last_updated);
        lastUpdated.setText(currentHospital.getLastUpdated());

        TextView ww = (TextView) listItemView.findViewById(R.id.waiting_white);
        ww.setText(String.valueOf(currentHospital.getWhiteWaiting()));

        TextView wg = (TextView) listItemView.findViewById(R.id.waiting_green);
        wg.setText(String.valueOf(currentHospital.getGreenWaiting()));

        TextView wy = (TextView) listItemView.findViewById(R.id.waiting_yellow);
        wy.setText(String.valueOf(currentHospital.getYellowWaiting()));

        TextView wr = (TextView) listItemView.findViewById(R.id.waiting_red);
        wr.setText(String.valueOf(currentHospital.getRedWaiting()));

        TextView rw = (TextView) listItemView.findViewById(R.id.running_white);
        rw.setText(String.valueOf(currentHospital.getWhiteRunning()));

        TextView rg = (TextView) listItemView.findViewById(R.id.running_green);
        rg.setText(String.valueOf(currentHospital.getGreenRunning()));

        TextView ry = (TextView) listItemView.findViewById(R.id.running_yellow);
        ry.setText(String.valueOf(currentHospital.getYellowRunning()));

        TextView rr = (TextView) listItemView.findViewById(R.id.running_red);
        rr.setText(String.valueOf(currentHospital.getRedRunning()));

        TextView total = (TextView) listItemView.findViewById(R.id.total_people);
        total.setText(mContext.getString(R.string.total_people, currentHospital.getTotal()));

        TextView waitLabel = (TextView) listItemView.findViewById(R.id.waiting_label);
        waitLabel.setText(mContext.getString(R.string.total_waiting, currentHospital.getTotalWaiting()));

        TextView runLabel = (TextView) listItemView.findViewById(R.id.running_label);
        runLabel.setText(mContext.getString(R.string.total_running, currentHospital.getTotalRunning()));

        TextView obi = (TextView) listItemView.findViewById(R.id.obi);
        if (currentHospital.getHasOBI()) {
            obi.setText(String.valueOf(currentHospital.getObi()));
        } else {
            TextView obiLabel = (TextView) listItemView.findViewById(R.id.obi_label);
            obiLabel.setVisibility(View.GONE);
            obi.setVisibility(View.GONE);
            TextView text1 = (TextView) listItemView.findViewById(R.id.textView6);
            text1.setVisibility(View.GONE);
        }

        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }


    /**
     * Return the formatted magnitude string showing 1 decimal place (i.e. "3.2")
     * from a decimal magnitude value.
     */
    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }
}
