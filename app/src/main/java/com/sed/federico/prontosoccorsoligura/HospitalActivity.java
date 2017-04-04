package com.sed.federico.prontosoccorsoligura;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

public class HospitalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    BarChart barChartWaiting;
    BarChart barChartRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);
        TextView hospitalName = (TextView) findViewById(R.id.hospita_name);
        TextView total = (TextView) findViewById(R.id.total);
        TextView lastUpdate = (TextView) findViewById(R.id.last_updated);

        barChartWaiting = (BarChart) findViewById(R.id.barchart_waiting);
        barChartRunning = (BarChart) findViewById(R.id.barchart_running);

        ArrayList<BarEntry> barEntriesW = new ArrayList<>();
        ArrayList<BarEntry> barEntriesR = new ArrayList<>();
        Intent intent = getIntent();

        int position = intent.getIntExtra(MainActivity.EXTRA_HOSPITAL_POSITION, 0);

        HospitalListCustom hospitals = MainActivity.getHospitals();

        Hospital hospital = hospitals.get(position);

        total.setText("Tot. " + String.valueOf(hospital.getTotal()));
        lastUpdate.setText(hospital.getLastUpdated());

        int ww = hospital.getWhiteWaiting();
        int gw = hospital.getGreenWaiting();
        int yw = hospital.getYellowWaiting();
        int rw = hospital.getRedWaiting();
        int wr = hospital.getWhiteRunning();
        int gr = hospital.getGreenRunning();
        int yr = hospital.getYellowRunning();
        int rr = hospital.getRedRunning();
        int obi = hospital.getObi();

        hospitalName.setText(hospital.getName());

        loadData(barChartWaiting, hospital, true);
        loadData(barChartRunning, hospital, false);
    }

    private void loadData(BarChart barChart, Hospital hospital, boolean isWaiting) {
        ArrayList<BarEntry> barEntries = new ArrayList<>();

        int white;
        int green;
        int yellow;
        int red;
        int obi = -1;

        if (isWaiting) {
            white = hospital.getWhiteWaiting();
            green = hospital.getGreenWaiting();
            yellow = hospital.getYellowWaiting();
            red = hospital.getRedWaiting();
        } else {
            white = hospital.getWhiteRunning();
            green = hospital.getGreenRunning();
            yellow = hospital.getYellowRunning();
            red = hospital.getRedRunning();
            obi = hospital.getObi();
        }
        barEntries.add(new BarEntry(0f, white));
        barEntries.add(new BarEntry(1f, green));
        barEntries.add(new BarEntry(2f, yellow));
        barEntries.add(new BarEntry(3f, red));

        if (!isWaiting) barEntries.add(new BarEntry(4f, obi));

        BarDataSet set = new BarDataSet(barEntries, "");

        if (isWaiting)
            set.setColors(new int[]{R.color.white, R.color.green, R.color.yellow, R.color.red},
                    getBaseContext());
        else set.setColors(new int[]{R.color.white, R.color.green, R.color.yellow, R.color.red,
                R.color.obi}, getBaseContext());

        set.setDrawValues(false);
        XAxis xAxis = barChart.getXAxis();
        formatXAxis(xAxis, white, green, yellow, red, obi);
        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setAxisMaximum((float) hospital.getMaxNo());
        yAxis.setDrawGridLines(false);

        Legend legend = barChart.getLegend();
        legend.setEnabled(false);
        barChart.setExtraBottomOffset(10f);
        BarData data = new BarData(set);
        populateChart(barChart, data);

    }

    private void formatXAxis(XAxis xAxis, int value1, int value2, int value3, int value4, int value5) {
        if (value5 == -1) {
            xAxis.setValueFormatter(new
                    IndexAxisValueFormatter(new String[]{String.valueOf(value1), String.valueOf(value2),
                    String.valueOf(value3), String.valueOf(value4)}));
            xAxis.setLabelCount(4);
        } else {
            xAxis.setValueFormatter(new
                    IndexAxisValueFormatter(new String[]{String.valueOf(value1), String.valueOf(value2),
                    String.valueOf(value3), String.valueOf(value4), String.valueOf(value5) + " OBI"}));
            xAxis.setLabelCount(5);
        }
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setTextSize(14f);
        xAxis.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.white));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
    }

    private void populateChart(BarChart barChart, BarData barData) {
        barChart.setData(barData);
        barChart.setFitBars(true); // make the x-axis fit exactly all bars
        barChart.setDrawGridBackground(false);

        Description desc = new Description();
        desc.setText("");
        barChart.setDescription(desc);
        barChart.setBackgroundColor(getResources().getColor(R.color.black));
        barChart.invalidate(); // refresh

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            // Handle the camera action
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_hospital, menu);
        return true;
    }
}
