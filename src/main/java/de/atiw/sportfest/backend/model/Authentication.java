package de.atiw.sportfest.backend.model;

import javax.validation.constraints.*;


import io.swagger.annotations.*;
import java.util.Objects;


public class Authentication   {
  
  private Boolean success = null;
  private Boolean intial = null;
  private String token = null;

  /**
   **/
  public Authentication success(Boolean success) {
    this.success = success;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @NotNull
  public Boolean getSuccess() {
    return success;
  }
  public void setSuccess(Boolean success) {
    this.success = success;
  }

  /**
   **/
  public Authentication intial(Boolean intial) {
    this.intial = intial;
    return this;
  }

  
  @ApiModelProperty(value = "")
  public Boolean getIntial() {
    return intial;
  }
  public void setIntial(Boolean intial) {
    this.intial = intial;
  }

  /**
   **/
  public Authentication token(String token) {
    this.token = token;
    return this;
  }

  
  @ApiModelProperty(value = "")
  public String getToken() {
    return token;
  }
  public void setToken(String token) {
    this.token = token;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Authentication authentication = (Authentication) o;
    return Objects.equals(success, authentication.success) &&
        Objects.equals(intial, authentication.intial) &&
        Objects.equals(token, authentication.token);
  }

  @Override
  public int hashCode() {
    return Objects.hash(success, intial, token);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Authentication {\n");
    
    sb.append("    success: ").append(toIndentedString(success)).append("\n");
    sb.append("    intial: ").append(toIndentedString(intial)).append("\n");
    sb.append("    token: ").append(toIndentedString(token)).append("\n");
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

