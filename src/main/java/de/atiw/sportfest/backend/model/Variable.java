package de.atiw.sportfest.backend.model;

import de.atiw.sportfest.backend.model.Typ;
import javax.validation.constraints.*;


import io.swagger.annotations.*;
import java.util.Objects;


public class Variable   {
  
  private Long id = null;
  private String bezeichnung = null;
  private Typ typ = null;

  /**
   **/
  public Variable id(Long id) {
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
  public Variable bezeichnung(String bezeichnung) {
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
  public Variable typ(Typ typ) {
    this.typ = typ;
    return this;
  }

  
  @ApiModelProperty(value = "")
  public Typ getTyp() {
    return typ;
  }
  public void setTyp(Typ typ) {
    this.typ = typ;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Variable variable = (Variable) o;
    return Objects.equals(id, variable.id) &&
        Objects.equals(bezeichnung, variable.bezeichnung) &&
        Objects.equals(typ, variable.typ);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, bezeichnung, typ);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Variable {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    bezeichnung: ").append(toIndentedString(bezeichnung)).append("\n");
    sb.append("    typ: ").append(toIndentedString(typ)).append("\n");
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

