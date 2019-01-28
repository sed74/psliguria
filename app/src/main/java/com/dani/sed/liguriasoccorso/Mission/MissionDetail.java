package com.dani.sed.liguriasoccorso.Mission;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.dani.sed.liguriasoccorso.R;

public class MissionDetail extends AppCompatActivity {

    public static final String EXTRA_PA_NAME = "pa_name";
    public static final String EXTRA_MISSION_NO = "mission_no";
    public static final String EXTRA_LOCATION = "location";
    public static final String EXTRA_PICKUP_LOCATION = "pickup_location";
    public static final String EXTRA_CHARLIE_CODE = "charlie_code";
    public static final String EXTRA_INDIA_CODE = "india_code";
    public static final String EXTRA_DEST_HOSPITAL = "dest_hospital";
    public static final String EXTRA_AMBULANCE_NO = "ambulance_no";
    public static final String EXTRA_CODE = "extra_code";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mission_detail);
        Intent i = getIntent();

        TextView paName = (TextView) findViewById(R.id.pa_name);
        paName.setText(i.getStringExtra(EXTRA_PA_NAME));

        TextView MissionNO = (TextView) findViewById(R.id.mission_no);
        MissionNO.setText(i.getStringExtra(EXTRA_MISSION_NO));

        TextView location = (TextView) findViewById(R.id.location);
        location.setText(i.getStringExtra(EXTRA_LOCATION));

        TextView pickup = (TextView) findViewById(R.id.pickup_location);
        pickup.setText(i.getStringExtra(EXTRA_PICKUP_LOCATION));

        TextView charlieCode = (TextView) findViewById(R.id.charlie_code);
        charlieCode.setText(i.getStringExtra(EXTRA_CHARLIE_CODE));

        TextView indiaCode = (TextView) findViewById(R.id.india_code);
        indiaCode.setText(i.getStringExtra(EXTRA_INDIA_CODE));

        TextView destHospital = (TextView) findViewById(R.id.dest_hospital);
        destHospital.setText(i.getStringExtra(EXTRA_DEST_HOSPITAL));

        TextView ambulanceNo = (TextView) findViewById(R.id.ambulance_no);
        ambulanceNo.setText(i.getStringExtra(EXTRA_AMBULANCE_NO));

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) ambulanceNo.getBackground();
        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getCodeColor(i.getStringExtra(EXTRA_CODE));
        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

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
        return ContextCompat.getColor(getBaseContext(), emergencyCodeColorResourceId);

    }

}
