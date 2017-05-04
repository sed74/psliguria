package com.sed.federico.prontosoccorsoligura;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

/**
 * Created by federico.marchesi on 13/04/2017.
 */


public class Centrale {
    private int mId;
    private String mCodice;
    private String mDescrizione;
    private String mCitta;
    private String mCentrale;
    private CrossImage mCrossImage;
//    private Color mCrossColor;

    public Centrale() {
    }


    public Centrale(int id, String codice, String descrizione, String citta, String centrale) {
        this.mId = id;
        this.mCodice = codice;
        this.mDescrizione = descrizione;
        this.mCitta = citta;
        this.mCentrale = centrale;
        this.populateExtraInfo();
    }

    public Centrale(String codice, String descrizione) {
        this.mCodice = codice;
        this.mDescrizione = descrizione;
        this.populateExtraInfo();
    }

    public Centrale(String mCodice) {
        this.mCodice = mCodice;
        this.populateExtraInfo();
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
        return mCodice;
    }

    public void setCodice(String codice) {
        this.mCodice = codice;
    }

    public String getDescription() {
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

    private void populateExtraInfo() {
        int crossColor;
        switch (mCodice) {
            case "CRTORR":
                mDescrizione = "Croce Rossa Torriglia";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "GIALLA":
                mDescrizione = "P.A. Croce Gialla";
                mCrossImage = new CrossImage(Color.YELLOW);
                break;
            case "BGL":
                mDescrizione = "P.A. Croce Verde Bogliasco";
                mCrossImage = new CrossImage(Color.GREEN);
            case "VER":
                mDescrizione = "P.A. Croce Verde Genovese";
                mCrossImage = new CrossImage(Color.GREEN);
                break;
            case "BIA":
                mDescrizione = "P.A. Croce Bianca Genovese";
                mCrossImage = new CrossImage(Color.WHITE);
                break;
            case "ISO":
                mDescrizione = "P.A. Croce Verde Isoverde";
                mCrossImage = new CrossImage(Color.GREEN);
                break;
            case "SES":
                mDescrizione = "P.A. Croce Verde Sestri Ponente";
                mCrossImage = new CrossImage(Color.GREEN);
                mCrossImage = new CrossImage(Color.GREEN);
                break;
            case "PRA":
                mDescrizione = "P.A. Croce Verde Praese";
                mCrossImage = new CrossImage(Color.GREEN);
                break;
            case "PGL":
                mDescrizione = "P.A. Croce Verde Pegliese";
                mCrossImage = new CrossImage(Color.GREEN);
                break;
            case "QUI":
                mDescrizione = "P.A. Croce Verde Quinto";
                mCrossImage = new CrossImage(Color.GREEN);
                break;
            case "ORO":
                mDescrizione = "P.A. Croce D'oro Sampierdarena";
                mCrossImage = new CrossImage(Color.GOLD);
                break;
            case "PDE":
                mDescrizione = "P.A. Croce Verde Pontedecimo";
                mCrossImage = new CrossImage(Color.GREEN);
                break;
            case "BOL":
                mDescrizione = "P.A. Croce Bianca Bolzaneto";
                mCrossImage = new CrossImage(Color.WHITE);
                break;
            case "MANESS":
                mDescrizione = "P.A. Croce D'oro Manesseno";
                mCrossImage = new CrossImage(Color.GOLD);
                break;
            case "RIV":
                mDescrizione = "P.A. Croce Rosa Rivarolo";
                mCrossImage = new CrossImage(Color.ROSE);
                break;
//            case "POS":
//                mDescrizione = "P.A. Croce Verde Praese";
//                mCrossImage = new CrossImage(Color.RED);
//                break;
            case "BLU":
                mDescrizione = "P.A. Croce Blu Castelletto";
                mCrossImage = new CrossImage(Color.BLUE);
                break;
            case "ORG":
                mDescrizione = "P.A. Croce Blu dist. Oregina";
                mCrossImage = new CrossImage(Color.BLUE);
                break;
            case "CRMASO":
                mDescrizione = "Croce Rossa Masone";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CRCOGO":
                mDescrizione = "Croce Rossa Cogoleto";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CRROSS":
                mDescrizione = "Croce Rossa Rossiglione";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CRAREN":
                mDescrizione = "Croce Rossa Arenzano";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CRGENO":
                mDescrizione = "Croce Rossa Genova";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CRRONC":
                mDescrizione = "Croce Rossa Ronco Scrivia";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CRVTR":
                mDescrizione = "Croce Rossa Voltri";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CRSORI":
                mDescrizione = "Croce Rossa Sori";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CRUSCI":
                mDescrizione = "Croce Rossa Uscio";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CRCMOR":
                mDescrizione = "Croce Rossa Campomorone";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CRBARG":
                mDescrizione = "Croce Rossa Bargagli";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CRDAVA":
                mDescrizione = "Croce Rossa Davagna";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CRMONT":
                mDescrizione = "Croce Rossa Montoggio";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CRSTUR":
                mDescrizione = "Croce Rossa Sturla";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CRCERA":
                mDescrizione = "Croce Rossa Ceranesi";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CRTERR":
                mDescrizione = "Croce Rossa Ceranesi";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CRMTBRUNO":
                mDescrizione = "Croce Rossa Montebruno";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CRCLIG":
                mDescrizione = "Croce Rossa Campo Ligure";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CRRICC":
                mDescrizione = "Croce Rossa Serra Riccò";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "CROLCE":
                mDescrizione = "Croce Rossa Sant'Olcese";
                mCrossImage = new CrossImage(Color.RED);
                break;
            case "FIU":
                mDescrizione = "Volontari Del Soccorso Fiumara";
                mCrossImage = new CrossImage(Color.CROSS);
                break;
            case "BUR":
                mDescrizione = "P.A. Croce Verde San Gottardo - Sezione Burlando";
                mCrossImage = new CrossImage(Color.GREEN);
                break;
            case "BUS":
                mDescrizione = "P.A. Croce Verde Busallese";
                mCrossImage = new CrossImage(Color.GREEN);
                break;
            case "MOL":
                mDescrizione = "P.A. Molassana";
                mCrossImage = new CrossImage(Color.CROSS);
                break;
            case "SGT":
                mDescrizione = "P.A. Croce Verde San Gottardo";
                mCrossImage = new CrossImage(Color.GREEN);
                break;
            case "SQR":
                mDescrizione = "P.A. Croce Rosa San Quirico";
                mCrossImage = new CrossImage(Color.ROSE);
                break;
            case "QUA":
                mDescrizione = "P.A. Croce Verde Quarto dei Mille";
                mCrossImage = new CrossImage(Color.GREEN);
                break;
            case "FEG":
                mDescrizione = "P.A. Croce Azzurra Fegino";
                mCrossImage = new CrossImage(Color.LIGHT_BLUE);
                break;
            case "MEL":
                mDescrizione = "P.A. Croce Verde Mele";
                mCrossImage = new CrossImage(Color.GREEN);
                break;
            case "COR":
                mDescrizione = "P.A. Croce Bianca Cornigliano";
                mCrossImage = new CrossImage(Color.WHITE);
                break;
            case "RUT":
                mDescrizione = "P.A. Volontari Del Soccorso Ruta Di Camogli";
                mCrossImage = new CrossImage(Color.CROSS);
                break;
            case "NER":
                mDescrizione = "P.A. Nerviese";
                mCrossImage = new CrossImage(Color.CROSS);
                break;
            case "RCC":
                mDescrizione = "P.A. Croce Verde Recco";
                mCrossImage = new CrossImage(Color.GREEN);
                break;
            case "MISGE":
                mDescrizione = "P.A. Misericordia Genova Centro";
                mCrossImage = new CrossImage(Color.CROSS);
                break;
            case "CAS":
                mDescrizione = "P.A. Croce Verde Casellese";
                mCrossImage = new CrossImage(Color.GREEN);
                break;
            case "STR":
                mDescrizione = "P.A. G.A.U. Struppa";
                mCrossImage = new CrossImage(Color.CROSS);
                break;
            case "CAM":
                mDescrizione = "P.A. Croce Verde Camogliese";
                mCrossImage = new CrossImage(Color.GREEN);
                break;
            case "VAL":
                mDescrizione = "P.A. Croce Bianca Valsecca";
                mCrossImage = new CrossImage(Color.WHITE);
                break;
            case "MIG":
                mDescrizione = "P.A. Croce Bianca Mignanego";
                mCrossImage = new CrossImage(Color.WHITE);
                break;
            case "LUM":
                mDescrizione = "P.A. Croce Verde Lumarzo";
                mCrossImage = new CrossImage(Color.GREEN);
                break;
            case "CEL":
                mDescrizione = "P.A. Croce Celeste San Benigno";
                mCrossImage = new CrossImage(Color.CELESTE);
                break;
            case "BOR":
                mDescrizione = "P.A. Croce Azzurra Borzoli";
                mCrossImage = new CrossImage(Color.LIGHT_BLUE);
                break;
            case "CCF":
                mDescrizione = "P.A. Croce Verde Crocefieschi";
                mCrossImage = new CrossImage(Color.GREEN);
                break;
            case "LERCA":
                mDescrizione = "P.A. Croce d'Oro Sciarborasca - Sezione Lerca";
                mCrossImage = new CrossImage(Color.GOLD);
                break;
            case "SCI":
                mDescrizione = "P.A. Croce d'Oro Sciarborasca";
                mCrossImage = new CrossImage(Color.GOLD);
                break;
            case "BAV":
                mDescrizione = "P.A. Croce Azzurra Bavari";
                mCrossImage = new CrossImage(Color.LIGHT_BLUE);
                break;
            case "A.V.T.":
                mDescrizione = "P.A. Volontari Del Soccorso Alta Val Trebbia";
                mCrossImage = new CrossImage(Color.CROSS);
                break;
            case "TOR":
                mDescrizione = "P.A. Croce Bianca Torrazza";
                mCrossImage = new CrossImage(Color.WHITE);
                break;
            case "CROCE TIGLIETO":
                mDescrizione = "P.A. Croce Bianca Tiglieto";
                mCrossImage = new CrossImage(Color.WHITE);
                break;
            case "FONGOR":
                mDescrizione = "P.A. Fontanigorda";
                mCrossImage = new CrossImage(Color.CROSS);
                break;
            case "CNSAS SOCCORSO ALPINO":
                mDescrizione = "Soccorso Alpino Genova";
                mCrossImage = new CrossImage(Color.CROSS);
                break;
            default:
                mDescrizione = mCodice;
                mCrossImage = new CrossImage(getDefaultCrossColor(mCodice));
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
