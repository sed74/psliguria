package com.sed.federico.prontosoccorsoligura;

import android.content.Context;
import android.os.AsyncTask;

import com.sed.federico.prontosoccorsoligura.Mission.MissionListCustom;

/**
 * Created by federico.marchesi on 12/04/2017.
 */

public class AsyncDownloader extends AsyncTask<String, Void, MissionListCustom> {
    onDownloadFinishedListener mOnDownloadFinishedListener;
    Context mContext;

    public AsyncDownloader(Context context, onDownloadFinishedListener interf) {
        mContext = context;
        mOnDownloadFinishedListener = interf;
    }

    @Override
    protected MissionListCustom doInBackground(String... params) {
        mOnDownloadFinishedListener.onDownloadStarted();
        if (params.length == 0) {
            return null;
        }

        MissionListCustom missions = new MissionListCustom();
        for (String url : params) {
            MissionListCustom temp = QueryUtils.fetchMissionData(mContext, url);
            missions.addAll(temp);
        }

        return missions;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected void onPostExecute(MissionListCustom missions) {
        super.onPostExecute(missions);
        String toastText;
        if (missions.isIsDragoWorking()) {
            toastText = missions.getDragoPosition() + " (" + missions.getDragoCentrale() + ")";

        } else {
            toastText = "";
        }
        mOnDownloadFinishedListener.onDownloadFinished(toastText);
    }

    public void setOnDownloadClickListener(onDownloadFinishedListener listener) {
        mOnDownloadFinishedListener = listener;
    }

    public interface onDownloadFinishedListener {
        void onDownloadFinished(String location);

        void onDownloadStarted();
    }
}
