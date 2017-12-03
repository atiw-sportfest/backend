package de.atiw.sportfest.backend.model.datentypen;

public class FloatTyp implements Datentyp<Float> {

    public Float load(String s){
        return Float.parseFloat(s);
    }
}

