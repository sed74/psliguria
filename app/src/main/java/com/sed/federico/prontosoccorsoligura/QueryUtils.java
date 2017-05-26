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

import com.sed.federico.prontosoccorsoligura.AmbulanceDetails.AmbulanceDetail;
import com.sed.federico.prontosoccorsoligura.Mission.Mission;
import com.sed.federico.prontosoccorsoligura.Mission.MissionListCustom;
import com.sed.federico.prontosoccorsoligura.AmbulanceDetails.AmbulanceDetailsListCustom;
import com.sed.federico.prontosoccorsoligura.PubblicheAssistenze.Postazione;

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

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    /**
     * Firebase Constants
     */
    public static final String FBASE_AMBULANCE_NO = "ambulance_no";
    public static final int TABLET_MIN_WIDTH = 720;
    public static final int PHONE_ORIZONTAL_WIDTH = 600;

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

    public static final String POSTAZIONE_ID = "id";
    public static final String POSTAZIONE_CODE = "code";
    public static final String POSTAZIONE_NAME = "name";
    public static final String POSTAZIONE_ADDRESS = "address";
    public static final String POSTAZIONE_TOT_AMBULANCE = "no_mezzi_censiti";
    public static final String POSTAZIONE_AVG_MISSION = "average_mission_per_day";
    public static final String POSTAZIONE_AVG_WHITE = "average_white_per_day";
    public static final String POSTAZIONE_AVG_GREEN = "average_green_per_day";
    public static final String POSTAZIONE_AVG_YELLOW = "average_yellow_per_day";
    public static final String POSTAZIONE_AVG_RED = "average_red_per_day";
    public static final String POSTAZIONE_TOT_WHITE = "tot_white";
    public static final String POSTAZIONE_TOT_GREEN = "tot_green";
    public static final String POSTAZIONE_TOT_YELLOW = "tot_yellow";
    public static final String POSTAZIONE_TOT_RED = "tot_red";
    public static final String POSTAZIONE_TOT_DAYS = "tot_days";

    public static final String MEZZO_POSTAZIONE = "postazione";
    public static final String MEZZO_AMBULANZA = "code_ambulance";
    public static final String MEZZO_TOT_MISSION = "tot_mission";
    public static final String MEZZO_TOT_WHITE = "tot_white";
    public static final String MEZZO_TOT_GREEN = "tot_green";
    public static final String MEZZO_TOT_YELLOW = "tot_yellow";
    public static final String MEZZO_TOT_RED = "tot_red";
    public static final String MEZZO_FROM_DATE = "from_date";
    public static final String MEZZO_TO_DATE = "to_date";
    public static final String MEZZO_TOT_DAYS = "tot_days";
    public static final String MEZZO_REAL_DAYS = "tot_real_days";
    /**
     * /**
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
    public static PostazioneListCustom fetchCentrali(String requestUrl) {
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
        PostazioneListCustom centrali = extractPostazioneFromJson(jsonResponse);

        // Return the list of {@link Earthquake}s
        return centrali;
    }

    public static AmbulanceDetailsListCustom fetchMezzi(String requestUrl) {
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
        AmbulanceDetailsListCustom centrali = extractMezziFromJson(jsonResponse);

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
    private static PostazioneListCustom extractPostazioneFromJson(String postazioneJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(postazioneJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        PostazioneListCustom postazioni = new PostazioneListCustom();


        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            JSONArray centraliArray = new JSONArray(postazioneJSON);

            // For each earthquake in the earthquakeArray, create an {@link Earthquake} object
            for (int i = 0; i < centraliArray.length(); i++) {
                JSONObject hospitalJSONObj = centraliArray.getJSONObject(i);

                int id = hospitalJSONObj.getInt(POSTAZIONE_ID);
                String code = hospitalJSONObj.getString(POSTAZIONE_CODE);
                String descr = hospitalJSONObj.getString(POSTAZIONE_NAME);
                String address = hospitalJSONObj.getString(POSTAZIONE_ADDRESS);
                int totAmbulance = hospitalJSONObj.getInt(POSTAZIONE_TOT_AMBULANCE);
                double totMission = hospitalJSONObj.getDouble(POSTAZIONE_AVG_MISSION);
                double avgWhite = hospitalJSONObj.getDouble(POSTAZIONE_AVG_WHITE);
                double avgGreen = hospitalJSONObj.getDouble(POSTAZIONE_AVG_GREEN);
                double avgYellow = hospitalJSONObj.getDouble(POSTAZIONE_AVG_YELLOW);
                double avgRed = hospitalJSONObj.getDouble(POSTAZIONE_AVG_RED);

                int totWhite = hospitalJSONObj.getInt(POSTAZIONE_TOT_WHITE);
                int totGreen = hospitalJSONObj.getInt(POSTAZIONE_TOT_GREEN);
                int totYellow = hospitalJSONObj.getInt(POSTAZIONE_TOT_YELLOW);
                int totRed = hospitalJSONObj.getInt(POSTAZIONE_TOT_RED);
                int totDays = hospitalJSONObj.getInt(POSTAZIONE_TOT_DAYS);

//                Postazione c = new Postazione(id, code, descr, city, centrale);
                Postazione c = new Postazione(id, code, descr, address, totAmbulance, totMission,
                        avgWhite, avgGreen, avgYellow, avgRed, totWhite, totGreen, totYellow,
                        totRed, totDays);

                // Add the new {@link Earthquake} to the list of earthquakes.
                postazioni.add(c);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return postazioni;
    }

    private static AmbulanceDetailsListCustom extractMezziFromJson(String postazioneJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(postazioneJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        AmbulanceDetailsListCustom mezzi = new AmbulanceDetailsListCustom();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            JSONArray mezziArray = new JSONArray(postazioneJSON);

            // For each earthquake in the earthquakeArray, create an {@link Earthquake} object
            for (int i = 0; i < mezziArray.length(); i++) {
                JSONObject mezziJSONObj = mezziArray.getJSONObject(i);

                String codePostazione = mezziJSONObj.getString(MEZZO_POSTAZIONE);
                String ambulanceCode = mezziJSONObj.getString(MEZZO_AMBULANZA);
                int totMissions = mezziJSONObj.getInt(MEZZO_TOT_MISSION);
                int totWhite = mezziJSONObj.getInt(MEZZO_TOT_WHITE);
                int totGreen = mezziJSONObj.getInt(MEZZO_TOT_GREEN);
                int totYellow = mezziJSONObj.getInt(MEZZO_TOT_YELLOW);
                int totRed = mezziJSONObj.getInt(MEZZO_TOT_RED);
                String fromDate = mezziJSONObj.getString(MEZZO_FROM_DATE);
                String toDAte = mezziJSONObj.getString(MEZZO_TO_DATE);
                int totDays = mezziJSONObj.getInt(MEZZO_TOT_DAYS);
                int realDAys = mezziJSONObj.getInt(MEZZO_REAL_DAYS);

//                Postazione c = new Postazione(id, code, descr, city, centrale);
                AmbulanceDetail c = new AmbulanceDetail(codePostazione, ambulanceCode, totMissions,
                        totWhite, totGreen, totYellow, totRed, totDays, realDAys);

                // Add the new {@link Earthquake} to the list of earthquakes.
                mezzi.add(c);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return mezzi;
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

    public static String createUrlWithVersion(String url, String version) {
        if (!url.endsWith("/"))
            url += ("/");
        url += version + "/";

        return url;

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
