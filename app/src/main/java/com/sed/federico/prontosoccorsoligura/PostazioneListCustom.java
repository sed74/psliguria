package com.sed.federico.prontosoccorsoligura;

import com.sed.federico.prontosoccorsoligura.PubblicheAssistenze.Postazione;

import java.util.ArrayList;

/**
 * Created by federico.marchesi on 31/03/2017.
 */

public class PostazioneListCustom extends ArrayList<Postazione> {


    public PostazioneListCustom() {
        super();

    }

    @Override
    public boolean add(Postazione postazione) {


        return super.add(postazione);
    }

    @Override
    public void add(int index, Postazione postazione) {
        super.add(index, postazione);
    }


}
