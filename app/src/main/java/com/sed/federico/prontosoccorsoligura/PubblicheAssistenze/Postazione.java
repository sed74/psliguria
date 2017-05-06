package com.sed.federico.prontosoccorsoligura.PubblicheAssistenze;

import com.sed.federico.prontosoccorsoligura.Color;
import com.sed.federico.prontosoccorsoligura.CrossImage;

/**
 * Created by federico.marchesi on 13/04/2017.
 */


public class Postazione {
    private int mId;
    private String mCode;
    private String mName;
    private String mCity;
    private String mCentrale;
    private String mAddress;
    private int mTotAmbulance;
    private double mAvgMission;
    private double mAvgWhite;
    private double mAvgGreen;
    private double mAvgYellow;
    private double mAvgRed;
    private CrossImage mCrossImage;

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


    public Postazione(int id, String codice, String descrizione, String citta, String centrale) {
        this.mId = id;
        this.mCode = codice;
        this.mName = descrizione;
        this.mCity = citta;
        this.mCentrale = centrale;
        this.mCrossImage = new CrossImage(Color.CROSS);
        if (!codice.equalsIgnoreCase("BIA"))
            this.populateExtraInfo();
        else
            mName = descrizione;
    }

    public Postazione() {
    }

    public Postazione(int id, String code, String name, String address, int totAmbulance,
                      double totMission, double avgWhite, double avgGreen, double avgYellow, double avgRed) {
        this.mId = id;
        this.mCode = code;
        this.mName = name;
        this.mAddress = address;
        this.mTotAmbulance = totAmbulance;
        this.mAvgMission = totMission;
        this.mAvgWhite = avgWhite;
        this.mAvgGreen = avgGreen;
        this.mAvgYellow = avgYellow;
        this.mAvgRed = avgRed;
        this.populateExtraInfo();
    }

    public Postazione(String codice, String descrizione) {
        this(0, codice, descrizione, "", "");
    }

