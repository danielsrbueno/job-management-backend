package com.danbueno.job_management.exceptions;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandler {

  @ExceptionHandler()
  public void handleMethodArgumentNotValidException (MethodArgumentNotValidException e) {

  }

}
