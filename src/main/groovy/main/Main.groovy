package main;

import de.atiw.sportfest.regeldsl.*;
import de.atiw.sportfest.backend.model.*;

println "Hello, World!"

def sportfest = new SportfestDsl().create {

  disziplin "hallo", {

    beschreibung "welt"
      versus true
      team true

      regeln {

        def isM = { schueler.geschlecht == "m" }
        def isW = { schueler.geschlecht == "w" }

        def weite = { min -> {-> max(w1, w2, w3) >= min } }

        direkt "herren", {

          cond isM;

          regel 6, weite(4.8)
          regel 4, weite(4.0)
          regel 2
        }

        direkt "damen", {

          cond isW;

          regel 6, weite(3.8)
          regel 4, weite(3.0)
          regel 2
        }

        sonder "herren", {

          cond isM;

          sort(); // Desc
          // sort false // Asc

          limit { punkte >= 6 }

          top 6;

          for(def i=0; i < 6; i++)
            regel 4

        }

        sonder "damen", {

          cond isW;

          sort();

          limit { punkte >= 6 }

          top 2;

          for(def i=0; i < 2; i++)
            regel 4
        }
      }
  }

}

println sportfest

Ergebnis erg = new Ergebnis().schueler(new Schueler().geschlecht("m")).leistungen([
    new Leistung().wert("4.6").variable(new Variable().bezeichnung("w1")),
    new Leistung().wert("2.9").variable(new Variable().bezeichnung("w2")),
    new Leistung().wert("4.2").variable(new Variable().bezeichnung("w3"))
]);

println sportfest.disziplinen[0].regelConfig.direktEinzel(erg);
