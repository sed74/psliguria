package com.sed.federico.prontosoccorsoligura.Mission;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by federico.marchesi on 05/04/2017.
 */

public class MissionListCustom extends ArrayList<Mission> {
    private static final String DRAGO_POSTAZIONE = "elisoccorso";

    private boolean mIsDragoWorking = false;
    private String mWhereIsDrago;
    private String mWhereIsDragoCentrale;

    public MissionListCustom() {
        // Do some initialization
    }

    @Override
    public boolean add(Mission mission) {
        doChecks(mission);
        return super.add(mission);
    }

    @Override
    public void add(int index, Mission mission) {
        super.add(index, mission);
//        doChecks(mission);
        checkForDrago(mission);
    }

    private void doChecks(Mission mission) {
        checkForDrago(mission);
        isMissionTerminated(mission);
    }

    @Override
    public boolean addAll(Collection<? extends Mission> c) {
        for (Mission t :
                c) {
            doChecks(t);
        }
        return super.addAll(c);
    }

    private void checkForDrago(Mission mission) {
        if (mission.getmPostazione().equalsIgnoreCase(DRAGO_POSTAZIONE)) {
            mIsDragoWorking = true;
            mWhereIsDrago = mission.getLocation();
            mWhereIsDragoCentrale = mission.getCentrale();
        }
    }

    private void isMissionTerminated(Mission element) {
        for (Mission mission :
                this) {
            if (mission.getAmbulanceNo().equalsIgnoreCase(element.getAmbulanceNo())) {
                if (Double.parseDouble(mission.getMissionNo()) >
                        Double.parseDouble(element.getMissionNo())) {
                    element.setIsMissionTerminated(true);
                } else {
                    mission.setIsMissionTerminated(true);
                }
            }
        }

    }

    public boolean isIsDragoWorking() {
        return mIsDragoWorking;
    }

    public String getDragoPosition() {
        return mWhereIsDrago;
    }

    public String getDragoCentrale() {
        return mWhereIsDragoCentrale;
    }
}
