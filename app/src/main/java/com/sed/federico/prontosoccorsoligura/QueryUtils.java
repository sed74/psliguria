/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sed.federico.prontosoccorsoligura;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.sed.federico.prontosoccorsoligura.Mission.Mission;
import com.sed.federico.prontosoccorsoligura.Mission.MissionListCustom;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    /**
     * Firebase Constants
     */
    public static final String FBASE_AMBULANCE_NO = "ambulance_no";
    public static final int TABLET_MIN_WIDTH = 720;
    public static final String HOSPITAL_NAME = "name";
    public static final String WHITE_WAITING = "whiteWaiting";
    public static final String GREEN_WAITING = "greenWaiting";
    public static final String YELLOW_WAITING = "yellowWaiting";
    public static final String RED_WAITING = "redWaiting";
    public static final String WHITE_RUNNING = "whiteRunning";
    public static final String GREEN_RUNNING = "greenRunning";
    public static final String YELLOW_RUNNING = "yellowRunning";
    public static final String RED_RUNNING = "redRunning";
    public static final String OBI = "obi";
    public static final String LASTUPDATED = "lastUpdate";


    public static final String MISSION = "missione";
    public static final String AMBULANCE = "mezzo";
    public static final String PUBBLICA_ASSISTENZA = "postazione";
    public static final String EMERGENCY_CODE = "codice";
    public static final String PICKUP_LOCATION = "localita";
    public static final String SYNTHESIS = "sintesi";
    public static final String DESTINATION = "destinazione";
    public static final String ASL = "asl";
    public static final String CENTRALE = "centrale";

    public static final String CENTRALE_ID = "id";
    public static final String CENTRALE_CODE = "codice";
    public static final String CENTRALE_DESCRIZIONE = "descrizione";
    public static final String CENTRALE_CITTA = "citta";
    public static final String CENTRALE_CENTRALE = "centrale";
    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();
    private static final int READ_TIMEOUT = 15000;
    private static final int CONNECT_TIMEOUT = 15000;


    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }


    /**
     * Query the USGS dataset and return a list of {@link Hospital} objects.
     */
    public static HospitalListCustom fetchHospitalData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        HospitalListCustom earthquakes = extractHospitalFromJson(jsonResponse);

        // Return the list of {@link Earthquake}s
        return earthquakes;
    }

    /**
     * Query the USGS dataset and return a list of {@link Hospital} objects.
     */
    public static CentraleListCustom fetchCentrali(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        CentraleListCustom centrali = extractCentraleFromJson(jsonResponse);

        // Return the list of {@link Earthquake}s
        return centrali;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(READ_TIMEOUT /* milliseconds */);
            urlConnection.setConnectTimeout(CONNECT_TIMEOUT /* milliseconds */);
            urlConnection.setRequestProperty("PSGE", SecurityToken.mSecurityToken);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving JSON results.\n" + url.toString(), e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link Hospital} objects that has been built up from
     * parsing the given JSON response.
     */
    private static HospitalListCustom extractHospitalFromJson(String hospitalJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(hospitalJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        HospitalListCustom hospitals = new HospitalListCustom();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Create a JSONObject from the JSON response string
//            JSONObject baseJsonResponse = new JSONObject(hospitalJSON);

            // Extract the JSONArray associated with the key called "features",
            // which represents a list of features (or earthquakes).
//            JSONArray earthquakeArray = baseJsonResponse.getJSONArray("features");
            JSONArray hospitalsArray = new JSONArray(hospitalJSON);

            // For each earthquake in the earthquakeArray, create an {@link Earthquake} object
            for (int i = 0; i < hospitalsArray.length(); i++) {
                JSONObject hospitalJSONObj = hospitalsArray.getJSONObject(i);
                // Get a single earthquake at position i within the list of earthquakes
//                JSONObject currentEarthquake = earthquakeArray.getJSONObject(i);

                // For a given earthquake, extract the JSONObject associated with the
                // key called "properties", which represents a list of all properties
                // for that earthquake.
//                JSONObject properties = currentEarthquake.getJSONObject("properties");

                String name = hospitalJSONObj.getString(HOSPITAL_NAME);
                int ww = hospitalJSONObj.getInt(WHITE_WAITING);
                int gw = hospitalJSONObj.getInt(GREEN_WAITING);
                int yw = hospitalJSONObj.getInt(YELLOW_WAITING);
                int rw = hospitalJSONObj.getInt(RED_WAITING);
                int wr = hospitalJSONObj.getInt(WHITE_RUNNING);
                int gr = hospitalJSONObj.getInt(GREEN_RUNNING);
                int yr = hospitalJSONObj.getInt(YELLOW_RUNNING);
                int rr = hospitalJSONObj.getInt(RED_RUNNING);
                int obi = hospitalJSONObj.getInt(OBI);
                String lastUpdated = hospitalJSONObj.getString(LASTUPDATED);


                // Create a new {@link Earthquake} object with the magnitude, location, time,
                // and url from the JSON Hospital.
                Hospital hospital = new Hospital(name, ww, gw, yw, rw, wr, gr, yr, rr, obi, lastUpdated);

                // Add the new {@link Earthquake} to the list of earthquakes.
                hospitals.add(hospital);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return hospitals;
    }

    /**
     * Return a list of {@link Hospital} objects that has been built up from
     * parsing the given JSON response.
     */
    private static CentraleListCustom extractCentraleFromJson(String hospitalJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(hospitalJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        CentraleListCustom centrali = new CentraleListCustom();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            JSONArray centraliArray = new JSONArray(hospitalJSON);

            // For each earthquake in the earthquakeArray, create an {@link Earthquake} object
            for (int i = 0; i < centraliArray.length(); i++) {
                JSONObject hospitalJSONObj = centraliArray.getJSONObject(i);

                int id = hospitalJSONObj.getInt(CENTRALE_ID);
                String code = hospitalJSONObj.getString(CENTRALE_CODE);
                String descr = hospitalJSONObj.getString(CENTRALE_DESCRIZIONE);
                String city = hospitalJSONObj.getString(CENTRALE_CITTA);
                String centrale = hospitalJSONObj.getString(CENTRALE_CENTRALE);

                Centrale c = new Centrale(id, code, descr, city, centrale);

                // Add the new {@link Earthquake} to the list of earthquakes.
                centrali.add(c);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return centrali;
    }

    public static void callWebAPI(String requestUrl) {

        new callWebApi().execute(requestUrl);
    }

    /**
     * Query the USGS dataset and return a list of {@link Hospital} objects.
     */
    public static MissionListCustom fetchMissionData(Context context, String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.\n" + requestUrl, e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        MissionListCustom missions = extractMissionFromJson(context, jsonResponse);

        // Return the list of {@link Earthquake}s
        return missions;
    }

    /**
     * Return a list of {@link MissionListCustom} objects that has been built up from
     * parsing the given JSON response.
     */
    private static MissionListCustom extractMissionFromJson(Context context, String hospitalJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(hospitalJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        MissionListCustom missions = new MissionListCustom();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Create a JSONObject from the JSON response string
//            JSONObject baseJsonResponse = new JSONObject(hospitalJSON);

            // Extract the JSONArray associated with the key called "features",
            // which represents a list of features (or earthquakes).
//            JSONArray earthquakeArray = baseJsonResponse.getJSONArray("features");
            JSONArray missionArray = new JSONArray(hospitalJSON);

            // For each earthquake in the earthquakeArray, create an {@link Earthquake} object
            for (int i = 0; i < missionArray.length(); i++) {
                JSONObject hospitalJSONObj = missionArray.getJSONObject(i);
                // Get a single earthquake at position i within the list of earthquakes
//                JSONObject currentEarthquake = earthquakeArray.getJSONObject(i);

                // For a given earthquake, extract the JSONObject associated with the
                // key called "properties", which represents a list of all properties
                // for that earthquake.
//                JSONObject properties = currentEarthquake.getJSONObject("properties");

                String mission = hospitalJSONObj.getString(MISSION);
                String ambulance = hospitalJSONObj.getString(AMBULANCE);
                String pubblicaAssistenza = hospitalJSONObj.getString(PUBBLICA_ASSISTENZA);
                String emergencyCode = hospitalJSONObj.getString(EMERGENCY_CODE);
                String pickuplocation = hospitalJSONObj.getString(PICKUP_LOCATION);
                String synthesis = hospitalJSONObj.getString(SYNTHESIS);
                String destination = hospitalJSONObj.getString(DESTINATION);
                String asl = hospitalJSONObj.getString(ASL);
                String centrale = hospitalJSONObj.getString(CENTRALE);


                // Create a new {@link Earthquake} object with the magnitude, location, time,
                // and url from the JSON Hospital.
                Mission missionObj = new Mission(context, mission, ambulance, pubblicaAssistenza,
                        emergencyCode, pickuplocation, synthesis, destination, asl, centrale);

                // Add the new {@link Earthquake} to the list of earthquakes.
                missions.add(missionObj);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return missions;
    }

    public static String getPostazioneName(String codice) {
        HashMap<String, String> temp;
        temp = initPostazione();
        String name = temp.get(codice);
        if (name == null) {
            name = codice;
        }
        return name;
    }

    public static HashMap<String, String> initPostazione() {
        HashMap<String, String> pubblicaAssistenza;
        pubblicaAssistenza = new HashMap<>();

//        Genova
        pubblicaAssistenza.put("CRTORR", "P.A. Torriglia");
        pubblicaAssistenza.put("GIALLA", "P.A. Croce Gialla");
        pubblicaAssistenza.put("BGL", "P.A. Croce Verde Bogliasco");
        pubblicaAssistenza.put("VER", "P.A. Croce Verde Genovese");
        pubblicaAssistenza.put("BIA", "P.A. Croce Bianca Genovese");
        pubblicaAssistenza.put("ISO", "P.A. Isoverde");
        pubblicaAssistenza.put("SES", "P.A. Sestri Ponente");
        pubblicaAssistenza.put("PRA", "P.A. Prato");
        pubblicaAssistenza.put("PGL", "P.A. Croce Verde Pegliese");
        pubblicaAssistenza.put("QUI", "P.A. Quinto");
        pubblicaAssistenza.put("RIV", "P.A. Rivarolo");
        pubblicaAssistenza.put("ORO", "P.A. Croce D'oro Sampierdarena");
        pubblicaAssistenza.put("PDE", "P.A. Croce Verde Pontedecimo");
        pubblicaAssistenza.put("BOL", "P.A. Croce Bianca Bolzaneto");
        pubblicaAssistenza.put("MANESS", "P.A. Croce D'oro Manesseno");
        pubblicaAssistenza.put("RIV", "P.A. Croce Rosa Rivarolo");
        pubblicaAssistenza.put("POS", "P.A. Croce Verde Praese");
        pubblicaAssistenza.put("BLU", "P.A. Croce Blu Castelletto");
        pubblicaAssistenza.put("ORG", "P.A. Croce Blu dist. Oregina");
        pubblicaAssistenza.put("CRMASO", "Croce Rossa Masone");
        pubblicaAssistenza.put("CRCOGO", "Croce Rossa Cogoleto");
        pubblicaAssistenza.put("CRROSS", "Croce Rossa Rossiglione");
        pubblicaAssistenza.put("CRAREN", "Croce Rossa Arenzano");
        pubblicaAssistenza.put("CRGENO", "Croce Rossa Genova");
        pubblicaAssistenza.put("CRRONC", "Croce Rossa Ronco Scrivia");
        pubblicaAssistenza.put("CRVTR", "Croce Rossa Voltri");
        pubblicaAssistenza.put("CRSORI", "Croce Rossa Sori");
        pubblicaAssistenza.put("CRUSCI", "Croce Rossa Uscio");
        pubblicaAssistenza.put("CRCMOR", "Croce Rossa Campomorone");
        pubblicaAssistenza.put("CRBARG", "Croce Rossa Bargagli");
        pubblicaAssistenza.put("CRDAVA", "Croce Rossa Davagna");
        pubblicaAssistenza.put("CRMONT", "Croce Rossa Montoggio");
        pubblicaAssistenza.put("CRSTUR", "Croce Rossa Sturla");
        pubblicaAssistenza.put("CRCERA", "Croce Rossa Ceranesi");
        pubblicaAssistenza.put("CRTERR", "Croce Rossa Ceranesi");
        pubblicaAssistenza.put("CRMTBRUNO", "Croce Rossa Montebruno");
        pubblicaAssistenza.put("CRCLIG", "Croce Rossa Campo Ligure");
        pubblicaAssistenza.put("CRRICC", "Croce Rossa Serra Riccò");
        pubblicaAssistenza.put("CROLCE", "Croce Rossa Sant'Olcese");
        pubblicaAssistenza.put("FIU", "Volontari Del Soccorso Fiumara");
        pubblicaAssistenza.put("BUR", "P.A. Croce Verde San Gottardo - Sezione Burlando");
        pubblicaAssistenza.put("BUS", "P.A. Croce Verde Busallese");
        pubblicaAssistenza.put("MOL", "P.A. Molassana");
        pubblicaAssistenza.put("SGT", "P.A. Croce Verde San Gottardo");
        pubblicaAssistenza.put("SQR", "P.A. Croce Rosa San Quirico");
        pubblicaAssistenza.put("QUA", "P.A. Croce Verde Quarto dei Mille");
        pubblicaAssistenza.put("FEG", "P.A. Croce Azzurra Fegino");
        pubblicaAssistenza.put("MEL", "P.A. Croce Verde Mele");
        pubblicaAssistenza.put("COR", "P.A. Croce Bianca Cornigliano");
        pubblicaAssistenza.put("RUT", "P.A. Volontari Del Soccorso Ruta Di Camogli");
        pubblicaAssistenza.put("NER", "P.A. Nerviese");
        pubblicaAssistenza.put("RCC", "P.A. Croce Verde Recco");
        pubblicaAssistenza.put("MISGE", "P.A. Misericordia Genova Centro");
        pubblicaAssistenza.put("CAS", "P.A. Croce Verde Casellese");
        pubblicaAssistenza.put("STR", "P.A. G.A.U. Struppa");
        pubblicaAssistenza.put("CAM", "P.A. Croce Verde Camogliese");
        pubblicaAssistenza.put("VAL", "P.A. Croce Bianca Valsecca");
        pubblicaAssistenza.put("MIG", "P.A. Croce Bianca Mignanego");
        pubblicaAssistenza.put("LUM", "P.A. Croce Verde Lumarzo");
        pubblicaAssistenza.put("CEL", "P.A. Croce Celeste San Benigno");
        pubblicaAssistenza.put("BOR", "P.A. Croce Azzurra Borzoli");
        pubblicaAssistenza.put("CEL", "P.A. Croce Celeste");
        pubblicaAssistenza.put("CCF", "P.A. Croce Verde Crocefieschi");
        pubblicaAssistenza.put("LERCA", "P.A. Croce d'Oro Sciarborasca - Sezione Lerca");
        pubblicaAssistenza.put("SCI", "P.A. Croce d'Oro Sciarborasca");
        pubblicaAssistenza.put("BAV", "P.A. Croce Azzurra Bavari");
        pubblicaAssistenza.put("A.V.T.", "P.A. Volontari Del Soccorso Alta Val Trebbia");
        pubblicaAssistenza.put("TOR", "P.A. Croce Bianca Torrazza");
        pubblicaAssistenza.put("CROCE TIGLIETO", "P.A. Croce Bianca Tiglieto");
        pubblicaAssistenza.put("FONGOR", "P.A. Fontanigorda");
        pubblicaAssistenza.put("CNSAS SOCCORSO ALPINO", "Soccorso Alpino Genova");

        //BUR
        //FIU
        //CCF
//        pubblicaAssistenza.put("", "Associazione Cinofili Da Soccorso \"Il Branco\" Onlus");
//        pubblicaAssistenza.put("", "Medicina E Progresso");
//        pubblicaAssistenza.put("", "P.A. Croce Bianca Rapallese");
//        pubblicaAssistenza.put("", "P.A. Croce Bianca San Desiderio");
//        pubblicaAssistenza.put("", "P.A. Croce D'oro Fascia");
//        pubblicaAssistenza.put("", "P.A. Croce D'oro Sciarborasca");
//        pubblicaAssistenza.put("", "P.A. Croce Rosa Di Trensasco");
//        pubblicaAssistenza.put("", "P.A. Croce Verde Isoverde");
//        pubblicaAssistenza.put("", "P.A. Croce Verde Quarto");
//        pubblicaAssistenza.put("", "P.A. Croce Verde Quinto");
//        pubblicaAssistenza.put("", "P.A. Croce Verde Sestri Ponente");
//        pubblicaAssistenza.put("", "P.A. Croce Verde Vobbia - C/O Grosso Zaverio");
//        pubblicaAssistenza.put("", "P.A. Volontari Del Soccorso Sestri Levante");
//
//
////        Savona
//        pubblicaAssistenza.put("", "P.A. Croce Azzurra Calizzano");
//        pubblicaAssistenza.put("", "P.A. Croce Bianca Alassio ");
//        pubblicaAssistenza.put("", "P.A. Croce Bianca Albenga");
//        pubblicaAssistenza.put("", "P.A. Croce Bianca Altare");
//        pubblicaAssistenza.put("", "P.A. Croce Bianca Andora");
//        pubblicaAssistenza.put("", "P.A. Croce Bianca Borghetto Santo Spirito");
//        pubblicaAssistenza.put("", "P.A. Croce Bianca Borgio Verezzi");
//        pubblicaAssistenza.put("", "P.A. Croce Bianca Cairo Montenotte");
//        pubblicaAssistenza.put("", "P.A. Croce Bianca Calice Ligure \"Dott. G. Cesio\"");
//        pubblicaAssistenza.put("", "P.A. Croce Bianca Carcare");
//        pubblicaAssistenza.put("", "P.A. Croce Bianca Dego");
//        pubblicaAssistenza.put("", "P.A. Croce Bianca Finale Ligure");
//        pubblicaAssistenza.put("", "P.A. Croce Bianca Giusvalla");
//        pubblicaAssistenza.put("", "P.A. Croce Bianca Laigueglia");
//        pubblicaAssistenza.put("", "P.A. Croce Bianca Mioglia");
//        pubblicaAssistenza.put("", "P.A. Croce Bianca Noli");
//        pubblicaAssistenza.put("", "P.A. Croce Bianca Pontinvrea");
//        pubblicaAssistenza.put("", "P.A. Croce Bianca Savona");
//        pubblicaAssistenza.put("", "P.A. Croce Bianca Spotorno");
//        pubblicaAssistenza.put("", "P.A. Croce D'oro Albissola Marina");
//        pubblicaAssistenza.put("", "P.A. Croce Rosa Cellese");
//        pubblicaAssistenza.put("", "P.A. Croce Verde Albisola Superiore");
//        pubblicaAssistenza.put("", "P.A. Croce Verde Bardineto");
//        pubblicaAssistenza.put("", "P.A. Croce Verde Finalborgo");
//        pubblicaAssistenza.put("", "P.A. Croce Verde Murialdo");
//
////        Imperia
//        pubblicaAssistenza.put("", "P.A. Croce Bianca Imperia Onlus");
//        pubblicaAssistenza.put("", "P.A. Croce Bianca Pornassio");
//        pubblicaAssistenza.put("", "P.A. Croce D'oro Cervo");
//
////        La Spezia
//        pubblicaAssistenza.put("", "P.A. \"Humanitas\" Romito Magra");
//        pubblicaAssistenza.put("", "P.A. Croce Azzurra Bonassola");
//        pubblicaAssistenza.put("", "P.A. Croce Azzurra Brugnato");
//        pubblicaAssistenza.put("", "P.A. Croce Bianca Beverino");
//        pubblicaAssistenza.put("", "P.A. Croce Bianca Le Grazie");
//        pubblicaAssistenza.put("", "P.A. Croce Bianca Monterosso Al Mare");
//        pubblicaAssistenza.put("", "P.A. Croce Bianca Portovenere");
//        pubblicaAssistenza.put("", "P.A. Croce Bianca Riomaggiore");
//        pubblicaAssistenza.put("", "P.A. Croce Bianca Santo Stefano Magra");
//        pubblicaAssistenza.put("", "P.A. Croce D'oro Deivese");
//        pubblicaAssistenza.put("", "P.A. Croce Gialla");
//        pubblicaAssistenza.put("", "P.A. Croce Rosso-Bianca Lerici");
//        pubblicaAssistenza.put("", "P.A. Croce Verde Arcola");
//        pubblicaAssistenza.put("", "P.A. Croce Verde Borghetto Vara");
//        pubblicaAssistenza.put("", "P.A. Croce Verde Carro");
//        pubblicaAssistenza.put("", "P.A. Croce Verde Levanto");
//        pubblicaAssistenza.put("", "P.A. Croce Verde Manarola");
//        pubblicaAssistenza.put("", "P.A. Croce Verde Pignone");
//        pubblicaAssistenza.put("", "P.A. Croce Verde Vernazza");
//        pubblicaAssistenza.put("", "P.A. Croce Verde Zignago");
//        pubblicaAssistenza.put("", "P.A. Framurese");
//        pubblicaAssistenza.put("", "P.A. La Misericordia Et Olmo");
//        pubblicaAssistenza.put("", "P.A. Luni Onlus");
//        pubblicaAssistenza.put("", "P.A. Pitelli");
//        pubblicaAssistenza.put("", "P.A. Vezzano Ligure");

        return pubblicaAssistenza;

    }

    public static class callWebApi extends AsyncTask<String, Integer, Long> {

        private Exception exception;

        @Override
        protected Long doInBackground(String... params) {
            URL url = createUrl(params[0]);

            // If the URL is null, then return early.
            if (url == null) {
                return 0l;
            }

            HttpURLConnection urlConnection = null;

            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.setRequestProperty("PSGE", SecurityToken.mSecurityToken);
//            urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // If the request was successful (response code 200),
                // then read the input stream and parse the response.
                if (urlConnection.getResponseCode() != 200) {
                    Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
                }
            } catch (IOException e) {
                Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                    return 1l;
                }

            }
            return 0l;
        }
    }


}
