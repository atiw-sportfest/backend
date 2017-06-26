package de.atiw.sportfest.backend.resource.jaxb;

import java.util.ArrayList;

import de.atiw.sportfest.backend.rules.Regel;

public class Regeln {
    private Regel[] regeln;

    public Regeln(Regel regel){

        ArrayList<Regel> regeln = new ArrayList<>();

        do {
            regeln.add(regel);
            regel = regel.getNext();
        } while(regel != null && regel.getNext() != null);

        this.regeln = regeln.toArray(this.regeln);
     }


    public Regel[] getRegeln() {
        return regeln;
    }
    public void setRegeln(Regel[] regeln) {
        this.regeln = regeln;
    }


}
