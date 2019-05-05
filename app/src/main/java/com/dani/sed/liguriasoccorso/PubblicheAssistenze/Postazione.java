package com.dani.sed.liguriasoccorso.PubblicheAssistenze;

import com.dani.sed.liguriasoccorso.Color;
import com.dani.sed.liguriasoccorso.CrossImage;

/**
 * Created by federico.marchesi on 13/04/2017.
 */


public class Postazione {

    private String mUsableCode;
    private int mId;
    private String mCode;
    private String mName;
    private String mCity;
    private String mCentrale;
    private String mAddress;
    private int mTotAmbulance;

    private int mTotMissions;
    private int mTotWhite;
    private int mTotGreen;
    private int mTotYellow;
    private int mTotRed;

    private int mTotDays;

    private double mAvgMission;
    private double mAvgWhite;
    private double mAvgGreen;
    private double mAvgYellow;
    private double mAvgRed;
    private CrossImage mCrossImage;


    public Postazione() {
    }

    public Postazione(int id, String code, String name, String address, int totAmbulance,
                      double totMission, double avgWhite, double avgGreen, double avgYellow,
                      double avgRed, int totWhite, int totGreen, int totYellow, int totRed,
                      int totDays) {

        this.mId = id;
        this.mUsableCode = code.replaceAll("\\.", "");
        this.mCode = code;
        this.mName = name;
        this.mAddress = address;
        this.mTotAmbulance = totAmbulance;
        this.mAvgMission = totMission;
        this.mAvgWhite = avgWhite;
        this.mAvgGreen = avgGreen;
        this.mAvgYellow = avgYellow;
        this.mAvgRed = avgRed;
        this.mTotDays = totDays;
        this.mTotWhite = totWhite;
        this.mTotGreen = totGreen;
        this.mTotYellow = totYellow;
        this.mTotRed = totRed;

        this.updateTotal();

        this.populateExtraInfo();
    }

