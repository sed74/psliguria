package com.sed.federico.prontosoccorsoligura.Mission;

import java.util.ArrayList;

/**
 * Created by federico.marchesi on 05/04/2017.
 */

public class MissionListCustom extends ArrayList<Mission> {
    private static final String DRAGO_POSTAZIONE = "elisoccorso";

    private boolean mIsDragoWorking = false;
    private String mWhereIsDrago;

    public MissionListCustom() {
        // Do some initialization
    }

    @Override
    public void add(int index, Mission element) {
        super.add(index, element);
        if (element.getmPostazione().toLowerCase() == DRAGO_POSTAZIONE.toLowerCase()) {
            mIsDragoWorking = true;
            mWhereIsDrago = element.getLocation();
        }
    }

    public boolean isIsDragoWorking() {
        return mIsDragoWorking;
    }

    public String getDragoPosition() {
        return mWhereIsDrago;
    }
}
