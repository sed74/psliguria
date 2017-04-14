package com.sed.federico.prontosoccorsoligura;

import java.util.ArrayList;

/**
 * Created by federico.marchesi on 31/03/2017.
 */

public class CentraleListCustom extends ArrayList<Centrale> {


    public CentraleListCustom() {
        super();

    }

    @Override
    public boolean add(Centrale centrale) {


        return super.add(centrale);
    }

    @Override
    public void add(int index, Centrale centrale) {
        super.add(index, centrale);
    }


}
