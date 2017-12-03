package de.atiw.sportfest.backend.model.datentypen;

public class IntegerTyp implements Datentyp<Integer> {

    public Integer load(String s){
        return Integer.parseInt(s);
    }

}

