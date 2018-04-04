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
package com.sed.federico.prontosoccorsoligura.Mission;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.sed.federico.prontosoccorsoligura.QueryUtils;

/**
 * Loads a list of hospital by using an AsyncTask to perform the
 * network request to the given URL.
 */
public class MissionLoader extends AsyncTaskLoader<MissionListCustom> {

    private Context mContext;

    /**
     * Query URL
     */
    private String mUrl;
    private String[] mUrls;

    /**
     * Constructs a new {@link MissionLoader}.
     *
     * @param context of the activity
     * @param url     to load data from
     */
    public MissionLoader(Context context, String url) {
        super(context);
        mUrl = url;
        mContext = context;
    }

    public MissionLoader(Context context, String[] url) {
        super(context);
        mUrls = url;
        mContext = context;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public MissionListCustom loadInBackground() {
        if (mUrl == null && mUrls.length == 0) {
            return null;
        }
        if (mUrl != null) {
            // Perform the network request, parse the response, and extract a list of earthquakes.
            return QueryUtils.fetchMissionData(mContext, mUrl);
        } else {
            MissionListCustom missions = new MissionListCustom();
            for (String url :
                    mUrls) {
                MissionListCustom temp = QueryUtils.fetchMissionData(mContext, url);
                missions.addAll(temp);
            }
            return missions;
        }
    }
}
