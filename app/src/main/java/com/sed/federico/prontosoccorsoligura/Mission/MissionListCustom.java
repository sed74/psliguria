package com.sed.federico.prontosoccorsoligura.Mission;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by federico.marchesi on 05/04/2017.
 */

public class MissionListCustom extends ArrayList<Mission> {
    private static final String DRAGO_POSTAZIONE = "elisoccorso";
    private static final String DRAGO_POSTAZIONE_1 = "bia";


    private boolean mIsDragoWorking = false;
    private String mWhereIsDrago;

    public MissionListCustom() {
        // Do some initialization
    }

    @Override
    public void add(int index, Mission element) {
        super.add(index, element);
        checkForDrago(element);
    }

    @Override
    public boolean addAll(Collection<? extends Mission> c) {
        for (Mission t :
                c) {
            checkForDrago(t);
        }
        return super.addAll(c);
    }
    private void checkForDrago(Mission mission){
        if (mission.getmPostazione().equalsIgnoreCase(DRAGO_POSTAZIONE)){
            mIsDragoWorking = true;
            mWhereIsDrago = mission.getLocation();
        }
    }

    public boolean isIsDragoWorking() {
        return mIsDragoWorking;
    }

    public String getDragoPosition() {
        return mWhereIsDrago;
    }
}
