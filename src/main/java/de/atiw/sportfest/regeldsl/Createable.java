package de.atiw.sportfest.regeldsl;

import groovy.lang.Closure;
import groovy.lang.GroovyShell;

public interface Createable<T> {

    T getDelegateInstance();

    default T create(Closure<Void> script){
        return create(script, Closure.DELEGATE_FIRST, getDelegateInstance());
    }

    default T create(Closure<Void> script, int resolveStrategy, T delegate){

        script.setDelegate(delegate);
        script.setResolveStrategy(resolveStrategy);

        script.call();

        return delegate;

    }

    @SuppressWarnings("unchecked") // we're only interesed in the body, not the result
    default T create(String script){
        return create((Closure<Void>) new GroovyShell().evaluate(String.format("{->%s}", script)));
    }
}
