package com.dani.sed.liguriasoccorso.PubblicheAssistenze;

import com.dani.sed.liguriasoccorso.CrossImage;

public class PostazioneInfo {
    private String name;
    private CrossImage crossImage;
    private String code;

    public PostazioneInfo(String name, CrossImage crossImage, String code) {
        this.name = name;
        this.crossImage = crossImage;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CrossImage getCrossImage() {
        return crossImage;
    }

    public void setCrossImage(CrossImage crossImage) {
        this.crossImage = crossImage;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
