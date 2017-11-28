package de.atiw.sportfest.backend.model;

import de.atiw.sportfest.backend.model.Disziplin;
import de.atiw.sportfest.backend.model.Schueler;
import javax.validation.constraints.*;


import io.swagger.annotations.*;
import java.util.Objects;


public class Anmeldung   {
  
  private Long id = null;
  private Disziplin disziplin = null;
  private Schueler schueler = null;

  /**
   **/
  public Anmeldung id(Long id) {
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
  public Anmeldung disziplin(Disziplin disziplin) {
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

  /**
   **/
  public Anmeldung schueler(Schueler schueler) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Anmeldung anmeldung = (Anmeldung) o;
    return Objects.equals(id, anmeldung.id) &&
        Objects.equals(disziplin, anmeldung.disziplin) &&
        Objects.equals(schueler, anmeldung.schueler);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, disziplin, schueler);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Anmeldung {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    disziplin: ").append(toIndentedString(disziplin)).append("\n");
    sb.append("    schueler: ").append(toIndentedString(schueler)).append("\n");
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

