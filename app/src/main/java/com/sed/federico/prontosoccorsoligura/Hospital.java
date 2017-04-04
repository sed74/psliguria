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
    private int mMaxNo;

    private int mTotal;


    public Hospital(String name) {
        mName = name;
    }

    public Hospital(String name, int whiteW, int greenW, int yellowW, int redW, int whiteR,
                    int greenR, int yellowR, int redR, int obi) {
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
        int[] values = new int[]{whiteW, greenW, yellowW, redW, whiteR, greenR, yellowR, redR, obi};
        mMaxNo = calculateMax(values);
    }

    public Hospital(String name, int whiteW, int greenW, int yellowW, int redW, int whiteR,
                    int greenR, int yellowR, int redR, int obi, String lastUpdated) {
        mName = name.trim();
        mWhiteWaiting = whiteW;
        mGreenWaiting = greenW;
        mYellowWaiting = yellowW;
        mRedWaiting = redW;

        mWhiteRunning = whiteR;
        mGreenRunning = greenR;
        mYellowRunning = yellowR;
        mRedRunning = redR;

        mObi = obi;

        mLastUpdated = lastUpdated.trim();
        int[] values = new int[]{whiteW, greenW, yellowW, redW, whiteR, greenR, yellowR, redR, obi};
        mMaxNo = calculateMax(values);

    }

    private int calculateMax(int[] values) {
        int max = Integer.MIN_VALUE;
        mTotal = 0;
        for (int i = 0; i < values.length; i++) {
            mTotal += values[i];
            if (values[i] > max) {
                max = values[i];
            }
        }
        return max;
    }

    // Getters
    public String getName() {
        return mName;
    }

    // Setters
    public void setName(String mName) {
        this.mName = mName;
    }

    public int getWhiteWaiting() {
        return mWhiteWaiting;
    }

    public void setWhiteWaiting(int mWhiteWaiting) {
        this.mWhiteWaiting = mWhiteWaiting;
    }

    public int getGreenWaiting() {
        return mGreenWaiting;
    }

    public void setGreenWaiting(int mGreenWaiting) {
        this.mGreenWaiting = mGreenWaiting;
    }

    public int getYellowWaiting() {
        return mYellowWaiting;
    }

    public void setYellowWaiting(int mYellowWaiting) {
        this.mYellowWaiting = mYellowWaiting;
    }

    public int getRedWaiting() {
        return mRedWaiting;
    }

    public void setRedWaiting(int mRedWaiting) {
        this.mRedWaiting = mRedWaiting;
    }

    public int getGreenRunning() {
        return mGreenRunning;
    }

    public void setGreenRunning(int mGreenRunning) {
        this.mGreenRunning = mGreenRunning;
    }

    public int getWhiteRunning() {
        return mWhiteRunning;
    }

    public void setWhiteRunning(int mWhiteRunning) {
        this.mWhiteRunning = mWhiteRunning;
    }

    public int getYellowRunning() {
        return mYellowRunning;
    }

    public void setYellowRunning(int mYellowRunning) {
        this.mYellowRunning = mYellowRunning;
    }

    public int getRedRunning() {
        return mRedRunning;
    }

    public void setRedRunning(int mRedRunning) {
        this.mRedRunning = mRedRunning;
    }

    public int getObi() {
        return mObi;
    }

    public void setObi(int obi) {
        this.mObi = obi;
    }

    public String getLastUpdated() {
        return mLastUpdated;
    }

    public void setLastUpdated(String mLastUpdated) {
        this.mLastUpdated = mLastUpdated;
    }

    public int getMaxNo() {
        return mMaxNo;
    }

    public int getTotal() {
        return mTotal;
    }

    //{"name":"SAN MARTINO ","lastUpdate":" 17:15","whiteWaiting":0,"greenWaiting":3,"yellowWaiting":12,"redWaiting":0,"whiteRunning":1,"greenRunning":12,"yellowRunning":24,
    // "redRunning":8,"mObi":11}
}
