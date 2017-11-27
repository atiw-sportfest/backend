package de.atiw.sportfest.backend.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import io.swagger.annotations.*;
import javax.validation.constraints.*;


@Entity
public class Regeln   {
  
  @Id
  @GeneratedValue
  private Long id = null;
  private String skript = null;
  private Boolean sonder = null;

  /**
   **/
  public Regeln id(Long id) {
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
  public Regeln skript(String skript) {
    this.skript = skript;
    return this;
  }

  
  @ApiModelProperty(value = "")
  public String getSkript() {
    return skript;
  }
  public void setSkript(String skript) {
    this.skript = skript;
  }

  /**
   **/
  public Regeln sonder(Boolean sonder) {
    this.sonder = sonder;
    return this;
  }

  
  @ApiModelProperty(value = "")
  public Boolean getSonder() {
    return sonder;
  }
  public void setSonder(Boolean sonder) {
    this.sonder = sonder;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Regeln regeln = (Regeln) o;
    return Objects.equals(id, regeln.id) &&
        Objects.equals(skript, regeln.skript) &&
        Objects.equals(sonder, regeln.sonder);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, skript, sonder);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Regeln {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    skript: ").append(toIndentedString(skript)).append("\n");
    sb.append("    sonder: ").append(toIndentedString(sonder)).append("\n");
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
