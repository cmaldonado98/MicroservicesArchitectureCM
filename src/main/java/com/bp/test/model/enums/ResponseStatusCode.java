package com.bp.test.model.enums;

import lombok.Getter;


@Getter
public enum ResponseStatusCode {

  OK("0", "success"),
  INVALID_VERIFICATION_STATE("2", "Invalid verification state."),
  INVALID_PARAMETERS("3","Some or all the parameter are null or empty"),
  INVALID_STATUS("4","Status must be 1 or 0"),
  ORGANIZATION_DOES_NOT_EXISTS("5","The organizations does not exists."),
  NO_RESULTS("6","La Tribu no tiene repositorios que cumplan con la cobertura necesaria"),
  TRIBE_DOES_NOT_EXISTS("7","The tribe does not exists."),

  UNDEFINED_ERROR("500","Unexpected error: %s"),
  UNDEFINED_VALUE("-1","status not found")
  ;


  private final String code;
  private final String message;

  ResponseStatusCode(String code, String message){
    this.code = code;
    this.message = message;
  }

}
