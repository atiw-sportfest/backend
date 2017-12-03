package de.atiw.sportfest.regeldsl;

import de.atiw.sportfest.regeldsl.model.*;
import de.atiw.sportfest.regeldsl.*;

class EvalDsl extends EvalConfiguration implements Createable<EvalDsl> {

  Regel lastRule = null;

  EvalDsl getDelegateInstance(){ return this; }

  void cond(Closure script){ condition = script; }

  void regel(Integer pts, Closure script){

    def newRule = new Regel().points(pts).condition(script);

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

  void sort(){}
  void sort(boolean desc){}

  void limit(Closure script){}

  void top(int limit){}

}
