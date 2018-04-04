package com.sed.federico.prontosoccorsoligura;

/**
 * Created by federico.marchesi on 13/04/2017.
 */

public class CharlieCode {
    private String mCode;
    private String mDescr;


    CharlieCode(String code, String desc) {
        mCode = code;
        mDescr = desc;
    }

    public void setmode(String code) {
        this.mCode = code;
    }

    public void setmDescr(String descr) {
        this.mDescr = descr;
    }

    public String getCode() {
        return mCode;
    }

    public String getDescr() {
        return mDescr;
    }
}
