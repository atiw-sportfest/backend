package de.atiw.sportfest.regeldsl;

class EvalDsl extends EvalConfiguration implements Createable<EvalDsl> {

  RuleConfiguration lastRule = null;

  EvalDsl getDelegateInstance(){ return this; }

  void cond(Closure script){ condition = script; }

  void regel(Integer pts, Closure script){

    def newRule = new RuleConfiguration().points(pts).condition(script);

    if(lastRule == null){
      lastRule = firstRule = newRule;
    } else {
      lastRule.next = newRule;
      lastRule = newRule;
    }

  }

  void regel(Integer pts){
    regel(pts, { true })
  }

  void sort(String sortAttrName){
    sort({ erg -> erg.getLeistungen().find({ l -> l.getVariable().getBezeichnung() == sortAttrName }); });
  }

  void sort(Closure sortAttr = { erg-> erg.getPunkte() }){
    sortCl = { ergs ->
      ergs.sort(sortAttr).reverse()
    }
  }

  void sort(boolean desc, Closure sortAttr = null){
    sortCl = { ergs ->
      def sorted = (sortAttr ? sort(sortAttr) : sort());
      desc ? sorted.reverse() : sorted;
    }
  }

  void top(int limit){
    this.limit = limit;
  }

  void punkte(Integer[] pts){
    points = pts;
  }

  void flat(boolean flat=true){
    this.flat = flat;
  }

}
