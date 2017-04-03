package com.sed.federico.prontosoccorsoligura;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

public class HospitalActivity extends AppCompatActivity {

    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);

        TextView hospitalName = (TextView) findViewById(R.id.hospita_name);

        barChart = (BarChart) findViewById(R.id.barchart_waiting);

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        Intent intent = getIntent();

        hospitalName.setText(intent.getStringExtra(MainActivity.EXTRA_HOSPITAL_NAME));
        barEntries.add(new BarEntry(0f, intent.getIntExtra(MainActivity.EXTRA_HOSPITAL_WW, 0)));
        barEntries.add(new BarEntry(1f, intent.getIntExtra(MainActivity.EXTRA_HOSPITAL_GW, 0)));
        barEntries.add(new BarEntry(2f, intent.getIntExtra(MainActivity.EXTRA_HOSPITAL_YW, 0)));
        barEntries.add(new BarEntry(3f, intent.getIntExtra(MainActivity.EXTRA_HOSPITAL_RW, 0)));

        BarDataSet set = new BarDataSet(barEntries, "In Attesa");
        set.setColors(new int[]{R.color.white, R.color.green, R.color.yellow, R.color.red}, getBaseContext());
        set.setStackLabels(new String[]{"W", "G", "Y", "R"});


        BarData data = new BarData(set);
//        data.setBarWidth(0.9f); // set custom bar width
        barChart.setData(data);
        barChart.setFitBars(true); // make the x-axis fit exactly all bars
        barChart.setDrawGridBackground(false);
        barChart.invalidate(); // refresh
    }
}
