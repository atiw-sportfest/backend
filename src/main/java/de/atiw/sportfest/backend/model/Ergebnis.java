package de.atiw.sportfest.backend.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import de.atiw.sportfest.backend.model.Disziplin;
import de.atiw.sportfest.backend.model.Klasse;
import de.atiw.sportfest.backend.model.Leistung;
import de.atiw.sportfest.backend.model.Schueler;
import io.swagger.annotations.*;
import javax.validation.constraints.*;


@Entity
public class Ergebnis   {

  @Id
  @GeneratedValue
  private Long id = null;
  private Integer punkte = null;

  @OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE})
  private List<Leistung> leistungen = new ArrayList<Leistung>();

  @ManyToOne
  private Klasse klasse = null;

  @ManyToOne
  private Schueler schueler = null;

  @ManyToOne
  private Disziplin disziplin = null;

  /**
   **/
  public Ergebnis id(Long id) {
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
  public Ergebnis punkte(Integer punkte) {
    this.punkte = punkte;
    return this;
  }

  
  @ApiModelProperty(value = "")
  public Integer getPunkte() {
    return punkte;
  }
  public void setPunkte(Integer punkte) {
    this.punkte = punkte;
  }

  /**
   **/
  public Ergebnis leistungen(List<Leistung> leistungen) {
    this.leistungen = leistungen;
    return this;
  }

  
  @ApiModelProperty(value = "")
  public List<Leistung> getLeistungen() {
    return leistungen;
  }
  public void setLeistungen(List<Leistung> leistungen) {
    this.leistungen = leistungen;
  }

  /**
   **/
  public Ergebnis klasse(Klasse klasse) {
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

  /**
   **/
  public Ergebnis schueler(Schueler schueler) {
    this.schueler = schueler;
    return this;
  }

  
  @ApiModelProperty(value = "")
  public Schueler getSchueler() {
    return schueler;
  }
  public void setSchueler(Schueler schueler) {
    this.schueler = schueler;
  }

  /**
   **/
  public Ergebnis disziplin(Disziplin disziplin) {
    this.disziplin = disziplin;
    return this;
  }

  
  @ApiModelProperty(value = "")
  public Disziplin getDisziplin() {
    return disziplin;
  }
  public void setDisziplin(Disziplin disziplin) {
    this.disziplin = disziplin;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Ergebnis ergebnis = (Ergebnis) o;
    return Objects.equals(id, ergebnis.id) &&
        Objects.equals(punkte, ergebnis.punkte) &&
        Objects.equals(leistungen, ergebnis.leistungen) &&
        Objects.equals(klasse, ergebnis.klasse) &&
        Objects.equals(schueler, ergebnis.schueler) &&
        Objects.equals(disziplin, ergebnis.disziplin);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, punkte, leistungen, klasse, schueler, disziplin);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Ergebnis {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    punkte: ").append(toIndentedString(punkte)).append("\n");
    sb.append("    leistungen: ").append(toIndentedString(leistungen)).append("\n");
    sb.append("    klasse: ").append(toIndentedString(klasse)).append("\n");
    sb.append("    schueler: ").append(toIndentedString(schueler)).append("\n");
    sb.append("    disziplin: ").append(toIndentedString(disziplin)).append("\n");
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

