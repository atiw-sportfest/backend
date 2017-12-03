package de.atiw.sportfest.regeldsl;

import de.atiw.sportfest.backend.model.Ergebnis;

class RuleDsl extends RuleConfiguration {

  Ergebnis ergebnis;

  RuleDsl(Ergebnis erg){
    this.ergebnis = erg;
  }

  void setProperty(String name, Object value){

    if(metaClass.hasProperty(name))
      metaClass.setProperty(name, value);

    if(getMetaClass().hasProperty(name))
      getMetaClass().setProperty(name, value)
  }

  def getProperty(String name){

    if(name == null)
      throw new IllegalArgumentException("Cannot get a property when the name is null!");

    def lstg = ergebnis.getLeistungen().find({l -> l.getVariable()?.getBezeichnung() == name });

    if(lstg != null && lstg.getVariable().getTyp()?.getDatentyp() == null)
      throw new IllegalArgumentException("Variable ohne gültigen Datentyp!");

    if(lstg != null)
      return lstg.getVariable().getTyp().getDatentyp().load(lstg.getWert());

    def fromErg = Ergebnis.getMetaClass().getProperty(ergebnis, name);

    if(fromErg != null)
      return fromErg;

      if(name == "punkte")
        return ergebnis.getPunkte();

    return getMetaClass().getProperty(this, name);

  }

  def max(Number...vars){
    vars.max()
  }

}
