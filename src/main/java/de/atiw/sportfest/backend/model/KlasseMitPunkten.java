package de.atiw.sportfest.backend.model;

import de.atiw.sportfest.backend.model.Klasse;
import javax.validation.constraints.*;


import io.swagger.annotations.*;
import java.util.Objects;


public class KlasseMitPunkten   {
  
  private Klasse klasse = null;
  private Integer punkte = null;

  /**
   **/
  public KlasseMitPunkten klasse(Klasse klasse) {
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
  public KlasseMitPunkten punkte(Integer punkte) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    KlasseMitPunkten klasseMitPunkten = (KlasseMitPunkten) o;
    return Objects.equals(klasse, klasseMitPunkten.klasse) &&
        Objects.equals(punkte, klasseMitPunkten.punkte);
  }

  @Override
  public int hashCode() {
    return Objects.hash(klasse, punkte);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class KlasseMitPunkten {\n");
    
    sb.append("    klasse: ").append(toIndentedString(klasse)).append("\n");
    sb.append("    punkte: ").append(toIndentedString(punkte)).append("\n");
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

