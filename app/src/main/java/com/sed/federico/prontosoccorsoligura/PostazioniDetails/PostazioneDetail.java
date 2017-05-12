package com.sed.federico.prontosoccorsoligura.PostazioniDetails;

/**
 * Created by federico.marchesi on 08/05/2017.
 */

public class PostazioneDetail {
    private String mCodeAmbulance;
    private int mTotMissions;
    private int mTotWhite;
    private int mTotGreen;
    private int mTotYellow;
    private int mTotRed;
    private int mTotDays;
    private int mTotRealDays;
    private String mFromDate;
    private String mToDate;
    private String mCodePostazione;


    public PostazioneDetail(String codePostaione, String codeAmbulance, int totMissions, int totWhite, int totGreen,
                            int totYellow, int totRed, String fromDate, String toDate, int totDays, int totRealDays) {

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
        this.mTotRealDays = totRealDays;

    }

    public PostazioneDetail(String codePostaione, String codeAmbulance, int totMissions, int totWhite, int totGreen,
                            int totYellow, int totRed, int totDays, int totRealDays) {

        this(codePostaione, codeAmbulance, totMissions, totWhite, totGreen, totYellow, totRed, "", "", totDays, totRealDays);
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

    public int getTotRealDays() {
        return mTotRealDays;
    }

    public void setTotRealDays(int totRealDays) {
        this.mTotRealDays = totRealDays;
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
}
