package de.atiw.sportfest.backend.model;

import javax.validation.constraints.*;


import io.swagger.annotations.*;
import java.util.Objects;


public class Typ   {
  
  private Long id = null;

public enum DatentypEnum {

    STRING(String.valueOf("string")), INT(String.valueOf("int")), FLOAT(String.valueOf("float")), LONG(String.valueOf("long")), DOUBLE(String.valueOf("double"));


    private String value;

    DatentypEnum (String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public static DatentypEnum fromValue(String v) {
        for (DatentypEnum b : DatentypEnum.values()) {
            if (String.valueOf(b.value).equals(v)) {
                return b;
            }
        }
        return null;
    }
}

  private DatentypEnum datentyp = null;
  private String einheit = null;
  private String format = null;
  private String bsp = null;

  /**
   **/
  public Typ id(Long id) {
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
  public Typ datentyp(DatentypEnum datentyp) {
    this.datentyp = datentyp;
    return this;
  }

  
  @ApiModelProperty(value = "")
  public DatentypEnum getDatentyp() {
    return datentyp;
  }
  public void setDatentyp(DatentypEnum datentyp) {
    this.datentyp = datentyp;
  }

  /**
   **/
  public Typ einheit(String einheit) {
    this.einheit = einheit;
    return this;
  }

  
  @ApiModelProperty(value = "")
  public String getEinheit() {
    return einheit;
  }
  public void setEinheit(String einheit) {
    this.einheit = einheit;
  }

  /**
   * RegEx
   **/
  public Typ format(String format) {
    this.format = format;
    return this;
  }

  
  @ApiModelProperty(value = "RegEx")
  public String getFormat() {
    return format;
  }
  public void setFormat(String format) {
    this.format = format;
  }

  /**
   * Beispiel
   **/
  public Typ bsp(String bsp) {
    this.bsp = bsp;
    return this;
  }

  
  @ApiModelProperty(value = "Beispiel")
  public String getBsp() {
    return bsp;
  }
  public void setBsp(String bsp) {
    this.bsp = bsp;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Typ typ = (Typ) o;
    return Objects.equals(id, typ.id) &&
        Objects.equals(datentyp, typ.datentyp) &&
        Objects.equals(einheit, typ.einheit) &&
        Objects.equals(format, typ.format) &&
        Objects.equals(bsp, typ.bsp);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, datentyp, einheit, format, bsp);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Typ {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    datentyp: ").append(toIndentedString(datentyp)).append("\n");
    sb.append("    einheit: ").append(toIndentedString(einheit)).append("\n");
    sb.append("    format: ").append(toIndentedString(format)).append("\n");
    sb.append("    bsp: ").append(toIndentedString(bsp)).append("\n");
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

