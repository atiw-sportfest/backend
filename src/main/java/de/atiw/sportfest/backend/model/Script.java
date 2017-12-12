package de.atiw.sportfest.backend.model;

import javax.validation.constraints.*;


import io.swagger.annotations.*;
import java.util.Objects;


public class Script   {
  
  private String script = null;

  /**
   **/
  public Script script(String script) {
    this.script = script;
    return this;
  }

  
  @ApiModelProperty(value = "")
  public String getScript() {
    return script;
  }
  public void setScript(String script) {
    this.script = script;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Script script = (Script) o;
    return Objects.equals(script, script.script);
  }

  @Override
  public int hashCode() {
    return Objects.hash(script);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Script {\n");
    
    sb.append("    script: ").append(toIndentedString(script)).append("\n");
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

