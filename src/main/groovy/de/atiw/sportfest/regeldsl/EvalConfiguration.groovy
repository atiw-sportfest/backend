package de.atiw.sportfest.regeldsl;

import de.atiw.sportfest.backend.model.Ergebnis;

import java.util.stream.Collectors;

class EvalConfiguration {

  String name;

  Closure condition = { true };
  Closure sortCl;
  Closure selectCl;

  Integer limit;
  Boolean flat;

  RuleConfiguration firstRule;

  List<Integer> points;

  EvalConfiguration name(String name){
    this.name = name;
    return this;
  }

  def prepareErgebnisse(List<Ergebnis> ergs){

    if(limit == null || limit == 0)
      limit = ergs.size();

    if(sortCl)
      ergs = sortCl(ergs);

    return ergs;

  }

  List<Ergebnis> eval(List<Ergebnis> ergs){

    ergs = prepareErgebnisse(ergs);

    for(def i =0; i<limit; i++){

      def erg = ergs.get(i);

      if(selectCl == null || selectCl(erg))
        ergs.set(i, eval(erg));

    }

    ergs;

  }

  Ergebnis eval(Ergebnis erg){

    condition.delegate = erg;
    condition.resolveStrategy = Closure.DELEGATE_FIRST;

    if(!condition()){
      return erg;
    }

    if(firstRule != null)
      erg = erg.punkte((erg.getPunkte() ?: 0) + firstRule.eval(erg));

    erg;

  }

  List<Ergebnis> versus(List<Ergebnis> ergs){

    if(!condition())
      return ergs;

    ergs = prepareErgebnisse(ergs);

    for(def i = 0; i < ergs.size() && i < points.size(); i++){

      def erg = ergs.get(i);

      ergs.set(i, erg.punkte((erg.getPunkte() ?: 0) + points.get(i)));

    }

    return ergs;
  }

  String toString(){
    "{name $name, cond ${condition.toString()}, firstRule $firstRule}"
  }

}
