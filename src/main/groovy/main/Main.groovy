package main;

import de.atiw.sportfest.regeldsl.*;
import de.atiw.sportfest.backend.model.*;

import java.util.stream.Collectors;

println "Hello, World!"

def sportfest = new SportfestDsl().create {


  /*

  // SportfestDsl

  disziplin {

    // DisziplinDsl

    regeln {

      // RulesDsl

      direkt {

        // EvalDsl

        regel 100, { // RuleDsl }

      }

      sonder {

        // Eval Dsl

        regel 100, { // RuleDsl }

      }

    }
  }
  */

  def isM = { schueler.geschlecht == "m" }
  def isW = { schueler.geschlecht == "w" }

  disziplin "2000m-Lauf", {

    team true;
    versus true;

    regeln {

      direkt "herren", {

        cond isM;

        sort false;

        regel 10, { zeit <=  9 };
        regel  7, { zeit <= 11 };
        regel  4;
      }

      direkt "damen", {

        cond isW;

        sort false;

        regel 10, { zeit <= 11 };
        regel  7, { zeit <= 13 };
        regel  4;

      }

      sonder "herren", {

        cond isM;

        flat();

        sort false;
        top 6;

        regel 4, { punkte >= 10 };

      }

      sonder "damen", {

        cond isW;

        flat();

        sort false;
        top 2;

        regel 4, { punkte >= 10 };

      }
    }
  }

  disziplin "Staffellauf", {

    team true;
    versus true; // Regeln erhalten Ergebnisse (List<Ergebnis>)

    regeln {

      direkt {

        sort "zeit";

        top 4;

        punkte 24, 21, 18, 15;

      }

      sonder {

        flat(); // Ignore versus;

        sort "zeit";

        top 3;

        regel 4

      }
    }
  }


  def werfen = {
    regel 6, { treffer >= 10 };
    regel 4, { treffer >=  6 };
    regel 3, { treffer >=  4 };
    regel 2, { treffer >=  1 };
  };

  disziplin "Baseball", {

    team false;
    versus false;

    regeln {
      direkt werfen;
    }
  }

  disziplin "Freiwürfe", {

    team false;
    versus false;

    regeln {
      direkt werfen;
    }

  }

  disziplin "Beach-Volleayball", {

    team true;
    versus true;

    regeln {

      direkt {
        sort "punkte";
        punkte 15, 8;
      }

    }

  }

  /* ... */

  disziplin "Weitsprung", {

    team false;
    versus false;

    regeln {

      def weite = { min -> {-> [w1, w2, w3].max() >= min } }

      direkt "herren", {

        cond isM;

        regel 6, weite(4.8);
        regel 4, weite(4.0);
        regel 2;
      }

      direkt "damen", {

        cond isW;

        regel 6, weite(3.8);
        regel 4, weite(3.0);
        regel 2;
      }

      sonder "herren", {

        cond isM;

        sort();

        top 6;

        regel 4, { punkte >= 6 }

      }

      sonder "damen", {

        cond isW;

        sort();

        top 2;

        regel 4, { punkte >= 6 }
      }
    }
  }


}

println sportfest

def meterT = new Typ().datentyp(Typ.DatentypEnum.DOUBLE);
def minutenT = new Typ().datentyp(Typ.DatentypEnum.DOUBLE);

def meterV1 = new Variable().bezeichnung("w1").typ(meterT);
def meterV2 = new Variable().bezeichnung("w2").typ(meterT);
def meterV3 = new Variable().bezeichnung("w3").typ(meterT);

def zeitV = new Variable().bezeichnung("zeit").typ(minutenT);

def makeErg1 = { w1, w2, w3, g ->
  new Ergebnis().schueler(new Schueler().geschlecht(g)).leistungen([
      new Leistung().wert("${w1/100}").variable(meterV1),
      new Leistung().wert("${w2/100}").variable(meterV2),
      new Leistung().wert("${w3/100}").variable(meterV3)
  ]);
}

