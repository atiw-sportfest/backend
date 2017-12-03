package de.atiw.sportfest.regeldsl;

import de.atiw.sportfest.backend.model.*;
import de.atiw.sportfest.regeldsl.model.*;
import de.atiw.sportfest.regeldsl.*;

class DisziplinDsl extends Disziplin implements Createable<Disziplin> {

    RegelConfiguration regelConfig;

    DisziplinDsl(){
        super();
    }

    void regeln(Closure script){
        regelConfig = new RegelDsl().create(script);
    }

    Disziplin getDelegateInstance(){ return this; }

    String toString(){
        return "${super.toString()}\nregeln:\n${regelConfig}"
    }

}
