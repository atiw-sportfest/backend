package de.atiw.sportfest.regeldsl;

import de.atiw.sportfest.backend.model.Ergebnis;

import java.util.stream.Collectors;

class RulesConfiguration {

  final List<EvalConfiguration> direkt = [];
  final List<EvalConfiguration> sonder = [];

  String toString(){
    return "direkt: ${direkt}\nsonder: ${sonder}"
  }

  def eval(tgt, arg){

    for(def eval in tgt){
      arg = eval.eval(arg);
    }

    arg;

  }

  def versus(tgt, arg){

    arg = eval(tgt, arg);

    for(def versus in tgt){
      arg = versus.versus(arg);
    }

    arg

  }

  List<Ergebnis> direktEinzelMulti(List<Ergebnis> ergebnisse){
    ergebnisse.stream().map({ erg -> eval(direkt, erg)});
  }

  Ergebnis direktEinzel(Ergebnis ergebnis){
    eval(direkt, ergebnis);
  }

  List<List<Ergebnis>> direktVersusMulti(List<List<Ergebnis>> ergebnisse){
    ergebnisse.stream().map({ ergs -> versus(direkt, ergs) }).collect(Collectors.toList());
  }

  List<Ergebnis> direktVersus(List<Ergebnis> ergebnisse){
    versus(direkt, ergebnisse)
  }

  List<Ergebnis> sonderEinzel(List<Ergebnis> ergebnisse){
    eval(sonder, ergebnisse)
  }

  List<Ergebnis> sonderVersus(List<Ergebnis> ergebnisse){
    versus(sonder, ergebnisse)
  }

  def sonderVersusMulti(List<List<Ergebnis>> ergebnisse){

    for(def eval in sonder){

      if(eval.flat){
        eval.eval(ergebnisse.flatten());

      } else {
        for(def ergs in ergebnisse)
          eval.eval(ergs);

      }

    }
  }

}
