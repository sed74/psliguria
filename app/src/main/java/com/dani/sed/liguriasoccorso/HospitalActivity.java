package com.dani.sed.liguriasoccorso;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
        implements NavigationView.OnNavigationItemSelectedListener,
        LoaderManager.LoaderCallbacks<HospitalListCustom> {

    private static final int HOSPITAL_LOADER_ID = 2;
    private BarChart barChartWaiting;
    private BarChart barChartRunning;
    private HospitalAdapter mAdapter;

    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);

        barChartWaiting = (BarChart) findViewById(R.id.barchart_waiting);
        barChartRunning = (BarChart) findViewById(R.id.barchart_running);

        ArrayList<BarEntry> barEntriesW = new ArrayList<>();
        ArrayList<BarEntry> barEntriesR = new ArrayList<>();
        Intent intent = getIntent();

        mPosition = intent.getIntExtra(MainActivity.EXTRA_HOSPITAL_POSITION, 0);

//        HospitalListCustom hospitals = MainActivity.getHospitals();
        mAdapter = new HospitalAdapter(this, new HospitalListCustom());
//        createGraph(hospitals.get(mPosition));
        android.app.LoaderManager loaderManager = getLoaderManager();


        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).
        loaderManager.initLoader(HOSPITAL_LOADER_ID, null, this);
    }

    private void createGraph(Hospital hospital) {

        TextView hospitalName = (TextView) findViewById(R.id.hospita_name);
        TextView total = (TextView) findViewById(R.id.total);
        TextView lastUpdate = (TextView) findViewById(R.id.last_updated);
        TextView waitingLabel = (TextView) findViewById(R.id.waiting_label);
        TextView runningLabel = (TextView) findViewById(R.id.running_label);

        waitingLabel.setText(getString(R.string.waiting_label, hospital.getTotalWaiting()));
        runningLabel.setText(getString(R.string.running_label, hospital.getTotalRunning(), hospital.getObi()));

        total.setText(getString(R.string.total_label, hospital.getTotal()));
        lastUpdate.setText(hospital.getLastUpdated());

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
        boolean hasObi = hospital.getHasOBI();

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
            if (hasObi) obi = hospital.getObi();
        }
        barEntries.add(new BarEntry(0f, white));
        barEntries.add(new BarEntry(1f, green));
        barEntries.add(new BarEntry(2f, yellow));
        barEntries.add(new BarEntry(3f, red));

        if (!isWaiting && hasObi) barEntries.add(new BarEntry(4f, obi));

        BarDataSet set = new BarDataSet(barEntries, "");

        if (isWaiting || !hasObi)
            set.setColors(new int[]{R.color.white, R.color.green, R.color.yellow, R.color.red},
                    getBaseContext());
        else set.setColors(new int[]{R.color.white, R.color.green, R.color.yellow, R.color.red,
                R.color.obi}, getBaseContext());

        set.setDrawValues(false);
        XAxis xAxis = barChart.getXAxis();
        formatXAxis(xAxis, white, green, yellow, red, obi, hasObi);
        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setAxisMaximum((float) hospital.getMaxNo());
        yAxis.setDrawGridLines(false);

        Legend legend = barChart.getLegend();
        legend.setEnabled(false);
        barChart.setExtraBottomOffset(10f);
        BarData data = new BarData(set);
        populateChart(barChart, data);

    }

    private void formatXAxis(XAxis xAxis, int value1, int value2, int value3, int value4,
                             int value5, boolean hasOBI) {
        if (value5 == -1 || !hasOBI) {
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
            refreshView();
            // Handle the camera action
        } else if (id == R.id.action_force_refresh) {
            QueryUtils.callWebAPI(MainActivity.DATI_PS_FORCE_REQUEST_URL);
            refreshView();
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

    @Override
    public android.content.Loader<HospitalListCustom> onCreateLoader(int i, Bundle bundle) {

        View progress = findViewById(R.id.progressBar);
        progress.setVisibility(View.VISIBLE);

        String url = MainActivity.DATI_PS_REQUEST_URL;
        Uri baseUri = Uri.parse(url);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        return new HospitalLoader(this, uriBuilder.toString());
    }


    @Override
    public void onLoadFinished(android.content.Loader<HospitalListCustom> loader, HospitalListCustom hospitals) {
        // Hide loading indicator because the data has been loaded
//        View loadingIndicator = findViewById(R.id.loading_indicator);
//        loadingIndicator.setVisibility(View.GONE);

        TextView text = (TextView) findViewById(R.id.text);
        View progress = findViewById(R.id.progressBar);

        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (hospitals != null && !hospitals.isEmpty()) {
            mAdapter.addAll(hospitals);
//            text.setVisibility(View.GONE);
            createGraph(hospitals.get(mPosition));
            progress.setVisibility(View.GONE);
//            mHospitals = hospitals;
        } else {
//            text.setText(R.string.no_data_display);
//            text.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onLoaderReset(Loader<HospitalListCustom> loader) {

    }

    private void refreshView() {

//        View loadingIndicator = findViewById(R.id.loading_indicator);
//        TextView text = (TextView) findViewById(R.id.text);
//        text.setVisibility(View.INVISIBLE);
//        loadingIndicator.setVisibility(View.VISIBLE);

        // Restart the loader to requery the USGS as the query settings have been updated
        getLoaderManager().restartLoader(HOSPITAL_LOADER_ID, null, HospitalActivity.this);

    }
}
