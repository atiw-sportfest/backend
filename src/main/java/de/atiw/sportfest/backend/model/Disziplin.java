package de.atiw.sportfest.backend.model;

import de.atiw.sportfest.backend.model.Regeln;
import de.atiw.sportfest.backend.model.Variable;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;


import io.swagger.annotations.*;
import java.util.Objects;


public class Disziplin   {
  
  private Long id = null;
  private String bezeichnung = null;
  private String beschreibung = null;
  private Boolean team = null;
  private List<Variable> variablen = new ArrayList<Variable>();
  private List<Regeln> regeln = new ArrayList<Regeln>();

  /**
   **/
  public Disziplin id(Long id) {
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
  public Disziplin bezeichnung(String bezeichnung) {
    this.bezeichnung = bezeichnung;
    return this;
  }

  
  @ApiModelProperty(value = "")
  public String getBezeichnung() {
    return bezeichnung;
  }
  public void setBezeichnung(String bezeichnung) {
    this.bezeichnung = bezeichnung;
  }

  /**
   **/
  public Disziplin beschreibung(String beschreibung) {
    this.beschreibung = beschreibung;
    return this;
  }

  
  @ApiModelProperty(value = "")
  public String getBeschreibung() {
    return beschreibung;
  }
  public void setBeschreibung(String beschreibung) {
    this.beschreibung = beschreibung;
  }

  /**
   **/
  public Disziplin team(Boolean team) {
    this.team = team;
    return this;
  }

  
  @ApiModelProperty(value = "")
  public Boolean getTeam() {
    return team;
  }
  public void setTeam(Boolean team) {
    this.team = team;
  }

  /**
   **/
  public Disziplin variablen(List<Variable> variablen) {
    this.variablen = variablen;
    return this;
  }

  
  @ApiModelProperty(value = "")
  public List<Variable> getVariablen() {
    return variablen;
  }
  public void setVariablen(List<Variable> variablen) {
    this.variablen = variablen;
  }

  /**
   **/
  public Disziplin regeln(List<Regeln> regeln) {
    this.regeln = regeln;
    return this;
  }

  
  @ApiModelProperty(value = "")
  public List<Regeln> getRegeln() {
    return regeln;
  }
  public void setRegeln(List<Regeln> regeln) {
    this.regeln = regeln;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Disziplin disziplin = (Disziplin) o;
    return Objects.equals(id, disziplin.id) &&
        Objects.equals(bezeichnung, disziplin.bezeichnung) &&
        Objects.equals(beschreibung, disziplin.beschreibung) &&
        Objects.equals(team, disziplin.team) &&
        Objects.equals(variablen, disziplin.variablen) &&
        Objects.equals(regeln, disziplin.regeln);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, bezeichnung, beschreibung, team, variablen, regeln);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Disziplin {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    bezeichnung: ").append(toIndentedString(bezeichnung)).append("\n");
    sb.append("    beschreibung: ").append(toIndentedString(beschreibung)).append("\n");
    sb.append("    team: ").append(toIndentedString(team)).append("\n");
    sb.append("    variablen: ").append(toIndentedString(variablen)).append("\n");
    sb.append("    regeln: ").append(toIndentedString(regeln)).append("\n");
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

