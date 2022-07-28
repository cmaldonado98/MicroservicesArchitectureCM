package com.bp.test.model.enums;

import lombok.Getter;


@Getter
public enum ResponseStatusCode {

  OK("0", "success"),
  INVALID_PARAMETERS("1","Some or all the parameter are null or empty"),
  CLIENT_DOES_NOT_EXISTS("2","The client does not exists."),
  PASSWORD_DOES_NOT_MATCH("3","The password does not match."),

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
