package de.atiw.sportfest.regeldsl;

class RulesDsl extends RulesConfiguration implements Createable<RulesDsl> {

  void direkt(Closure script){
    direkt(null, script);
  }

  void direkt(String name, Closure script){
    eval(direkt, name, script);
  }

  void sonder(Closure script){
    sonder(null, script);
  }

  void sonder(String name, Closure script){
    eval(sonder, name, script);
  }

  void eval(List<EvalConfiguration> target, String name, Closure script){
    target << new EvalDsl().name(name).create(script);
  }

  RulesDsl getDelegateInstance(){ return this; }

}
