package com.sed.federico.prontosoccorsoligura;

import java.util.HashMap;

/**
 * Created by federico.marchesi on 03/05/2017.
 */

public class CrossImage {

    private HashMap<Color, Integer> mCrossImage;

    private Color mCrossColor;


    public CrossImage(Color crossColor) {
        mCrossImage = new HashMap<>();
        mCrossImage.put(Color.BLUE, R.drawable.cross_blue);
        mCrossImage.put(Color.CELESTE, R.drawable.cross_celeste);
        mCrossImage.put(Color.ELI, R.drawable.cross_eli);
        mCrossImage.put(Color.GOLD, R.drawable.cross_gold);
        mCrossImage.put(Color.GREEN, R.drawable.cross_green);
        mCrossImage.put(Color.LIGHT_BLUE, R.drawable.cross_light_blue);
        mCrossImage.put(Color.RED, R.drawable.cross_red);
        mCrossImage.put(Color.ROSE, R.drawable.cross_rose);
        mCrossImage.put(Color.WHITE, R.drawable.cross_white_no_circle);
        mCrossImage.put(Color.YELLOW, R.drawable.cross_yellow);
        mCrossImage.put(Color.CROSS, R.drawable.cross);

        mCrossColor = crossColor;
    }

    public int getCrossImage() {
        return mCrossImage.get(mCrossColor);
    }

    public Color getmCrossColor() {
        return mCrossColor;
    }
}
