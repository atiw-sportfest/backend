package de.atiw.sportfest.regeldsl;

import de.atiw.sportfest.backend.model.Disziplin;

class DisziplinDsl extends Disziplin implements Createable<Disziplin> {

    RulesConfiguration rules; // name clash with Disziplin.regeln

    DisziplinDsl(){
        super();
    }

    void regeln(Closure script){
        rules = new RulesDsl().create(script);
    }

    // No other dsl methods required here - Disziplin has fluent setters

    Disziplin getDelegateInstance(){ return this; }

    String toString(){
        return "${super.toString()}\nregeln:\n${rules}"
    }

}
