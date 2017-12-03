package de.atiw.sportfest.regeldsl;

import de.atiw.sportfest.backend.model.*;
import de.atiw.sportfest.regeldsl.model.*;

class RegelConfiguration {

    final List<EvalConfiguration> direkt = [];
    final List<EvalConfiguration> sonder = [];

    String toString(){
        return "direkt: ${direkt}\nsonder: ${sonder}"
    }

    Integer direktEinzel(Ergebnis ergebnis){
        direkt.stream().mapToInt({eval -> eval.eval(ergebnis)}).sum()
    }
    Integer direktVersus(List<Ergebnis> ergebnisse){}

    Integer sonderEinzel(Ergebnis ergebnis){}
    Integer sonderVersus(List<Ergebnis> ergebnisse){}

}
