package de.atiw.sportfest.backend.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import de.atiw.sportfest.backend.model.datentypen.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;


@Entity
@NamedQueries({
@NamedQuery(name="typ.list", query="SELECT t FROM Typ t")
})
public class Typ   {
  
  @Id
  @GeneratedValue
  private Long id = null;

public enum DatentypEnum {

    STRING(String.valueOf("string"), new StringTyp()),
    INT(String.valueOf("int"), new IntegerTyp()),
    FLOAT(String.valueOf("float"), new FloatTyp()),
    LONG(String.valueOf("long"), new LongTyp()),
    DOUBLE(String.valueOf("double"), new DoubleTyp());

    private String value;
    private Datentyp<?> datentyp;

    DatentypEnum (String v, Datentyp<?> d) {
        value = v;
        datentyp = d;
    }

    public Object load(String s){
        return datentyp.load(s);
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

  @Enumerated(EnumType.STRING)
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

// vim: set ts=2 sw=2 tw=0 et :
