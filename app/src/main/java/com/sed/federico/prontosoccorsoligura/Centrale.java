package com.sed.federico.prontosoccorsoligura;

/**
 * Created by federico.marchesi on 13/04/2017.
 */

public class Centrale {
    private int mId;
    private String mCodice;
    private String mDescrizione;
    private String mCitta;
    private String mCentrale;

    public Centrale() {
    }

    public Centrale(int id, String codice, String descrizione, String citta, String centrale) {
        this.mId = id;
        this.mCodice = codice;
        this.mDescrizione = descrizione;
        this.mCitta = citta;
        this.mCentrale = centrale;
    }

    public Centrale(String codice, String descrizione) {
        this.mCodice = codice;
        this.mDescrizione = descrizione;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getCodice() {
        return mCodice;
    }

    public void setCodice(String codice) {
        this.mCodice = codice;
    }

    public String getDescrizione() {
        return mDescrizione;
    }

    public void setDescrizione(String descrizione) {
        this.mDescrizione = descrizione;
    }

    public String getCitta() {
        return mCitta;
    }

    public void setCitta(String citta) {
        this.mCitta = citta;
    }

    public String getCentrale() {
        return mCentrale;
    }

    public void setCentrale(String centrale) {
        this.mCentrale = centrale;
    }
}
