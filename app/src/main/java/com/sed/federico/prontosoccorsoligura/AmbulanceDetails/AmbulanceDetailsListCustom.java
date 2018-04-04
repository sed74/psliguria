package com.sed.federico.prontosoccorsoligura.AmbulanceDetails;

import java.util.ArrayList;

/**
 * Created by federico.marchesi on 31/03/2017.
 */

public class AmbulanceDetailsListCustom extends ArrayList<AmbulanceDetail> {


    public AmbulanceDetailsListCustom() {
        super();

    }

    @Override
    public boolean add(AmbulanceDetail ambulanceDetail) {


        return super.add(ambulanceDetail);
    }

    @Override
    public void add(int index, AmbulanceDetail ambulanceDetail) {
        super.add(index, ambulanceDetail);
    }


}
