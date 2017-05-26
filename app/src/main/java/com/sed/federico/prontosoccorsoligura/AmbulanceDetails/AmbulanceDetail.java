package com.sed.federico.prontosoccorsoligura.AmbulanceDetails;

/**
 * Created by federico.marchesi on 08/05/2017.
 */

public class AmbulanceDetail {
    private String mCodeAmbulance;

    private int mTotMissions;

    private int mTotWhite;
    private int mTotGreen;
    private int mTotYellow;
    private int mTotRed;

    private float mAvgWhite;
    private float mAvgGreen;
    private float mAvgYellow;
    private float mAvgRed;

    private int mTotDays;
    private int mTotWorkedDays;

    private String mFromDate;
    private String mToDate;
    private String mCodePostazione;


    public AmbulanceDetail(String codePostaione, String codeAmbulance, int totMissions, int totWhite, int totGreen,
                           int totYellow, int totRed, String fromDate, String toDate, int totDays, int totWorkedDays) {

        this.mCodePostazione = codePostaione;
        this.mCodeAmbulance = codeAmbulance;
        this.mTotMissions = totMissions;
        this.mTotWhite = totWhite;
        this.mTotGreen = totGreen;
        this.mTotYellow = totYellow;
        this.mFromDate = fromDate;
        this.mToDate = toDate;
        this.mTotRed = totRed;
        this.mTotDays = totDays;
        this.mTotWorkedDays = totWorkedDays;
        this.mAvgWhite = (float) mTotWhite / mTotDays;
        this.mAvgGreen = (float) mTotGreen / mTotDays;
        this.mAvgYellow = (float) mTotYellow / mTotDays;
        this.mAvgRed = (float) mTotRed / mTotDays;

    }

    public AmbulanceDetail(String codePostaione, String codeAmbulance, int totMissions, int totWhite, int totGreen,
                           int totYellow, int totRed, int totDays, int totWorkedDays) {

        this(codePostaione, codeAmbulance, totMissions, totWhite, totGreen, totYellow, totRed, "", "", totDays, totWorkedDays);
    }

    public String getCodeAmbulance() {
        return mCodeAmbulance;
    }

    public void setCodeAmbulance(String codeAmbulance) {
        this.mCodeAmbulance = codeAmbulance;
    }

    public int getTotMissions() {
        return mTotMissions;
    }

    public void setTotMissions(int totMissions) {
        this.mTotMissions = totMissions;
    }

    public int getTotWhite() {
        return mTotWhite;
    }

    public void setTotWhite(int totWhite) {
        this.mTotWhite = totWhite;
    }

    public int getTotGreen() {
        return mTotGreen;
    }

    public void setTotGreen(int totGreen) {
        this.mTotGreen = totGreen;
    }

    public int getTotYellow() {
        return mTotYellow;
    }

    public void setTotYellow(int totYellow) {
        this.mTotYellow = totYellow;
    }

    public int getTotRed() {
        return mTotRed;
    }

    public void setTotRed(int totRed) {
        this.mTotRed = totRed;
    }

    public int getTotDays() {
        return mTotDays;
    }

    public void setTotDays(int totDays) {
        this.mTotDays = totDays;
    }

    public int getTotWorkedDays() {
        return mTotWorkedDays;
    }

    public void setTotWorkedDays(int totWorkedDays) {
        this.mTotWorkedDays = totWorkedDays;
    }

    public String getFromDate() {
        return mFromDate;
    }

    public void setFromDate(String fromDate) {
        this.mFromDate = fromDate;
    }

    public String getToDate() {
        return mToDate;
    }

    public void setToDate(String toDate) {
        this.mToDate = toDate;
    }

    public String getCodePostazione() {
        return mCodePostazione;
    }

    public void setCodePostazione(String codePostazione) {
        this.mCodePostazione = codePostazione;
    }

    public float getAvgWhite() {
        return mAvgWhite;
    }

    public float getAvgGreen() {
        return mAvgGreen;
    }

    public float getAvgYellow() {
        return mAvgYellow;
    }

    public float getAvgRed() {
        return mAvgRed;
    }
}