    public Postazione(String code) {
        this(0, code, "", "", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    }

    private void updateTotal() {
        this.mTotMissions = mTotWhite + mTotGreen + mTotYellow + mTotRed;
    }

    public int getTotDays() {
        return mTotDays;
    }

    public void setTotDays(int totDays) {
        this.mTotDays = totDays;
    }

    public int getTotMissions() {
        return mTotMissions;
    }

    public void setmTotMissions(int totMissions) {
        this.mTotMissions = totMissions;
    }

    public int getTotWhite() {
        return mTotWhite;
    }

    public void setTotWhite(int totWhite) {
        this.mTotWhite = totWhite;
        this.updateTotal();
    }

    public int getTotGreen() {
        return mTotGreen;
    }

    public void setTotGreen(int totGreen) {
        this.mTotGreen = totGreen;
        this.updateTotal();
    }

    public int getTotYellow() {
        return mTotYellow;
    }

    public void setTotYellow(int totYellow) {
        this.mTotYellow = totYellow;
        this.updateTotal();
    }

    public int getTotRed() {
        return mTotRed;
    }

    public void setTotRed(int totRed) {
        this.mTotRed = totRed;
        this.updateTotal();
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public int getTotAmbulance() {
        return mTotAmbulance;
    }

    public void setTotAmbulance(int mTotAmbulance) {
        this.mTotAmbulance = mTotAmbulance;
    }

    public double getAvgMission() {
        return mAvgMission;
    }

    public void setAvgMission(double avgMission) {
        this.mAvgMission = avgMission;
    }

    public double getAvgWhite() {
        return mAvgWhite;
    }

    public void setAvgWhite(double mAvgWhite) {
        this.mAvgWhite = mAvgWhite;
    }

    public double getAvgGreen() {
        return mAvgGreen;
    }

    public void setAvgGreen(double mAvgGreen) {
        this.mAvgGreen = mAvgGreen;
    }

    public double getAvgYellow() {
        return mAvgYellow;
    }

    public void setAvgYellow(double mAvgYellow) {
        this.mAvgYellow = mAvgYellow;
    }

    public double getAvgRed() {
        return mAvgRed;
    }

    public void setAvgRed(double mAvgRed) {
        this.mAvgRed = mAvgRed;
    }

    public int getCrossImage() {
        return mCrossImage.getCrossImage();
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getCode() {
        return mCode;
    }

    public void setCode(String codice) {
        this.mCode = codice;
        this.mUsableCode = codice.replaceAll("/.", "");
        this.populateExtraInfo();
    }

    public String getUsableCode() {
        return mUsableCode;
    }

    public String getName() {
        return mName;
    }

    public void setName(String descrizione) {
        this.mName = descrizione;
    }

    public String getCitta() {
        return mCity;
    }

    public void setCitta(String citta) {
        this.mCity = citta;
    }

    public String getCentrale() {
        return mCentrale;
    }

    public void setCentrale(String centrale) {
        this.mCentrale = centrale;
    }

    private void populateExtraInfo() {

        int crossColor;
        switch (mCode) {
            case "CRTORR":
                if (mName.isEmpty()) mName = "Croce Rossa Torriglia";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "GIALLA":
                if (mName.isEmpty()) mName = "P.A. Croce Gialla";
                mCrossImage = new CrossImage(Color.YELLOW);
                break;
            case "BGL":
                if (mName.isEmpty()) mName = "P.A. Croce Verde Bogliasco";
                mCrossImage = new CrossImage(Color.GREEN);
            case "VER":
                if (mName.isEmpty()) mName = "P.A. Croce Verde Genovese";
                mCrossImage = new CrossImage(Color.GREEN);
                break;
            case "BIA":
                if (mName.isEmpty()) mName = "P.A. Croce Bianca Genovese";
                mCrossImage = new CrossImage(Color.WHITE);
                break;
            case "CAR":
                if (mName.isEmpty()) mName = "P.A. CBG - Distaccamento Carignano";
                mCrossImage = new CrossImage(Color.WHITE);
                break;
            case "ISO":
                if (mName.isEmpty()) mName = "P.A. Croce Verde Isoverde";
                mCrossImage = new CrossImage(Color.GREEN);
                break;
            case "SES":
                if (mName.isEmpty()) mName = "P.A. Croce Verde Sestri Ponente";
                mCrossImage = new CrossImage(Color.GREEN);
                break;
            case "PRA":
                if (mName.isEmpty()) mName = "P.A. Croce Verde Praese";
                mCrossImage = new CrossImage(Color.GREEN);
                break;
            case "PGL":
                if (mName.isEmpty()) mName = "P.A. Croce Verde Pegliese";
                mCrossImage = new CrossImage(Color.GREEN);
                break;
            case "QUI":
                if (mName.isEmpty()) mName = "P.A. Croce Verde Quinto";
                mCrossImage = new CrossImage(Color.GREEN);
                break;
            case "ORO":
                if (mName.isEmpty()) mName = "P.A. Croce D'Oro Sampierdarena";
                mCrossImage = new CrossImage(Color.GOLD);
                break;
            case "PDE":
                if (mName.isEmpty()) mName = "P.A. Croce Verde Pontedecimo";
                mCrossImage = new CrossImage(Color.GREEN);
                break;
            case "BOL":
                if (mName.isEmpty()) mName = "P.A. Croce Bianca Bolzaneto";
                mCrossImage = new CrossImage(Color.WHITE);
                break;
            case "MANESS":
                if (mName.isEmpty()) mName = "P.A. Croce D'Oro Manesseno";
                mCrossImage = new CrossImage(Color.GOLD);
                break;
            case "RIV":
                if (mName.isEmpty()) mName = "P.A. Croce Rosa Rivarolo";
                mCrossImage = new CrossImage(Color.ROSE);
                break;
            case "POS":
                if (mName.isEmpty()) mName = "Ponente Soccorso";
                mCrossImage = new CrossImage(Color.CROSS);
                break;
            case "BLU":
                if (mName.isEmpty()) mName = "P.A. Croce Blu Castelletto";
                mCrossImage = new CrossImage(Color.BLUE);
                break;
            case "ORG":
                if (mName.isEmpty()) mName = "P.A. Croce Blu dist. Oregina";
                mCrossImage = new CrossImage(Color.BLUE);
                break;
            case "CRMASO":
                if (mName.isEmpty()) mName = "Croce Rossa Masone";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CRCOGO":
                if (mName.isEmpty()) mName = "Croce Rossa Cogoleto";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CRROSS":
                if (mName.isEmpty()) mName = "Croce Rossa Rossiglione";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CRAREN":
                if (mName.isEmpty()) mName = "Croce Rossa Arenzano";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CRGENO":
                if (mName.isEmpty()) mName = "Croce Rossa Genova";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CRRONC":
                if (mName.isEmpty()) mName = "Croce Rossa Ronco Scrivia";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CRVTR":
                if (mName.isEmpty()) mName = "Croce Rossa Voltri";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CRSORI":
                if (mName.isEmpty()) mName = "Croce Rossa Sori";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CRUSCI":
                if (mName.isEmpty()) mName = "Croce Rossa Uscio";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CRCMOR":
                if (mName.isEmpty()) mName = "Croce Rossa Campomorone";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CRBARG":
                if (mName.isEmpty()) mName = "Croce Rossa Bargagli";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CRDAVA":
                if (mName.isEmpty()) mName = "Croce Rossa Davagna";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CRMONT":
                if (mName.isEmpty()) mName = "Croce Rossa Montoggio";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CRSTUR":
                if (mName.isEmpty()) mName = "Croce Rossa Sturla";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CRCERA":
                if (mName.isEmpty()) mName = "Croce Rossa Ceranesi";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CRTERR":
                if (mName.isEmpty()) mName = mCode;
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CRMTBRUNO":
                if (mName.isEmpty()) mName = "Croce Rossa Montebruno";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CRCLIG":
                if (mName.isEmpty()) mName = "Croce Rossa Campo Ligure";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CRRICC":
                if (mName.isEmpty()) mName = "Croce Rossa Serra Riccò";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CROLCE":
                if (mName.isEmpty()) mName = "Croce Rossa Sant'Olcese";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "FIU":
                if (mName.isEmpty()) mName = "Volontari Del Soccorso Fiumara";
                mCrossImage = new CrossImage(Color.CROSS);
                break;
            case "BUR":
                if (mName.isEmpty()) mName = "P.A. Croce Verde San Gottardo - Sezione Burlando";
                mCrossImage = new CrossImage(Color.GREEN);
                break;
            case "BUS":
                if (mName.isEmpty()) mName = "P.A. Croce Verde Busallese";
                mCrossImage = new CrossImage(Color.GREEN);
                break;
            case "MOL":
                if (mName.isEmpty()) mName = "P.A. Molassana";
                mCrossImage = new CrossImage(Color.CROSS);
                break;
            case "SGT":
                if (mName.isEmpty()) mName = "P.A. Croce Verde San Gottardo";
                mCrossImage = new CrossImage(Color.GREEN);
                break;
            case "SQR":
                if (mName.isEmpty()) mName = "P.A. Croce Rosa San Quirico";
                mCrossImage = new CrossImage(Color.ROSE);
                break;
            case "QUA":
                if (mName.isEmpty()) mName = "P.A. Croce Verde Quarto dei Mille";
                mCrossImage = new CrossImage(Color.GREEN);
                break;
            case "FEG":
                if (mName.isEmpty()) mName = "P.A. Croce Azzurra Fegino";
                mCrossImage = new CrossImage(Color.LIGHT_BLUE);
                break;
            case "MEL":
                if (mName.isEmpty()) mName = "P.A. Croce Verde Mele";
                mCrossImage = new CrossImage(Color.GREEN);
                break;
            case "COR":
                if (mName.isEmpty()) mName = "P.A. Croce Bianca Cornigliano";
                mCrossImage = new CrossImage(Color.WHITE);
                break;
            case "RUT":
                if (mName.isEmpty()) mName = "P.A. Volontari Del Soccorso Ruta Di Camogli";
                mCrossImage = new CrossImage(Color.CROSS);
                break;
            case "NER":
                if (mName.isEmpty()) mName = "P.A. Nerviese";
                mCrossImage = new CrossImage(Color.CROSS);
                break;
            case "RCC":
                if (mName.isEmpty()) mName = "P.A. Croce Verde Recco";
                mCrossImage = new CrossImage(Color.GREEN);
                break;
            case "MISGE":
                if (mName.isEmpty()) mName = "P.A. Misericordia Genova Centro";
                mCrossImage = new CrossImage(Color.CROSS);
                break;
            case "CAS":
                if (mName.isEmpty()) mName = "P.A. Croce Verde Casellese";
                mCrossImage = new CrossImage(Color.GREEN);
                break;
            case "STR":
                if (mName.isEmpty()) mName = "P.A. G.A.U. Struppa";
                mCrossImage = new CrossImage(Color.CROSS);
                break;
            case "CAM":
                if (mName.isEmpty()) mName = "P.A. Croce Verde Camogliese";
                mCrossImage = new CrossImage(Color.GREEN);
                break;
            case "VAL":
                if (mName.isEmpty()) mName = "P.A. Croce Bianca Valsecca";
                mCrossImage = new CrossImage(Color.WHITE);
                break;
            case "MIG":
                if (mName.isEmpty()) mName = "P.A. Croce Bianca Mignanego";
                mCrossImage = new CrossImage(Color.WHITE);
                break;
            case "LUM":
                if (mName.isEmpty()) mName = "P.A. Croce Verde Lumarzo";
                mCrossImage = new CrossImage(Color.GREEN);
                break;
            case "CEL":
                if (mName.isEmpty()) mName = "P.A. Croce Celeste San Benigno";
                mCrossImage = new CrossImage(Color.CELESTE);
                break;
            case "BOR":
                if (mName.isEmpty()) mName = "P.A. Croce Azzurra Borzoli";
                mCrossImage = new CrossImage(Color.LIGHT_BLUE);
                break;
            case "CCF":
                if (mName.isEmpty()) mName = "P.A. Croce Verde Crocefieschi";
                mCrossImage = new CrossImage(Color.GREEN);
                break;
            case "LERCA":
                if (mName.isEmpty()) mName = "V.P.S. Croce d'Oro Sciarborasca - Sezione Lerca";
                mCrossImage = new CrossImage(Color.GOLD);
                break;
            case "SCI":
                if (mName.isEmpty()) mName = "V.P.S. Croce d'Oro Sciarborasca";
                mCrossImage = new CrossImage(Color.GOLD);
                break;
            case "BAV":
                if (mName.isEmpty()) mName = "P.A. Croce Azzurra Bavari";
                mCrossImage = new CrossImage(Color.LIGHT_BLUE);
                break;
            case "A.V.T.":
                if (mName.isEmpty()) mName = "P.A. Volontari Del Soccorso Alta Val Trebbia";
                mCrossImage = new CrossImage(Color.CROSS);
                break;
            case "TOR":
                if (mName.isEmpty()) mName = "P.A. Croce Bianca Torrazza";
                mCrossImage = new CrossImage(Color.WHITE);
                break;
            case "CROCE TIGLIETO":
                if (mName.isEmpty()) mName = "P.A. Croce Bianca Tiglieto";
                mCrossImage = new CrossImage(Color.WHITE);
                break;
            case "FONGOR":
                if (mName.isEmpty()) mName = "P.A. Fontanigorda";
                mCrossImage = new CrossImage(Color.CROSS);
                break;
            case "CNSAS SOCCORSO ALPINO":
                if (mName.isEmpty()) mName = "Soccorso Alpino Genova";
                mCrossImage = new CrossImage(Color.CROSS);
                break;
            case "DES":
                if (mName.isEmpty()) mName = "P.A. Croce Bianca San Desiderio";
                mCrossImage = new CrossImage(Color.WHITE);
                break;
            case "CRISOL":
                if (mName.isEmpty()) mName = "Croce Rossa Isola del Cantone";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "TRAPIA":
                if (mName.isEmpty()) mName = "Trapianti";
                mCrossImage = new CrossImage(getDefaultCrossColor(mCode));
                break;
            case "CRSAVI":
                if (mName.isEmpty()) mName = "Croce Rossa Savignone";
                mCrossImage = new CrossImage(Color.RED);
                break;
            default:
                if (mName.isEmpty()) mName = mCode;
                mCrossImage = new CrossImage(getDefaultCrossColor(mCode));
        }
/*
"Associazione Cinofili Da Soccorso \"Il Branco\" Onlus"
"Medicina E Progresso"
"P.A. Croce Bianca Rapallese"
"P.A. Croce D'Oro Fascia"
"P.A. Croce D'Oro Sciarborasca"
"P.A. Croce Rosa Di Trensasco"
"P.A. Croce Verde Isoverde"
"P.A. Croce Verde Quarto"
"P.A. Croce Verde Quinto"
"P.A. Croce Verde Sestri Ponente"
"P.A. Croce Verde Vobbia - C/O Grosso Zaverio"
"P.A. Volontari Del Soccorso Sestri Levante"


        Savona
"P.A. Croce Azzurra Calizzano"
"P.A. Croce Bianca Alassio "
"P.A. Croce Bianca Albenga"
"P.A. Croce Bianca Altare"
"P.A. Croce Bianca Andora"
"P.A. Croce Bianca Borghetto Santo Spirito"
"P.A. Croce Bianca Borgio Verezzi"
"P.A. Croce Bianca Cairo Montenotte"
"P.A. Croce Bianca Calice Ligure \"Dott. G. Cesio\""
"P.A. Croce Bianca Carcare"
"P.A. Croce Bianca Dego"
"P.A. Croce Bianca Finale Ligure"
"P.A. Croce Bianca Giusvalla"
"P.A. Croce Bianca Laigueglia"
"P.A. Croce Bianca Mioglia"
"P.A. Croce Bianca Noli"
"P.A. Croce Bianca Pontinvrea"
"P.A. Croce Bianca Savona"
"P.A. Croce Bianca Spotorno"
"P.A. Croce D'Oro Albissola Marina"
"P.A. Croce Rosa Cellese"
"P.A. Croce Verde Albisola Superiore"
"P.A. Croce Verde Bardineto"
"P.A. Croce Verde Finalborgo"
"P.A. Croce Verde Murialdo"

        Imperia
"P.A. Croce Bianca Imperia Onlus"
"P.A. Croce Bianca Pornassio"
"P.A. Croce D'Oro Cervo"

        La Spezia
"P.A. \"Humanitas\" Romito Magra"
"P.A. Croce Azzurra Bonassola"
"P.A. Croce Azzurra Brugnato"
"P.A. Croce Bianca Beverino"
"P.A. Croce Bianca Le Grazie"
"P.A. Croce Bianca Monterosso Al Mare"
"P.A. Croce Bianca Portovenere"
"P.A. Croce Bianca Riomaggiore"
"P.A. Croce Bianca Santo Stefano Magra"
"P.A. Croce D'Oro Deivese"
"P.A. Croce Gialla"
"P.A. Croce Rosso-Bianca Lerici"
"P.A. Croce Verde Arcola"
"P.A. Croce Verde Borghetto Vara"
"P.A. Croce Verde Carro"
"P.A. Croce Verde Levanto"
"P.A. Croce Verde Manarola"
"P.A. Croce Verde Pignone"
"P.A. Croce Verde Vernazza"
"P.A. Croce Verde Zignago"
"P.A. Framurese"
"P.A. La Misericordia Et Olmo"
"P.A. Luni Onlus"
"P.A. Pitelli"
"P.A. Vezzano Ligure"
*/
    }

    private Color getDefaultCrossColor(String centrale) {
        Color resource;
        String cent = centrale.toLowerCase();
        if (cent.contains("oro ")) {
            resource = Color.GOLD;
        } else if (cent.contains("bianca") || cent.contains("cb")) {
            resource = Color.WHITE;
        } else if (cent.contains("blu")) {
            resource = Color.BLUE;
        } else if (cent.contains("celeste")) {
            resource = Color.CELESTE;
        } else if (cent.contains("verde ") || cent.contains(" cv ")) {
            resource = Color.GREEN;
        } else if (cent.contains("azzurra")) {
            resource = Color.LIGHT_BLUE;
        } else if (cent.contains("rosa")) {
            resource = Color.ROSE;
        } else if (cent.contains("rossa") || cent.contains("cri ") || cent.contains(" cr ")) {
            resource = Color.RED;
        } else if (cent.contains("gialla")) {
            resource = Color.YELLOW;
        } else if (cent.contains("elisoccorso")) {
            resource = Color.ELI;
        } else {
            resource = Color.CROSS;
        }
        return resource;
    }


}
