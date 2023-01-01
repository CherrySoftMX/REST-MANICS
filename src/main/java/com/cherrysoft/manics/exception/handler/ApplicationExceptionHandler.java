package com.cherrysoft.manics.exception.handler;

import com.cherrysoft.manics.exception.*;
import com.cherrysoft.manics.exception.user.UserNotFoundException;
import com.cherrysoft.manics.exception.user.UsernameAlreadyTakenException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class ApplicationExceptionHandler {

  @ExceptionHandler(ApplicationException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<Object> appExceptionHandler(final ApplicationException e) {
    return throwCustomException(e, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(CartoonNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<Object> appExceptionHandler(final CartoonNotFoundException e) {
    return throwCustomException(e, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(InvalidCartoonTypeException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<Object> appExceptionHandler(final InvalidCartoonTypeException e) {
    return throwCustomException(e, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(CategoryNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<Object> appExceptionHandler(final CategoryNotFoundException e) {
    return throwCustomException(e, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(UserNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<Object> appExceptionHandler(final UserNotFoundException e) {
    return throwCustomException(e, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(UsernameAlreadyTakenException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<Object> appExceptionHandler(final UsernameAlreadyTakenException e) {
    return throwCustomException(e, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ChapterNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<Object> appExceptionHandler(final ChapterNotFoundException e) {
    return throwCustomException(e, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(PageNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<Object> appExceptionHandler(final PageNotFoundException e) {
    return throwCustomException(e, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(CommentNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<Object> appExceptionHandler(final CommentNotFoundException e) {
    return throwCustomException(e, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(AmbiguousFilterException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<Object> appExceptionHandler(final AmbiguousFilterException e) {
    return throwCustomException(e, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(SuggestionNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<Object> appExceptionHandler(final SuggestionNotFoundException e) {
    return throwCustomException(e, HttpStatus.NOT_FOUND);
  }

  private ResponseEntity<Object> throwCustomException(final RuntimeException e, final HttpStatus status) {
    return new ResponseEntity<>(new ErrorResponse(e, status), status);
  }

}
