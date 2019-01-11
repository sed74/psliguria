package com.dani.sed.liguriasoccorso;

import java.util.ArrayList;

/**
 * Created by federico.marchesi on 31/03/2017.
 */

public class HospitalListCustom extends ArrayList<Hospital> {
    private int mMaxGlobalValue;

    public HospitalListCustom() {
        super();
        mMaxGlobalValue = Integer.MIN_VALUE;
    }

    @Override
    public boolean add(Hospital hospital) {

        mMaxGlobalValue = Math.max(hospital.getMaxNo(), mMaxGlobalValue);

        return super.add(hospital);
    }

    @Override
    public void add(int index, Hospital element) {
        super.add(index, element);
    }

    public int getMaxGlobalValue() {
        return mMaxGlobalValue;
    }
}
