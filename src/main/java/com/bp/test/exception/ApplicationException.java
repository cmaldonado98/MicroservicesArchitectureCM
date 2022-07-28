package com.bp.test.exception;

import com.bp.test.model.enums.ResponseStatusCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApplicationException extends  RuntimeException{

  private final ResponseStatusCode status;

}
