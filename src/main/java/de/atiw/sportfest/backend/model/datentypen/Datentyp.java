package de.atiw.sportfest.backend.model.datentypen;

public interface Datentyp<T> {

  public T load(String s);

  public String save(T t);

}

