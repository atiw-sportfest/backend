package de.atiw.sportfest.backend.model.datentypen;

public class DoubleTyp implements Datentyp<Double> {

    public Double load(String s){
        return Double.parseDouble(s);
    }

}

