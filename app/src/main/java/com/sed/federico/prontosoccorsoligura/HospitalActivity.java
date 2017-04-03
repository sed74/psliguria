package com.sed.federico.prontosoccorsoligura;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

public class HospitalActivity extends AppCompatActivity {

    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);
        TextView hospitalName = (TextView) findViewById(R.id.hospita_name);

        barChart = (BarChart) findViewById(R.id.barchart_waiting);

        ArrayList<BarEntry> barEntriesW = new ArrayList<>();
        ArrayList<BarEntry> barEntriesR = new ArrayList<>();
        Intent intent = getIntent();

        int ww = intent.getIntExtra(MainActivity.EXTRA_HOSPITAL_WW, 0);
        int gw = intent.getIntExtra(MainActivity.EXTRA_HOSPITAL_GW, 0);
        int yw = intent.getIntExtra(MainActivity.EXTRA_HOSPITAL_YW, 0);
        int rw = intent.getIntExtra(MainActivity.EXTRA_HOSPITAL_RW, 0);
        int wr = intent.getIntExtra(MainActivity.EXTRA_HOSPITAL_WR, 0);
        int gr = intent.getIntExtra(MainActivity.EXTRA_HOSPITAL_GR, 0);
        int yr = intent.getIntExtra(MainActivity.EXTRA_HOSPITAL_YR, 0);
        int rr = intent.getIntExtra(MainActivity.EXTRA_HOSPITAL_RR, 0);

        hospitalName.setText(intent.getStringExtra(MainActivity.EXTRA_HOSPITAL_NAME));
        barEntriesW.add(new BarEntry(0f, ww));
        barEntriesW.add(new BarEntry(1f, gw));
        barEntriesW.add(new BarEntry(2f, yw));
        barEntriesW.add(new BarEntry(3f, rw));

        barEntriesR.add(new BarEntry(0f, wr));
        barEntriesR.add(new BarEntry(1f, gr));
        barEntriesR.add(new BarEntry(2f, yr));
        barEntriesR.add(new BarEntry(3f, rr));

        BarDataSet setW = new BarDataSet(barEntriesW, "In Attesa");
        BarDataSet setR = new BarDataSet(barEntriesR, "In Visita");
        setW.setColors(new int[]{R.color.white, R.color.green, R.color.yellow, R.color.red},
                getBaseContext());
        setR.setColors(new int[]{R.color.white, R.color.green, R.color.yellow, R.color.red},
                getBaseContext());

        setW.setDrawValues(false);
        XAxis xAxis = barChart.getXAxis();

        xAxis.setValueFormatter(new
                IndexAxisValueFormatter(new String[]{String.valueOf(ww), String.valueOf(gw),
                String.valueOf(yw), String.valueOf(rw)}));
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setTextSize(14f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(4);

        BarData data = new BarData(setW, setR);

//        data.setBarWidth(0.9f); // set custom bar width
        barChart.setData(data);
        barChart.setFitBars(true); // make the x-axis fit exactly all bars
        barChart.setDrawGridBackground(false);

        Description desc = new Description();
        desc.setText("");
        barChart.setDescription(desc);
        barChart.setBackgroundColor(getResources().getColor(R.color.gray));
        barChart.invalidate(); // refresh
    }
}
