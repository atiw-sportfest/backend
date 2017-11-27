package de.atiw.sportfest.backend.model;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;


import io.swagger.annotations.*;
import java.util.Objects;


public class Teilnehmer   {
  
  private List<Integer> teilnehmer = new ArrayList<Integer>();

  /**
   **/
  public Teilnehmer teilnehmer(List<Integer> teilnehmer) {
    this.teilnehmer = teilnehmer;
    return this;
  }

  
  @ApiModelProperty(value = "")
  public List<Integer> getTeilnehmer() {
    return teilnehmer;
  }
  public void setTeilnehmer(List<Integer> teilnehmer) {
    this.teilnehmer = teilnehmer;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Teilnehmer teilnehmer = (Teilnehmer) o;
    return Objects.equals(teilnehmer, teilnehmer.teilnehmer);
  }

  @Override
  public int hashCode() {
    return Objects.hash(teilnehmer);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Teilnehmer {\n");
    
    sb.append("    teilnehmer: ").append(toIndentedString(teilnehmer)).append("\n");
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

