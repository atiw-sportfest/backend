package de.atiw.sportfest.backend.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import de.atiw.sportfest.backend.model.Variable;
import io.swagger.annotations.*;
import javax.validation.constraints.*;


@NamedQueries({
@NamedQuery(name="disziplin.list", query="SELECT d FROM Disziplin d JOIN FETCH d.variablen")
})
@Entity
public class Disziplin   {
  
  @Id
  @GeneratedValue
  private Long id = null;
  private String bezeichnung = null;
  private String beschreibung = null;
  private Boolean team = null;
  private Boolean versus = null;
  private String regeln = null;

  @OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.EAGER)
  @JoinColumn
  private List<Variable> variablen = new ArrayList<Variable>();

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
  public Disziplin versus(Boolean versus) {
    this.versus = versus;
    return this;
  }

  
  @ApiModelProperty(value = "")
  public Boolean getVersus() {
    return versus;
  }
  public void setVersus(Boolean versus) {
    this.versus = versus;
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
  public Disziplin regeln(String regeln) {
    this.regeln = regeln;
    return this;
  }

  
  @ApiModelProperty(value = "")
  public String getRegeln() {
    return regeln;
  }
  public void setRegeln(String regeln) {
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
        Objects.equals(versus, disziplin.versus) &&
        Objects.equals(variablen, disziplin.variablen) &&
        Objects.equals(regeln, disziplin.regeln);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, bezeichnung, beschreibung, team, versus, variablen, regeln);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Disziplin {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    bezeichnung: ").append(toIndentedString(bezeichnung)).append("\n");
    sb.append("    beschreibung: ").append(toIndentedString(beschreibung)).append("\n");
    sb.append("    team: ").append(toIndentedString(team)).append("\n");
    sb.append("    versus: ").append(toIndentedString(versus)).append("\n");
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

// vim: set ts=2 sw=2 tw=0 et :
