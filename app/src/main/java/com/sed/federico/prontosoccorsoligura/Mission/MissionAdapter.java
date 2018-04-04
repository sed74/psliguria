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
package com.sed.federico.prontosoccorsoligura.Mission;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sed.federico.prontosoccorsoligura.Hospital;
import com.sed.federico.prontosoccorsoligura.R;

import java.util.List;

/**
 * An {@link MissionAdapter} knows how to create a list item layout for each earthquake
 * in the data source (a list of {@link Hospital} objects).
 * <p>
 * These list item layouts will be provided to an adapter view like ListView
 * to be displayed to the user.
 */
public class MissionAdapter extends ArrayAdapter<Mission> {

    /**
     * The part of the location string from the USGS service that we use to determine
     * whether or not there is a location offset present ("5km N of Cairo, Egypt").
     */
    private static final String LOCATION_SEPARATOR = " of ";

    private Context mContext;

    /**
     * Constructs a new {@link MissionAdapter}.
     *
     * @param context  of the app
     * @param missions is the list of earthquakes, which is the data source of the adapter
     */
    public MissionAdapter(Context context, List<Mission> missions) {
        super(context, 0, missions);
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
                    R.layout.mission_list_clickable, parent, false);
        }

        // Find the Mission at the given position in the list of earthquakes
        Mission currentMission = getItem(position);

        // Find the TextView with view ID magnitude
        TextView ambulance = (TextView) listItemView.findViewById(R.id.ambulance_no);
//        ambulance.setText(currentMission.getAmbulanceNo());

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable ambulanceCircle = (GradientDrawable) ambulance.getBackground();
        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getCodeColor(currentMission.getCode());
        // Set the color on the magnitude circle
        ambulanceCircle.setColor(magnitudeColor);

        TextView croceName = (TextView) listItemView.findViewById(R.id.pa_name);
        croceName.setText(currentMission.getPubblicaAssistenza());

        TextView ambulanceNo = (TextView) listItemView.findViewById(R.id.ambulance_no);
        ambulanceNo.setText(currentMission.getAmbulanceNo());

        TextView mission = (TextView) listItemView.findViewById(R.id.mission);
        mission.setText(currentMission.getMissionNo());

        TextView location = (TextView) listItemView.findViewById(R.id.location);
        location.setText(currentMission.getLocation());

        TextView pickupLocation = (TextView) listItemView.findViewById(R.id.pickup_location);
        pickupLocation.setText(currentMission.getPickUpLocation());

        TextView charlieCode = (TextView) listItemView.findViewById(R.id.charlie_code);
        charlieCode.setText(currentMission.getCharlie());

        TextView indiaCode = (TextView) listItemView.findViewById(R.id.india_code);
        indiaCode.setText(currentMission.getIndiaCode());

        TextView hospital = (TextView) listItemView.findViewById(R.id.hospital);
        hospital.setText(currentMission.getDestination());

        TextView missionTerminated = (TextView) listItemView.findViewById(R.id.mission_terminated);

        missionTerminated.setVisibility(currentMission.isMissionTerminated() ? View.VISIBLE : View.GONE);

        listItemView.setTag(false);

        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }

    private int getCodeColor(String code) {
        int emergencyCodeColorResourceId;
        switch (code.toLowerCase()) {
            case "verde":
                emergencyCodeColorResourceId = R.color.green;
                break;
            case "giallo":
                emergencyCodeColorResourceId = R.color.yellow;
                break;
            case "rosso":
                emergencyCodeColorResourceId = R.color.red;
                break;
            default:
                emergencyCodeColorResourceId = R.color.white;
                break;
        }
        return ContextCompat.getColor(getContext(), emergencyCodeColorResourceId);

    }

}
