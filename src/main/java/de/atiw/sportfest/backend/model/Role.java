package de.atiw.sportfest.backend.model;

import javax.validation.constraints.*;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Gets or Sets Role
 */
public enum Role {
  
  GAST("GAST"),
  
  SCHIEDSRICHTER("SCHIEDSRICHTER"),
  
  ADMIN("ADMIN");

  private String value;

  Role(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static Role fromValue(String text) {
    for (Role b : Role.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}


