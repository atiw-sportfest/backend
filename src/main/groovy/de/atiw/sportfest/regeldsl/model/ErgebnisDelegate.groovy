package de.atiw.sportfest.regeldsl.model;

import de.atiw.sportfest.backend.model.*;

class ErgebnisDelegate extends Ergebnis {

  Ergebnis ergebnis;

  ErgebnisDelegate(Ergebnis erg){
    this.ergebnis = erg;
  }

  def getProperty(String name){

    def lstg = ergebnis.getLeistungen().find({l -> l.getVariable().getBezeichnung() == name });

    lstg != null ? lstg.getWert() : metaClass.getProperty(this, name);
  }

  def max(String ...vars){
    println "${vars}"
  }

}
