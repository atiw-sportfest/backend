package de.atiw.sportfest.regeldsl;

import de.atiw.sportfest.backend.model.Disziplin;

class SportfestDsl implements Createable<SportfestDsl> {

    final List<Disziplin> disziplinen = [];

    Disziplin disziplin(String name, Closure script){
      def d = new DisziplinDsl().bezeichnung(name).create(script);
      disziplinen << d
      d
    }

    SportfestDsl getDelegateInstance(){ return this; }

    String toString(){
        return "Disziplinen:\n${disziplinen.toString()}"
    }

}
