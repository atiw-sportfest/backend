package de.atiw.sportfest.backend.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import de.atiw.sportfest.backend.model.Klasse;
import io.swagger.annotations.*;
import javax.validation.constraints.*;


@Entity
public class Schueler   {
  
  @Id
  @GeneratedValue
  private Long id = null;
  private String vorname = null;
  private String nachname = null;
  private String geschlecht = null;

  @ManyToOne
  private Klasse klasse = null;

  /**
   **/
  public Schueler id(Long id) {
    this.id = id;
    return this;
  }

  
  @ApiModelProperty(value = "")
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }

  /**
   **/
  public Schueler vorname(String vorname) {
    this.vorname = vorname;
    return this;
  }

  
  @ApiModelProperty(value = "")
  public String getVorname() {
    return vorname;
  }
  public void setVorname(String vorname) {
    this.vorname = vorname;
  }

  /**
   **/
  public Schueler nachname(String nachname) {
    this.nachname = nachname;
    return this;
  }

  
  @ApiModelProperty(value = "")
  public String getNachname() {
    return nachname;
  }
  public void setNachname(String nachname) {
    this.nachname = nachname;
  }

  /**
   **/
  public Schueler geschlecht(String geschlecht) {
    this.geschlecht = geschlecht;
    return this;
  }

  
  @ApiModelProperty(value = "")
  public String getGeschlecht() {
    return geschlecht;
  }
  public void setGeschlecht(String geschlecht) {
    this.geschlecht = geschlecht;
  }

  /**
   **/
  public Schueler klasse(Klasse klasse) {
    this.klasse = klasse;
    return this;
  }

  
  @ApiModelProperty(value = "")
  public Klasse getKlasse() {
    return klasse;
  }
  public void setKlasse(Klasse klasse) {
    this.klasse = klasse;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Schueler schueler = (Schueler) o;
    return Objects.equals(id, schueler.id) &&
        Objects.equals(vorname, schueler.vorname) &&
        Objects.equals(nachname, schueler.nachname) &&
        Objects.equals(geschlecht, schueler.geschlecht) &&
        Objects.equals(klasse, schueler.klasse);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, vorname, nachname, geschlecht, klasse);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Schueler {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    vorname: ").append(toIndentedString(vorname)).append("\n");
    sb.append("    nachname: ").append(toIndentedString(nachname)).append("\n");
    sb.append("    geschlecht: ").append(toIndentedString(geschlecht)).append("\n");
    sb.append("    klasse: ").append(toIndentedString(klasse)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

