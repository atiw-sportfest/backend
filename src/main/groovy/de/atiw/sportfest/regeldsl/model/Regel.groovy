package de.atiw.sportfest.regeldsl.model;

import de.atiw.sportfest.backend.model.*;

class Regel {

  Integer points;
  Regel next;
  Closure condition;

  Regel points(int points){
    this.points = points;
    this;
  }

  Regel next(Regel next){
    this.next = next;
    this;
  }

  Regel condition(Closure script){
    this.condition = script
    this;
  }

  Integer eval(ErgebnisDelegate ergDelegate){

    condition.delegate = ergDelegate;
    condition.resolveStrategy = Closure.DELEGATE_FIRST;

    println "Regel.eval: ${condition.toString()}"

    if(condition()){
      return points;
    }
    else if(next != null) {
      return next.eval(ergDelegate);
      }
    else {
      return 0;
    }

  }

  Integer eval(Ergebnis erg){
    eval(new ErgebnisDelegate(erg));
  }

  String toString(){
    "{cond: ${condition.toString()}, $points.\nnext: $next}"
  }

}
