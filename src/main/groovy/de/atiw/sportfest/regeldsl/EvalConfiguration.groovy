package de.atiw.sportfest.regeldsl;

import de.atiw.sportfest.backend.model.*;
import de.atiw.sportfest.regeldsl.model.*;

class EvalConfiguration {

  String name;
  Closure condition;

  Regel firstRule;

  EvalConfiguration name(String name){
    this.name = name;
    return this;
  }

  Integer eval(Ergebnis erg){

    condition.delegate = erg;
    condition.resolveStrategy = Closure.DELEGATE_FIRST

    if(!condition()){
      return 0;
    }

    return firstRule.eval(erg);

  }

  String toString(){
    "{name $name, cond ${condition.toString()}, firstRule $firstRule}"
  }

}
