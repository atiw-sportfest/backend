package de.atiw.sportfest.regeldsl;

import groovy.lang.Closure;

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
}
