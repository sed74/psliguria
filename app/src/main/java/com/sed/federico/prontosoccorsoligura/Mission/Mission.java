package com.sed.federico.prontosoccorsoligura.Mission;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by federico.marchesi on 05/04/2017.
 */

public class Mission {

    private Context mContext;

    private String mMissionNo;
    private String mAmbulanceNo;
    private String mPostazione;
    private HashMap<String, String> mPubblicaAssistenza;
    private String mCode;
    private String mLocation;
    private MissionSynthesis mMissionSynthesis;
    private String mSynthesis;
    private String mDestination;
    private String mAsl;
    private String mCentrale;

    public Mission(Context context) {
        this.mContext = context;
    }

    public Mission(Context context, String missionNo, String ambulanceNo,
                   String pubblicaAssitenza, String code, String location,
                   String synthesis, String destination, String asl) {
        this.mContext = context;
        this.mMissionNo = missionNo;
        this.mAmbulanceNo = ambulanceNo;
        this.mPostazione = pubblicaAssitenza;
        this.mCode = code;
        this.mLocation = location;
        this.mSynthesis = synthesis;
        this.mMissionSynthesis = new MissionSynthesis(mContext, mSynthesis);
        this.mDestination = destination;
        this.mAsl = asl;
        initPostazione();
    }