    public Postazione(String codice) {
        this(0, codice, "", "", "");
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

    public String getCodice() {
        return mCode;
    }

    public void setCodice(String codice) {
        this.mCode = codice;
        this.populateExtraInfo();
    }

    public String getDescription() {
        return mName;
    }

    public void setDescrizione(String descrizione) {
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
                mName = "Croce Rossa Torriglia";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "GIALLA":
                mName = "P.A. Croce Gialla";
                mCrossImage = new CrossImage(Color.YELLOW);
                break;
            case "BGL":
                mName = "P.A. Croce Verde Bogliasco";
                mCrossImage = new CrossImage(Color.GREEN);
            case "VER":
                mName = "P.A. Croce Verde Genovese";
                mCrossImage = new CrossImage(Color.GREEN);
                break;
            case "BIA":
                mName = "P.A. Croce Bianca Genovese";
                mCrossImage = new CrossImage(Color.WHITE);
                break;
            case "ISO":
                mName = "P.A. Croce Verde Isoverde";
                mCrossImage = new CrossImage(Color.GREEN);
                break;
            case "SES":
                mName = "P.A. Croce Verde Sestri Ponente";
                mCrossImage = new CrossImage(Color.GREEN);
                break;
            case "PRA":
                mName = "P.A. Croce Verde Praese";
                mCrossImage = new CrossImage(Color.GREEN);
                break;
            case "PGL":
                mName = "P.A. Croce Verde Pegliese";
                mCrossImage = new CrossImage(Color.GREEN);
                break;
            case "QUI":
                mName = "P.A. Croce Verde Quinto";
                mCrossImage = new CrossImage(Color.GREEN);
                break;
            case "ORO":
                mName = "P.A. Croce D'oro Sampierdarena";
                mCrossImage = new CrossImage(Color.GOLD);
                break;
            case "PDE":
                mName = "P.A. Croce Verde Pontedecimo";
                mCrossImage = new CrossImage(Color.GREEN);
                break;
            case "BOL":
                mName = "P.A. Croce Bianca Bolzaneto";
                mCrossImage = new CrossImage(Color.WHITE);
                break;
            case "MANESS":
                mName = "P.A. Croce D'oro Manesseno";
                mCrossImage = new CrossImage(Color.GOLD);
                break;
            case "RIV":
                mName = "P.A. Croce Rosa Rivarolo";
                mCrossImage = new CrossImage(Color.ROSE);
                break;
//            case "POS":
//                mName = "P.A. Croce Verde Praese";
//                mCrossImage = new CrossImage(Color.RED);
//                break;
            case "BLU":
                mName = "P.A. Croce Blu Castelletto";
                mCrossImage = new CrossImage(Color.BLUE);
                break;
            case "ORG":
                mName = "P.A. Croce Blu dist. Oregina";
                mCrossImage = new CrossImage(Color.BLUE);
                break;
            case "CRMASO":
                mName = "Croce Rossa Masone";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CRCOGO":
                mName = "Croce Rossa Cogoleto";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CRROSS":
                mName = "Croce Rossa Rossiglione";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CRAREN":
                mName = "Croce Rossa Arenzano";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CRGENO":
                mName = "Croce Rossa Genova";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CRRONC":
                mName = "Croce Rossa Ronco Scrivia";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CRVTR":
                mName = "Croce Rossa Voltri";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CRSORI":
                mName = "Croce Rossa Sori";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CRUSCI":
                mName = "Croce Rossa Uscio";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CRCMOR":
                mName = "Croce Rossa Campomorone";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CRBARG":
                mName = "Croce Rossa Bargagli";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CRDAVA":
                mName = "Croce Rossa Davagna";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CRMONT":
                mName = "Croce Rossa Montoggio";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CRSTUR":
                mName = "Croce Rossa Sturla";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CRCERA":
                mName = "Croce Rossa Ceranesi";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CRTERR":
                mName = "Croce Rossa Ceranesi";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CRMTBRUNO":
                mName = "Croce Rossa Montebruno";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CRCLIG":
                mName = "Croce Rossa Campo Ligure";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CRRICC":
                mName = "Croce Rossa Serra Riccò";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CROLCE":
                mName = "Croce Rossa Sant'Olcese";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "FIU":
                mName = "Volontari Del Soccorso Fiumara";
                mCrossImage = new CrossImage(Color.CROSS);
                break;
            case "BUR":
                mName = "P.A. Croce Verde San Gottardo - Sezione Burlando";
                mCrossImage = new CrossImage(Color.GREEN);
                break;
            case "BUS":
                mName = "P.A. Croce Verde Busallese";
                mCrossImage = new CrossImage(Color.GREEN);
                break;
            case "MOL":
                mName = "P.A. Molassana";
                mCrossImage = new CrossImage(Color.CROSS);
                break;
            case "SGT":
                mName = "P.A. Croce Verde San Gottardo";
                mCrossImage = new CrossImage(Color.GREEN);
                break;
            case "SQR":
                mName = "P.A. Croce Rosa San Quirico";
                mCrossImage = new CrossImage(Color.ROSE);
                break;
            case "QUA":
                mName = "P.A. Croce Verde Quarto dei Mille";
                mCrossImage = new CrossImage(Color.GREEN);
                break;
            case "FEG":
                mName = "P.A. Croce Azzurra Fegino";
                mCrossImage = new CrossImage(Color.LIGHT_BLUE);
                break;
            case "MEL":
                mName = "P.A. Croce Verde Mele";
                mCrossImage = new CrossImage(Color.GREEN);
                break;
            case "COR":
                mName = "P.A. Croce Bianca Cornigliano";
                mCrossImage = new CrossImage(Color.WHITE);
                break;
            case "RUT":
                mName = "P.A. Volontari Del Soccorso Ruta Di Camogli";
                mCrossImage = new CrossImage(Color.CROSS);
                break;
            case "NER":
                mName = "P.A. Nerviese";
                mCrossImage = new CrossImage(Color.CROSS);
                break;
            case "RCC":
                mName = "P.A. Croce Verde Recco";
                mCrossImage = new CrossImage(Color.GREEN);
                break;
            case "MISGE":
                mName = "P.A. Misericordia Genova Centro";
                mCrossImage = new CrossImage(Color.CROSS);
                break;
            case "CAS":
                mName = "P.A. Croce Verde Casellese";
                mCrossImage = new CrossImage(Color.GREEN);
                break;
            case "STR":
                mName = "P.A. G.A.U. Struppa";
                mCrossImage = new CrossImage(Color.CROSS);
                break;
            case "CAM":
                mName = "P.A. Croce Verde Camogliese";
                mCrossImage = new CrossImage(Color.GREEN);
                break;
            case "VAL":
                mName = "P.A. Croce Bianca Valsecca";
                mCrossImage = new CrossImage(Color.WHITE);
                break;
            case "MIG":
                mName = "P.A. Croce Bianca Mignanego";
                mCrossImage = new CrossImage(Color.WHITE);
                break;
            case "LUM":
                mName = "P.A. Croce Verde Lumarzo";
                mCrossImage = new CrossImage(Color.GREEN);
                break;
            case "CEL":
                mName = "P.A. Croce Celeste San Benigno";
                mCrossImage = new CrossImage(Color.CELESTE);
                break;
            case "BOR":
                mName = "P.A. Croce Azzurra Borzoli";
                mCrossImage = new CrossImage(Color.LIGHT_BLUE);
                break;
            case "CCF":
                mName = "P.A. Croce Verde Crocefieschi";
                mCrossImage = new CrossImage(Color.GREEN);
                break;
            case "LERCA":
                mName = "P.A. Croce d'Oro Sciarborasca - Sezione Lerca";
                mCrossImage = new CrossImage(Color.GOLD);
                break;
            case "SCI":
                mName = "P.A. Croce d'Oro Sciarborasca";
                mCrossImage = new CrossImage(Color.GOLD);
                break;
            case "BAV":
                mName = "P.A. Croce Azzurra Bavari";
                mCrossImage = new CrossImage(Color.LIGHT_BLUE);
                break;
            case "A.V.T.":
                mName = "P.A. Volontari Del Soccorso Alta Val Trebbia";
                mCrossImage = new CrossImage(Color.CROSS);
                break;
            case "TOR":
                mName = "P.A. Croce Bianca Torrazza";
                mCrossImage = new CrossImage(Color.WHITE);
                break;
            case "CROCE TIGLIETO":
                mName = "P.A. Croce Bianca Tiglieto";
                mCrossImage = new CrossImage(Color.WHITE);
                break;
            case "FONGOR":
                mName = "P.A. Fontanigorda";
                mCrossImage = new CrossImage(Color.CROSS);
                break;
            case "CNSAS SOCCORSO ALPINO":
                mName = "Soccorso Alpino Genova";
                mCrossImage = new CrossImage(Color.CROSS);
                break;
            default:
                mName = mCode;
                mCrossImage = new CrossImage(getDefaultCrossColor(mCode));
        }
/*
"Associazione Cinofili Da Soccorso \"Il Branco\" Onlus"
"Medicina E Progresso"
"P.A. Croce Bianca Rapallese"
"P.A. Croce Bianca San Desiderio"
"P.A. Croce D'oro Fascia"
"P.A. Croce D'oro Sciarborasca"
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
"P.A. Croce D'oro Albissola Marina"
"P.A. Croce Rosa Cellese"
"P.A. Croce Verde Albisola Superiore"
"P.A. Croce Verde Bardineto"
"P.A. Croce Verde Finalborgo"
"P.A. Croce Verde Murialdo"

        Imperia
"P.A. Croce Bianca Imperia Onlus"
"P.A. Croce Bianca Pornassio"
"P.A. Croce D'oro Cervo"

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
"P.A. Croce D'oro Deivese"
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
