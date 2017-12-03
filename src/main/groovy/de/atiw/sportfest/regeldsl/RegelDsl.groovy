package de.atiw.sportfest.regeldsl;

import de.atiw.sportfest.regeldsl.model.*;
import de.atiw.sportfest.regeldsl.*;

class RegelDsl extends RegelConfiguration implements Createable<RegelDsl> {

    //RegelDsl(){ super(); }
    RegelDsl getDelegateInstance(){ return this; }

    void eval(List<EvalConfiguration> target, String name, Closure script){
        target << new EvalDsl().name(name).create(script);
    }

    void direkt(String name, Closure script){ eval(direkt, name, script); }
    void sonder(String name, Closure script){ eval(sonder, name, script); }

}
