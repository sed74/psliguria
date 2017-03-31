package com.sed.federico.prontosoccorsoligura;

/**
 * Created by federico.marchesi on 30/03/2017.
 */

public class Hospital {
    private String mName;
    private String mLastUpdated;
    private int mWhiteWaiting;
    private int mGreenWaiting;
    private int mYellowWaiting;
    private int mRedWaiting;

    private int mWhiteRunning;
    private int mGreenRunning;
    private int mYellowRunning;
    private int mRedRunning;

    private int mObi;


    public Hospital(String name) {
        mName = name;
    }

    public Hospital(String name, int whiteW, int greenW, int yellowW, int redW, int whiteR, int greenR, int yellowR, int redR, int obi) {
        mName = name;
        mWhiteWaiting = whiteW;
        mGreenWaiting = greenW;
        mYellowWaiting = yellowW;
        mRedWaiting = redW;

        mWhiteRunning = whiteR;
        mGreenRunning = greenR;
        mYellowRunning = yellowR;
        mRedRunning = redR;

        mObi = obi;
    }

    public Hospital(String name, int whiteW, int greenW, int yellowW, int redW, int whiteR, int greenR, int yellowR, int redR, int obi, String lastUpdated) {
        mName = name;
        mWhiteWaiting = whiteW;
        mGreenWaiting = greenW;
        mYellowWaiting = yellowW;
        mRedWaiting = redW;

        mWhiteRunning = whiteR;
        mGreenRunning = greenR;
        mYellowRunning = yellowR;
        mRedRunning = redR;

        mObi = obi;

        mLastUpdated = lastUpdated;
    }

    // Getters
    public String getName() {
        return mName;
    }

    public int getWhiteWaiting() {
        return mWhiteWaiting;
    }

    public int getGreenWaiting() {
        return mGreenWaiting;
    }

    public int getYellowWaiting() {
        return mYellowWaiting;
    }

    public int getRedWaiting() {
        return mRedWaiting;
    }

    public int getGreenRunning() {
        return mGreenRunning;
    }

    public int getWhiteRunning() {
        return mWhiteRunning;
    }

    public int getYellowRunning() {
        return mYellowRunning;
    }

    public int getRedRunning() {
        return mRedRunning;
    }

    public int getObi() {
        return mObi;
    }

    public String getLastUpdated() {return mLastUpdated;}

    // Setters
    public void setName(String mName) {
        this.mName = mName;
    }

    public void setWhiteWaiting(int mWhiteWaiting) {
        this.mWhiteWaiting = mWhiteWaiting;
    }

    public void setGreenWaiting(int mGreenWaiting) {
        this.mGreenWaiting = mGreenWaiting;
    }

    public void setYellowWaiting(int mYellowWaiting) {
        this.mYellowWaiting = mYellowWaiting;
    }

    public void setRedWaiting(int mRedWaiting) {
        this.mRedWaiting = mRedWaiting;
    }

    public void setWhiteRunning(int mWhiteRunning) {
        this.mWhiteRunning = mWhiteRunning;
    }

    public void setGreenRunning(int mGreenRunning) {
        this.mGreenRunning = mGreenRunning;
    }

    public void setYellowRunning(int mYellowRunning) {
        this.mYellowRunning = mYellowRunning;
    }

    public void setLastUpdated(String mLastUpdated) {
        this.mLastUpdated = mLastUpdated;
    }

    public void setRedRunning(int mRedRunning) {
        this.mRedRunning = mRedRunning;
    }

    public void setObi(int obi) {
        this.mObi = obi;
    }

    //{"name":"SAN MARTINO ","lastUpdate":" 17:15","whiteWaiting":0,"greenWaiting":3,"yellowWaiting":12,"redWaiting":0,"whiteRunning":1,"greenRunning":12,"yellowRunning":24,
    // "redRunning":8,"mObi":11}
}
