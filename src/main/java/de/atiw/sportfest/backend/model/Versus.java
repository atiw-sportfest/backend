package de.atiw.sportfest.backend.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

import de.atiw.sportfest.backend.model.Ergebnis;
import io.swagger.annotations.*;
import javax.validation.constraints.*;

@Entity
@NamedQueries({
@NamedQuery(name="versus.list", query="SELECT v FROM Versus v"),
// Disziplinen der Ergebnisse müssen im gleich sein, daher reicht der Vergleich der Disziplin des ersten Ergebnisses.
@NamedQuery(name="versus.listByDisziplin", query="SELECT v FROM Versus v JOIN v.ergebnisse e WHERE e.disziplin.id = :did and INDEX(e) = 0"),
})
public class Versus   {
  
  @Id
  @GeneratedValue
  private Long id = null;

  @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
  @OrderColumn
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

