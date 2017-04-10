package com.sed.federico.prontosoccorsoligura.Mission;

import android.content.Context;

import com.sed.federico.prontosoccorsoligura.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by federico.marchesi on 05/04/2017.
 */

public class MissionSynthesis {
    private HashMap<String, String> mPickupLocation;
    private HashMap<String, String> mCharlieCode;
    private HashMap<String, String> mEmergencyCode;
    private Context mContext;
    private String mLocation;
    private String mCharlie;
    private String mCode;

    public MissionSynthesis(Context context, String missionCode) {
        mContext = context;
        mLocation = missionCode.substring(0, 1);
        mCharlie = missionCode.substring(1, 4);
        mCode = missionCode.substring(4, 5);
        init();
    }

    private void init() {
        mPickupLocation = new HashMap<>();
        mPickupLocation.put("S", mContext.getResources().getString(R.string.location_s));
        mPickupLocation.put("P", mContext.getResources().getString(R.string.location_p));
        mPickupLocation.put("Y", mContext.getResources().getString(R.string.location_y));
        mPickupLocation.put("K", mContext.getResources().getString(R.string.location_k));
        mPickupLocation.put("L", mContext.getResources().getString(R.string.location_l));
        mPickupLocation.put("Q", mContext.getResources().getString(R.string.location_q));
        mPickupLocation.put("Z", mContext.getResources().getString(R.string.location_z));

        mCharlieCode = new HashMap<>();
        mCharlieCode.put("C01", mContext.getResources().getString(R.string.charlie01));
        mCharlieCode.put("C02", mContext.getResources().getString(R.string.charlie02));
        mCharlieCode.put("C03", mContext.getResources().getString(R.string.charlie03));
        mCharlieCode.put("C04", mContext.getResources().getString(R.string.charlie04));
        mCharlieCode.put("C05", mContext.getResources().getString(R.string.charlie05));
        mCharlieCode.put("C06", mContext.getResources().getString(R.string.charlie06));
        mCharlieCode.put("C07", mContext.getResources().getString(R.string.charlie07));
        mCharlieCode.put("C08", mContext.getResources().getString(R.string.charlie08));
        mCharlieCode.put("C09", mContext.getResources().getString(R.string.charlie09));
        mCharlieCode.put("C10", mContext.getResources().getString(R.string.charlie10));
        mCharlieCode.put("C11", mContext.getResources().getString(R.string.charlie11));
        mCharlieCode.put("C12", mContext.getResources().getString(R.string.charlie12));
        mCharlieCode.put("C13", mContext.getResources().getString(R.string.charlie13));
        mCharlieCode.put("C14", mContext.getResources().getString(R.string.charlie14));
        mCharlieCode.put("C15", mContext.getResources().getString(R.string.charlie15));
        mCharlieCode.put("C19", mContext.getResources().getString(R.string.charlie19));
        mCharlieCode.put("C20", mContext.getResources().getString(R.string.charlie20));

        mEmergencyCode = new HashMap<>();
        mEmergencyCode.put("B", mContext.getResources().getString(R.string.code_white));
        mEmergencyCode.put("V", mContext.getResources().getString(R.string.code_green));
        mEmergencyCode.put("G", mContext.getResources().getString(R.string.code_yellow));
        mEmergencyCode.put("R", mContext.getResources().getString(R.string.code_red));
    }

    public String getPickUpLocation() {
        return mPickupLocation.get(mLocation);
    }

    public String getEmergencyCode() {
        return mEmergencyCode.get(mCode);
    }

    public String getPatologia() {
        return mCharlieCode.get(mCharlie);
    }

    public String getCharlieCode() {
        return mCharlie;
    }
}