def ergs1 = [
  makeErg1(175,26,94,"w"),
  makeErg1(17,149,263,"m"),
  makeErg1(56,185,370,"w"),
  makeErg1(135,433,129,"w"),
  makeErg1(109,234,193,"w"),
  makeErg1(232,378,365,"w"),
  makeErg1(268,231,76,"m"),
  makeErg1(286,222,196,"m"),
  makeErg1(215,283,171,"w"),
  makeErg1(38,251,110,"w"),
  makeErg1(249,458,481,"m"),
  makeErg1(318,41,428,"w"),
  makeErg1(481,33,420,"m"),
  makeErg1(304,117,266,"w"),
  makeErg1(0,201,472,"w"),
  makeErg1(404,10,456,"m"),
  makeErg1(193,221,47,"m"),
  makeErg1(263,288,217,"m"),
  makeErg1(213,279,310,"m"),
  makeErg1(477,22,58,"m"),
  makeErg1(250,85,186,"w"),
  makeErg1(83,219,53,"w"),
  makeErg1(160,487,389,"m"),
  makeErg1(317,236,178,"m"),
  makeErg1(72,9,277,"w"),
  makeErg1(112,474,354,"w"),
  makeErg1(13,84,123,"m"),
  makeErg1(396,292,463,"m"),
  makeErg1(377,81,230,"w"),
  makeErg1(72,428,89,"m"),
  makeErg1(171,33,174,"m"),
  makeErg1(168,67,345,"m"),
  makeErg1(475,124,292,"w"),
  ];

def regelConfig1 = sportfest.disziplinen[sportfest.disziplinen.size()-1].rules;

ergs1 = ergs1.stream().map({ erg ->
    erg = regelConfig1.direktEinzel(erg);
}).collect(Collectors.toList())


println "Results direkt, einzel"
for(def erg in ergs1){
  println "${erg.getLeistungen().stream().map({it.getWert()}).collect(Collectors.joining("/"))} ${erg.getSchueler().getGeschlecht()}: ${erg.getPunkte()}";
}

ergs1 = regelConfig1.sonderEinzel(ergs1);

println "Results sonder"
for(def erg in ergs1){
  println "${erg.getLeistungen().stream().map({it.getWert()}).collect(Collectors.joining("/"))} ${erg.getSchueler().getGeschlecht()}: ${erg.getPunkte()}";
}

def makeErg2 = { d1, d2, d3, d4 ->
  [
    new Ergebnis().leistungen([ new Leistung().wert("${d1/1000}").variable(zeitV)]),
    new Ergebnis().leistungen([ new Leistung().wert("${d2/1000}").variable(zeitV)]),
    new Ergebnis().leistungen([ new Leistung().wert("${d3/1000}").variable(zeitV)]),
    new Ergebnis().leistungen([ new Leistung().wert("${d4/1000}").variable(zeitV)])
  ]
}

def ergs2 = [
makeErg2(5399,19384,13812,34677),
makeErg2(17294,2224,533,40921),
makeErg2(8564,26425,25036,27469),
makeErg2(62871,16105,1582,27756),
makeErg2(27009,2695,9412,48041),
makeErg2(30157,49140,6036,39780),
makeErg2(51696,50049,50094,21471),
makeErg2(18853,24739,11949,24229),
makeErg2(31170,47135,44826,26240),
makeErg2(53877,23662,28940,18928),
makeErg2(37247,13677,26075,49575),
makeErg2(38605,58659,35784,41909),
makeErg2(11996,52489,14044,52178)
]

def regelConfig2 = sportfest.disziplinen[1].rules;

ergs2 = regelConfig2.direktVersusMulti(ergs2);

println "Results direkt, versus"

for(def ergs_ in ergs2){
  for(def erg_ in ergs_)
  println "${erg_.getLeistungen().stream().map({it.getWert()}).collect(Collectors.joining("/"))}: ${erg_.getPunkte()}";
  println();
}

regelConfig2.sonderVersusMulti(ergs2);

println "Results sonder, versus"
for(def ergs_ in ergs2){
  for(def erg_ in ergs_)
  println "${erg_.getLeistungen().stream().map({it.getWert()}).collect(Collectors.joining("/"))}: ${erg_.getPunkte()}";
  println();
}
