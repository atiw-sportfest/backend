package de.atiw.sportfest.regeldsl;

import groovy.lang.Closure;
import groovy.lang.GroovyShell;

public interface StringCreateable<T> extends Createable<T> {

    @SuppressWarnings("unchecked") // we're only interesed in the body, not the result
    default T create(String script){
        return create((Closure<Void>) new GroovyShell().evaluate(String.format("{->%s}", script)));
    }
}