    public Mission(Context context, String missionNo, String ambulanceNo,
                   String pubblicaAssitenza, String code, String location,
                   String synthesis, String destination, String asl, String centrale) {
        this.mContext = context;
        this.mMissionNo = missionNo;
        this.mAmbulanceNo = ambulanceNo;
        this.mPostazione = pubblicaAssitenza;
        this.mCode = code;
        this.mLocation = location;
        this.mSynthesis = synthesis;
        this.mMissionSynthesis = new MissionSynthesis(mContext, mSynthesis);
        this.mDestination = destination;
        this.mAsl = asl;
        this.mCentrale = centrale;
        initPostazione();
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

    private void initPostazione() {
        mPubblicaAssistenza = new HashMap<>();

//        Genova
        mPubblicaAssistenza.put("CRTORR", "P.A. Torriglia");
        mPubblicaAssistenza.put("GIALLA", "P.A. Croce Gialla");
        mPubblicaAssistenza.put("BGL", "P.A. Croce Verde Bogliasco");
        mPubblicaAssistenza.put("VER", "P.A. Croce Verde Genovese");
        mPubblicaAssistenza.put("BIA", "P.A. Croce Bianca Genovese");
        mPubblicaAssistenza.put("ISO", "P.A. Isoverde");
        mPubblicaAssistenza.put("SES", "P.A. Sestri Ponente");
        mPubblicaAssistenza.put("PRA", "P.A. Prato");
        mPubblicaAssistenza.put("PGL", "P.A. Croce Verde Pegliese");
        mPubblicaAssistenza.put("QUI", "P.A. Quinto");
        mPubblicaAssistenza.put("RIV", "P.A. Rivarolo");
        mPubblicaAssistenza.put("ORO", "P.A. Croce D'oro Sampierdarena");
        mPubblicaAssistenza.put("PDE", "P.A. Croce Verde Pontedecimo");
        mPubblicaAssistenza.put("BOL", "P.A. Croce Bianca Bolzaneto");
        mPubblicaAssistenza.put("MANESS", "P.A. Croce D'oro Manesseno");
        mPubblicaAssistenza.put("RIV", "P.A. Croce Rosa Rivarolo");
        mPubblicaAssistenza.put("POS", "P.A. Croce Verde Praese");
        mPubblicaAssistenza.put("RIV", "P.A. Croce Rosa San Rivarolo");
        mPubblicaAssistenza.put("BLU", "P.A. Croce Blu Castelletto");
        mPubblicaAssistenza.put("CRMASO", "Croce Rossa Masone");
        mPubblicaAssistenza.put("CRCOGO", "Croce Rossa Cogoleto");
        mPubblicaAssistenza.put("CRROSS", "Croce Rossa Rossiglione");
        mPubblicaAssistenza.put("CRAREN", "Croce Rossa Arenzano");
        mPubblicaAssistenza.put("CRGENO", "Croce Rossa Genova");
        mPubblicaAssistenza.put("CRRONC", "Croce Rossa Ronco Scrivia");
        mPubblicaAssistenza.put("CRVTR", "Croce Rossa Voltri");
        mPubblicaAssistenza.put("CRSORI", "Croce Rossa Sori");
        mPubblicaAssistenza.put("CRUSCI", "Croce Rossa Uscio");
        mPubblicaAssistenza.put("CRCMOR", "Croce Rossa Campomorone");
        mPubblicaAssistenza.put("CRBARG", "Croce Rossa Bargagli");
        mPubblicaAssistenza.put("CRDAVA", "Croce Rossa Davagna");
        mPubblicaAssistenza.put("CRMONT", "Croce Rossa Montoggio");
        mPubblicaAssistenza.put("CRSTUR", "Croce Rossa Sturla");
        mPubblicaAssistenza.put("CRCERA", "Croce Rossa Ceranesi");
        mPubblicaAssistenza.put("CRTERR", "Croce Rossa Ceranesi");
        mPubblicaAssistenza.put("FIU", "Volontari Del Soccorso Fiumara");
        mPubblicaAssistenza.put("BUR", "P.A. Croce Verde San Gottardo - Sezione Burlando");
        mPubblicaAssistenza.put("BUS", "P.A. Croce Verde Busallese");
        mPubblicaAssistenza.put("MOL", "P.A. Molassana");
        mPubblicaAssistenza.put("SGT", "P.A. Croce Verde San Gottardo");
        mPubblicaAssistenza.put("SQR", "P.A. Croce Rosa San Quirico");
        mPubblicaAssistenza.put("QUA", "P.A. Croce Verde Quarto dei Mille");
        mPubblicaAssistenza.put("FEG", "P.A. Croce Azzurra Fegino");
        mPubblicaAssistenza.put("MEL", "P.A. Croce Verde Mele");
        mPubblicaAssistenza.put("COR", "P.A. Croce Bianca Cornigliano");
        mPubblicaAssistenza.put("RUT", "P.A. Volontari Del Soccorso Ruta Di Camogli");
        mPubblicaAssistenza.put("NER", "P.A. Nerviese");
        mPubblicaAssistenza.put("RCC", "P.A. Croce Verde Recco");
        mPubblicaAssistenza.put("MISGE", "P.A. Misericordia Genova Centro");
        mPubblicaAssistenza.put("CAS", "P.A. Croce Verde Casellese");
        mPubblicaAssistenza.put("STR", "P.A. G.A.U. Struppa");
        mPubblicaAssistenza.put("CAM", "P.A. Croce Verde Camogliese");
        mPubblicaAssistenza.put("VAL", "P.A. Croce Bianca Valsecca");
        mPubblicaAssistenza.put("MIG", "P.A. Croce Bianca Mignanego");
        mPubblicaAssistenza.put("LUM", "P.A. Croce Verde Lumarzo");
        mPubblicaAssistenza.put("CEL", "P.A. Croce Celeste San Benigno");
        mPubblicaAssistenza.put("BOR", "P.A. Croce Azzurra Borzoli");
        mPubblicaAssistenza.put("CEL", "P.A. Croce Celeste");
        mPubblicaAssistenza.put("CCF", "P.A. Croce Verde Crocefieschi");

        //BUR
        //FIU
        //CCF
//        mPubblicaAssistenza.put("", "Associazione Cinofili Da Soccorso \"Il Branco\" Onlus");
//        mPubblicaAssistenza.put("", "Medicina E Progresso");
//        mPubblicaAssistenza.put("", "P.A. Croce Azzurra Bavari");
//        mPubblicaAssistenza.put("", "P.A. Croce Bianca Rapallese");
//        mPubblicaAssistenza.put("", "P.A. Croce Bianca San Desiderio");
//        mPubblicaAssistenza.put("", "P.A. Croce Bianca Tiglieto");
//        mPubblicaAssistenza.put("", "P.A. Croce Bianca Torrazza");
//        mPubblicaAssistenza.put("", "P.A. Croce D'oro Fascia");
//        mPubblicaAssistenza.put("", "P.A. Croce D'oro Sciarborasca");
//        mPubblicaAssistenza.put("", "P.A. Croce Rosa Di Trensasco");
//        mPubblicaAssistenza.put("", "P.A. Croce Verde Isoverde");
//        mPubblicaAssistenza.put("", "P.A. Croce Verde Quarto");
//        mPubblicaAssistenza.put("", "P.A. Croce Verde Quinto");
//        mPubblicaAssistenza.put("", "P.A. Croce Verde Sestri Ponente");
//        mPubblicaAssistenza.put("", "P.A. Croce Verde Vobbia - C/O Grosso Zaverio");
//        mPubblicaAssistenza.put("", "P.A. Fontanigorda");
//        mPubblicaAssistenza.put("", "P.A. Volontari Del Soccorso Alta Val Trebbia");
//        mPubblicaAssistenza.put("", "P.A. Volontari Del Soccorso Sestri Levante");
//
//
////        Savona
//        mPubblicaAssistenza.put("", "P.A. Croce Azzurra Calizzano");
//        mPubblicaAssistenza.put("", "P.A. Croce Bianca Alassio ");
//        mPubblicaAssistenza.put("", "P.A. Croce Bianca Albenga");
//        mPubblicaAssistenza.put("", "P.A. Croce Bianca Altare");
//        mPubblicaAssistenza.put("", "P.A. Croce Bianca Andora");
//        mPubblicaAssistenza.put("", "P.A. Croce Bianca Borghetto Santo Spirito");
//        mPubblicaAssistenza.put("", "P.A. Croce Bianca Borgio Verezzi");
//        mPubblicaAssistenza.put("", "P.A. Croce Bianca Cairo Montenotte");
//        mPubblicaAssistenza.put("", "P.A. Croce Bianca Calice Ligure \"Dott. G. Cesio\"");
//        mPubblicaAssistenza.put("", "P.A. Croce Bianca Carcare");
//        mPubblicaAssistenza.put("", "P.A. Croce Bianca Dego");
//        mPubblicaAssistenza.put("", "P.A. Croce Bianca Finale Ligure");
//        mPubblicaAssistenza.put("", "P.A. Croce Bianca Giusvalla");
//        mPubblicaAssistenza.put("", "P.A. Croce Bianca Laigueglia");
//        mPubblicaAssistenza.put("", "P.A. Croce Bianca Mioglia");
//        mPubblicaAssistenza.put("", "P.A. Croce Bianca Noli");
//        mPubblicaAssistenza.put("", "P.A. Croce Bianca Pontinvrea");
//        mPubblicaAssistenza.put("", "P.A. Croce Bianca Savona");
//        mPubblicaAssistenza.put("", "P.A. Croce Bianca Spotorno");
//        mPubblicaAssistenza.put("", "P.A. Croce D'oro Albissola Marina");
//        mPubblicaAssistenza.put("", "P.A. Croce Rosa Cellese");
//        mPubblicaAssistenza.put("", "P.A. Croce Verde Albisola Superiore");
//        mPubblicaAssistenza.put("", "P.A. Croce Verde Bardineto");
//        mPubblicaAssistenza.put("", "P.A. Croce Verde Finalborgo");
//        mPubblicaAssistenza.put("", "P.A. Croce Verde Murialdo");
//
////        Imperia
//        mPubblicaAssistenza.put("", "P.A. Croce Bianca Imperia Onlus");
//        mPubblicaAssistenza.put("", "P.A. Croce Bianca Pornassio");
//        mPubblicaAssistenza.put("", "P.A. Croce D'oro Cervo");
//
////        La Spezia
//        mPubblicaAssistenza.put("", "P.A. \"Humanitas\" Romito Magra");
//        mPubblicaAssistenza.put("", "P.A. Croce Azzurra Bonassola");
//        mPubblicaAssistenza.put("", "P.A. Croce Azzurra Brugnato");
//        mPubblicaAssistenza.put("", "P.A. Croce Bianca Beverino");
//        mPubblicaAssistenza.put("", "P.A. Croce Bianca Le Grazie");
//        mPubblicaAssistenza.put("", "P.A. Croce Bianca Monterosso Al Mare");
//        mPubblicaAssistenza.put("", "P.A. Croce Bianca Portovenere");
//        mPubblicaAssistenza.put("", "P.A. Croce Bianca Riomaggiore");
//        mPubblicaAssistenza.put("", "P.A. Croce Bianca Santo Stefano Magra");
//        mPubblicaAssistenza.put("", "P.A. Croce D'oro Deivese");
//        mPubblicaAssistenza.put("", "P.A. Croce Gialla");
//        mPubblicaAssistenza.put("", "P.A. Croce Rosso-Bianca Lerici");
//        mPubblicaAssistenza.put("", "P.A. Croce Verde Arcola");
//        mPubblicaAssistenza.put("", "P.A. Croce Verde Borghetto Vara");
//        mPubblicaAssistenza.put("", "P.A. Croce Verde Carro");
//        mPubblicaAssistenza.put("", "P.A. Croce Verde Levanto");
//        mPubblicaAssistenza.put("", "P.A. Croce Verde Manarola");
//        mPubblicaAssistenza.put("", "P.A. Croce Verde Pignone");
//        mPubblicaAssistenza.put("", "P.A. Croce Verde Vernazza");
//        mPubblicaAssistenza.put("", "P.A. Croce Verde Zignago");
//        mPubblicaAssistenza.put("", "P.A. Framurese");
//        mPubblicaAssistenza.put("", "P.A. La Misericordia Et Olmo");
//        mPubblicaAssistenza.put("", "P.A. Luni Onlus");
//        mPubblicaAssistenza.put("", "P.A. Pitelli");
//        mPubblicaAssistenza.put("", "P.A. Vezzano Ligure");

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

    public String getPubblicaAssistenza(String postazione) {
        return mPubblicaAssistenza.get(postazione);
    }

    public String getPubblicaAssistenza() {
        String pubblicaAssistenza = mPubblicaAssistenza.get(mPostazione);
        if (pubblicaAssistenza == null) pubblicaAssistenza = mPostazione;
        return pubblicaAssistenza;
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

    public String getCharlie() {
        return mMissionSynthesis.getCharlieCode() + " - " + mMissionSynthesis.getPatologia();
    }

    public String getEmergencyCode() {
        return mMissionSynthesis.getEmergencyCode();
    }
}
