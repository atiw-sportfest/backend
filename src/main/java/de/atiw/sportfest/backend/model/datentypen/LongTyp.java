package de.atiw.sportfest.backend.model.datentypen;

public class LongTyp implements Datentyp<Long> {

    public Long load(String s){
        return Long.parseLong(s);
    }

}

