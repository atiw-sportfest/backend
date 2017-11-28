package de.atiw.sportfest.backend.model;

import java.util.Objects;
import javax.annotation.Generated;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import de.atiw.sportfest.backend.model.Typ;
import io.swagger.annotations.*;
import javax.validation.constraints.*;


@Entity
public class Variable   {
  
  @Id
  @GeneratedValue
  private Long id = null;
  private String name = null;

  @ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.EAGER)
  @JoinColumn
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
  public Variable name(String name) {
    this.name = name;
    return this;
  }

  
  @ApiModelProperty(value = "")
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
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
        Objects.equals(name, variable.name) &&
        Objects.equals(typ, variable.typ);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, typ);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Variable {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
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

// vim: set ts=2 sw=2 tw=0 et :
