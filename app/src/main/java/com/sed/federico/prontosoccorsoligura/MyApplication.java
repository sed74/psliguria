package com.sed.federico.prontosoccorsoligura;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by federico.marchesi on 05/05/2017.
 */

public class MyApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}