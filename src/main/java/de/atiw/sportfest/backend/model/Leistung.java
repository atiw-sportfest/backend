package de.atiw.sportfest.backend.model;

import de.atiw.sportfest.backend.model.Variable;
import javax.validation.constraints.*;


import io.swagger.annotations.*;
import java.util.Objects;


public class Leistung   {
  
  private Long id = null;
  private String wert = null;
  private Variable variable = null;

  /**
   **/
  public Leistung id(Long id) {
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
  public Leistung wert(String wert) {
    this.wert = wert;
    return this;
  }

  
  @ApiModelProperty(value = "")
  public String getWert() {
    return wert;
  }
  public void setWert(String wert) {
    this.wert = wert;
  }

  /**
   **/
  public Leistung variable(Variable variable) {
    this.variable = variable;
    return this;
  }

  
  @ApiModelProperty(value = "")
  public Variable getVariable() {
    return variable;
  }
  public void setVariable(Variable variable) {
    this.variable = variable;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Leistung leistung = (Leistung) o;
    return Objects.equals(id, leistung.id) &&
        Objects.equals(wert, leistung.wert) &&
        Objects.equals(variable, leistung.variable);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, wert, variable);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Leistung {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    wert: ").append(toIndentedString(wert)).append("\n");
    sb.append("    variable: ").append(toIndentedString(variable)).append("\n");
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

