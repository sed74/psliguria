package com.sed.federico.prontosoccorsoligura.Mission;

import android.app.Activity;
import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sed.federico.prontosoccorsoligura.PubblicheAssistenze.Postazione;

import java.util.ArrayList;

/**
 * Created by federico.marchesi on 05/04/2017.
 */

public class Mission {

    private Context mContext;

    private String mMissionNo;
    private String mAmbulanceNo;
    private String mPostazione;
    private String mCode;
    private String mLocation;
    private MissionSynthesis mMissionSynthesis;
    private String mSynthesis;
    private String mDestination;
    private String mAsl;
    private String mCentrale;
    private Postazione mPostazioneObj;
    private boolean mIsMissionTerminated = false;

    public Mission(Context context) {
        this.mContext = context;
    }

    public Mission(Context context, String missionNo, String ambulanceNo,
                   String pubblicaAssitenza, String code, String location,
                   String synthesis, String destination, String asl, String centrale) {
        try {

            this.mContext = context;
            this.mMissionNo = missionNo;
            this.mAmbulanceNo = ambulanceNo;
            this.mPostazione = pubblicaAssitenza;
            this.mCode = code;
            this.mLocation = location;
            this.mSynthesis = synthesis;
            this.mMissionSynthesis = new MissionSynthesis(mContext, mSynthesis);
            if (destination.length() > 4) {
                if (destination.substring(1, 4).equalsIgnoreCase(" h "))
                    destination = destination.substring(4);
            }
            this.mDestination = destination;
            this.mAsl = asl;
            this.mCentrale = centrale;
            this.mPostazioneObj = new Postazione(pubblicaAssitenza);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getCentrale() {
        return mCentrale;
    }

    public void setCentrale(String centrale) {
        this.mCentrale = centrale;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }


    public String getFullMissionNo() {
        return mMissionNo;
    }

    public String getMissionNo() {
        return mMissionNo.substring(2);
    }

    public void setMissionNo(String missionNo) {
        this.mMissionNo = missionNo;
    }

    public String getAmbulanceNo() {
        return mAmbulanceNo;
    }

    public void setAmbulanceNo(String ambulanceNo) {
        this.mAmbulanceNo = ambulanceNo;
    }

    public String getmPostazione() {
        return mPostazione;
    }

    public void setPostazione(String postazione) {
        this.mPostazione = postazione;
    }

    public String getPubblicaAssistenza() {
        return mPostazioneObj.getName();
//        String pubblicaAssistenza = mPubblicaAssistenza.get(mPostazione);
//        if (pubblicaAssistenza == null) pubblicaAssistenza = mPostazione;
//        return pubblicaAssistenza;
    }

    public String getCode() {
        return mCode;
    }

    public void setCode(String code) {
        this.mCode = code;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        this.mLocation = location;
    }

    public MissionSynthesis getMissionSynthesis() {
        return mMissionSynthesis;
    }

    public void setSynthesis(String synthesis) {
        this.mMissionSynthesis = new MissionSynthesis(mContext, synthesis);
    }

    public String getDestination() {
        return mDestination;
    }

    public void setDestination(String destination) {
        this.mDestination = destination;
    }

    public String getAsl() {
        return mAsl;
    }

    public void setAsl(String asl) {
        this.mAsl = asl;
    }

    public String getPickUpLocation() {
        return mMissionSynthesis.getPickUpLocation();
    }

    public String getIndiaCode() {
        return mMissionSynthesis.getEmergencyCode();
    }

    public String getCharlie() {
        return mMissionSynthesis.getCharlieCode() + " - " + mMissionSynthesis.getPatologia();
    }

    public String getEmergencyCode() {
        return mMissionSynthesis.getEmergencyCode();
    }

    public boolean isMissionTerminated() {
        return mIsMissionTerminated;
    }

    public void setIsMissionTerminated(boolean isMissionTerminated) {
        this.mIsMissionTerminated = isMissionTerminated;
    }
}
