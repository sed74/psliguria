package com.dani.sed.liguriasoccorso;

public class Globals {
    private static Globals instance;

    private Globals(){}

    public static synchronized Globals getInstance(){
        if(instance == null)
            instance = new Globals();
        return instance;
    }

    public static Globals getData() {
        return instance;
    }

    public static void setData(Globals postazione) {
        Globals.instance = postazione;
    }
}
