package com.manics.rest.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  public static ResponseEntity<Object> marshalErrorResponse(HttpStatus status, String error) {
    return marshalErrorResponse(status, new ArrayList<>(Collections.singleton(error)));
  }

  public static ResponseEntity<Object> marshalErrorResponse(HttpStatus status, List<String> errors) {
    Map<String, Object> body = new HashMap<>();
    body.put("timestamp", new Date());
    body.put("status", status.value());
    body.put("errors", errors);
    return new ResponseEntity<>(body, status);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request
  ) {

    List<String> errors = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .collect(Collectors.toList());

    return marshalErrorResponse(status, errors);
  }

}