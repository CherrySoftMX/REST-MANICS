package com.cherrysoft.manics.exception.v2.handler;

import com.cherrysoft.manics.exception.v2.ApplicationException;
import com.cherrysoft.manics.exception.v2.ErrorResponse;
import com.cherrysoft.manics.exception.v2.cartoon.CartoonNotFoundException;
import com.cherrysoft.manics.exception.v2.cartoon.CategoryNotFoundException;
import com.cherrysoft.manics.exception.v2.user.UserNotFoundException;
import com.cherrysoft.manics.exception.v2.user.UsernameAlreadyTakenException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class ApplicationExceptionHandler {

  @ExceptionHandler(ApplicationException.class)
  public ResponseEntity<Object> appExceptionHandler(final ApplicationException e) {
    return throwCustomException(e, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(CartoonNotFoundException.class)
  public ResponseEntity<Object> appExceptionHandler(final CartoonNotFoundException e) {
    return throwCustomException(e, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(CategoryNotFoundException.class)
  public ResponseEntity<Object> appExceptionHandler(final CategoryNotFoundException e) {
    return throwCustomException(e, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<Object> appExceptionHandler(final UserNotFoundException e) {
    return throwCustomException(e, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(UsernameAlreadyTakenException.class)
  public ResponseEntity<Object> appExceptionHandler(final UsernameAlreadyTakenException e) {
    return throwCustomException(e, HttpStatus.BAD_REQUEST);
  }

  private ResponseEntity<Object> throwCustomException(final RuntimeException e, final HttpStatus status) {
    return new ResponseEntity<>(new ErrorResponse(e, status), status);
  }

}
