package de.atiw.sportfest.regeldsl;

import de.atiw.sportfest.backend.model.Ergebnis;

class RuleConfiguration {

  Integer points;
  RuleConfiguration next;
  Closure condition;

  RuleConfiguration points(int points){
    this.points = points;
    this;
  }

  RuleConfiguration next(RuleConfiguration next){
    this.next = next;
    this;
  }

  RuleConfiguration condition(Closure script){
    this.condition = script
    this;
  }

  Integer eval(RuleDsl ruleDsl){

    condition.delegate = ruleDsl;
    condition.resolveStrategy = Closure.DELEGATE_FIRST;

    if(condition()){
      return points;
    }

    if(next != null)
      return next.eval(ruleDsl);

    return 0;

  }

  Integer eval(Ergebnis erg){
    eval(new RuleDsl(erg));
  }

  String toString(){
    "{cond: ${condition.toString()}, $points. next:\n$next}"
  }

}
