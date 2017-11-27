package de.atiw.sportfest.backend.model;

import javax.validation.constraints.*;


import io.swagger.annotations.*;
import java.util.Objects;


public class Klasse   {
  
  private Long id = null;
  private String bezeichnung = null;

  /**
   **/
  public Klasse id(Long id) {
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
  public Klasse bezeichnung(String bezeichnung) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Klasse klasse = (Klasse) o;
    return Objects.equals(id, klasse.id) &&
        Objects.equals(bezeichnung, klasse.bezeichnung);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, bezeichnung);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Klasse {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    bezeichnung: ").append(toIndentedString(bezeichnung)).append("\n");
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

