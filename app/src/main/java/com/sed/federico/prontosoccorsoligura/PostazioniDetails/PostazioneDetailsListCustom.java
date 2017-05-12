package com.sed.federico.prontosoccorsoligura.PostazioniDetails;

import com.sed.federico.prontosoccorsoligura.PubblicheAssistenze.Postazione;

import java.util.ArrayList;

/**
 * Created by federico.marchesi on 31/03/2017.
 */

public class PostazioneDetailsListCustom extends ArrayList<PostazioneDetail> {


    public PostazioneDetailsListCustom() {
        super();

    }

    @Override
    public boolean add(PostazioneDetail postazioneDetail) {


        return super.add(postazioneDetail);
    }

    @Override
    public void add(int index, PostazioneDetail postazioneDetail) {
        super.add(index, postazioneDetail);
    }


}
