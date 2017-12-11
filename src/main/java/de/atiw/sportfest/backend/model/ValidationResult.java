package de.atiw.sportfest.backend.model;

import javax.validation.constraints.*;


import io.swagger.annotations.*;
import java.util.Objects;


public class ValidationResult   {
  
  private Boolean pass = null;
  private String messages = null;

  /**
   **/
  public ValidationResult pass(Boolean pass) {
    this.pass = pass;
    return this;
  }

  
  @ApiModelProperty(value = "")
  public Boolean getPass() {
    return pass;
  }
  public void setPass(Boolean pass) {
    this.pass = pass;
  }

  /**
   **/
  public ValidationResult messages(String messages) {
    this.messages = messages;
    return this;
  }

  
  @ApiModelProperty(value = "")
  public String getMessages() {
    return messages;
  }
  public void setMessages(String messages) {
    this.messages = messages;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ValidationResult validationResult = (ValidationResult) o;
    return Objects.equals(pass, validationResult.pass) &&
        Objects.equals(messages, validationResult.messages);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pass, messages);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ValidationResult {\n");
    
    sb.append("    pass: ").append(toIndentedString(pass)).append("\n");
    sb.append("    messages: ").append(toIndentedString(messages)).append("\n");
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

