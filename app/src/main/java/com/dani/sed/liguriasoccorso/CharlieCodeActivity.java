package com.dani.sed.liguriasoccorso;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CharlieCodeActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charlie_code);
        mRecyclerView = (RecyclerView) findViewById(R.id.list);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new CharlieCodeAdapter(populateCharlieCodes());
        mRecyclerView.setAdapter(mAdapter);
    }

    private ArrayList<CharlieCode> populateCharlieCodes() {
        ArrayList<CharlieCode> charlieCodes = new ArrayList<>();
        charlieCodes.add(new CharlieCode("C01", "Sospetta patologia di origine traumatica"));
        charlieCodes.add(new CharlieCode("C02", "Sospetta patologia di origine cardiocircolatoria"));
        charlieCodes.add(new CharlieCode("C03", "Sospetta patologia di origine respiratoria"));
        charlieCodes.add(new CharlieCode("C04", "Sospetta patologia di origine neurologica"));
        charlieCodes.add(new CharlieCode("C05", "Sospetta patologia di origine psichiatrica"));
        charlieCodes.add(new CharlieCode("C06", "Sospetta patologia di origine neoplastica"));
        charlieCodes.add(new CharlieCode("C07", "Sospetta patologia di origine tossicologica"));
        charlieCodes.add(new CharlieCode("C08", "Sospetta patologia di origine metabolica"));
        charlieCodes.add(new CharlieCode("C09", "Sospetta patologia di origine gastroenterologica"));
        charlieCodes.add(new CharlieCode("C10", "Sospetta patologia di origine urologica"));
        charlieCodes.add(new CharlieCode("C11", "Sospetta patologia di origine oculistica"));
        charlieCodes.add(new CharlieCode("C12", "Sospetta patologia di origine otorinolaringoiatrica"));
        charlieCodes.add(new CharlieCode("C13", "Sospetta patologia di origine dermatologica"));
        charlieCodes.add(new CharlieCode("C14", "Sospetta patologia di origine ostetrico-ginecologica"));
        charlieCodes.add(new CharlieCode("C15", "Sospetta patologia di origine infettiva"));
        charlieCodes.add(new CharlieCode("C19", "Altra patologia"));
        charlieCodes.add(new CharlieCode("C20", "Sospetta patologia di origine non identificata"));

        return charlieCodes;
    }
}
