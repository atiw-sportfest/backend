package de.atiw.sportfest.backend.model;

import de.atiw.sportfest.backend.model.Ergebnis;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;


import io.swagger.annotations.*;
import java.util.Objects;


public class Versus   {
  
  private Long id = null;
  private List<Ergebnis> ergebnisse = new ArrayList<Ergebnis>();

  /**
   **/
  public Versus id(Long id) {
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
  public Versus ergebnisse(List<Ergebnis> ergebnisse) {
    this.ergebnisse = ergebnisse;
    return this;
  }

  
  @ApiModelProperty(value = "")
  public List<Ergebnis> getErgebnisse() {
    return ergebnisse;
  }
  public void setErgebnisse(List<Ergebnis> ergebnisse) {
    this.ergebnisse = ergebnisse;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Versus versus = (Versus) o;
    return Objects.equals(id, versus.id) &&
        Objects.equals(ergebnisse, versus.ergebnisse);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, ergebnisse);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Versus {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    ergebnisse: ").append(toIndentedString(ergebnisse)).append("\n");
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

