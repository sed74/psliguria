package com.sed.federico.prontosoccorsoligura;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.sed.federico.prontosoccorsoligura.PubblicheAssistenze.PostazioniActivity;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by federico.marchesi on 13/04/2017.
 */

public class AsyncString extends AsyncTask<String, String, String> {
    ProgressDialog mProgressDialog;
    AppCompatActivity mCallingActivity;
    Context mContext;
    JSONException e;


    public AsyncString(Context context) {
        mContext = context;
    }

    protected void onPreExecute() {
        super.onPreExecute();

        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage("Please wait");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    protected String doInBackground(String... params) {


        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("PSGE", SecurityToken.mSecurityToken);
            connection.connect();


            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";

            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
                Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

            }

            return buffer.toString();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        Intent i = new Intent(mContext, PostazioniActivity.class);
        i.putExtra("centrali", result);
        mContext.startActivity(i);
    }
}


