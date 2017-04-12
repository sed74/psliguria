package com.sed.federico.prontosoccorsoligura;

import android.content.ContentValues;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by federico.marchesi on 11/04/2017.
 */

public class MissionFragmentAdapter extends FragmentPagerAdapter {

    Context mContext;

    public MissionFragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return MissionFragment.newInstance("Genova");
            case 1:
//                return new LaSpeziaFragment();
            case 2:
//                return new SavonaFragment();
            case 3:
//                return new ImperiaFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 0;
    }
}
