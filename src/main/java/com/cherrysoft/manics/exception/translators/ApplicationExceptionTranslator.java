package com.cherrysoft.manics.exception.translators;

import com.cherrysoft.manics.exception.*;
import com.cherrysoft.manics.exception.user.UserNotFoundException;
import com.cherrysoft.manics.exception.user.UsernameAlreadyTakenException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.*;
import org.zalando.problem.spring.web.advice.AdviceTrait;
import org.zalando.problem.spring.web.advice.validation.ConstraintViolationProblem;

import javax.servlet.http.HttpServletRequest;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@RestControllerAdvice
public class ApplicationExceptionTranslator implements AdviceTrait {
  private static final String PATH_KEY = "path";
  private static final String VIOLATIONS_KEY = "violations";

  @Override
  public ResponseEntity<Problem> process(@Nullable ResponseEntity<Problem> entity, @NonNull NativeWebRequest request) {
    if (isNull(entity)) {
      return null;
    }
    Problem problem = entity.getBody();
    if (!(problem instanceof ConstraintViolationProblem || problem instanceof DefaultProblem)) {
      return entity;
    }

    HttpServletRequest nativeRequest = request.getNativeRequest(HttpServletRequest.class);
    String requestUri = nonNull(nativeRequest) ? nativeRequest.getRequestURI() : StringUtils.EMPTY;
    ProblemBuilder builder = Problem
        .builder()
        .withType(Problem.DEFAULT_TYPE.equals(problem.getType()) ? ErrorConstants.DEFAULT_TYPE : problem.getType())
        .withTitle(problem.getTitle())
        .withStatus(problem.getStatus())
        .withDetail(problem.getDetail())
        .with(PATH_KEY, requestUri);
    if (problem instanceof ConstraintViolationProblem) {
      builder.with(VIOLATIONS_KEY, ((ConstraintViolationProblem) problem).getViolations());
    } else {
      builder.withCause(((DefaultProblem) problem).getCause()).withDetail(problem.getDetail()).withInstance(problem.getInstance());
      problem.getParameters().forEach(builder::with);
    }

    ThrowableProblem throwableProblem = builder.build();
    throwableProblem.setStackTrace(((ThrowableProblem) problem).getStackTrace());
    return new ResponseEntity<>(problem, entity.getHeaders(), entity.getStatusCode());
  }

  @ExceptionHandler
  public ResponseEntity<Problem> handleUsernameAlreadyTakenException(UsernameAlreadyTakenException ex, NativeWebRequest request) {
    return create(Status.BAD_REQUEST, ex, request, ErrorConstants.LOGIN_ALREADY_USED_TYPE);
  }

  @ExceptionHandler
  public ResponseEntity<Problem> handleUserNotFoundException(UserNotFoundException ex, NativeWebRequest request) {
    return createNotFoundResponseWithDefaultType(ex, request);
  }

  @ExceptionHandler
  public ResponseEntity<Problem> handleSuggestionNotFoundException(SuggestionNotFoundException ex, NativeWebRequest request) {
    return createNotFoundResponseWithDefaultType(ex, request);
  }

  @ExceptionHandler
  public ResponseEntity<Problem> handleCategoryNotFoundException(CategoryNotFoundException ex, NativeWebRequest request) {
    return createNotFoundResponseWithDefaultType(ex, request);
  }

  @ExceptionHandler
  public ResponseEntity<Problem> handleCommentNotFoundException(CommentNotFoundException ex, NativeWebRequest request) {
    return createNotFoundResponseWithDefaultType(ex, request);
  }

  @ExceptionHandler
  public ResponseEntity<Problem> handleAmbiguousFilterException(AmbiguousFilterException ex, NativeWebRequest request) {
    return createBadRequestResponseWithDefaultType(ex, request);
  }

  @ExceptionHandler
  public ResponseEntity<Problem> handlePageNotFoundException(PageNotFoundException ex, NativeWebRequest request) {
    return createNotFoundResponseWithDefaultType(ex, request);
  }

  @ExceptionHandler
  public ResponseEntity<Problem> handleChapterNotFoundException(ChapterNotFoundException ex, NativeWebRequest request) {
    return createNotFoundResponseWithDefaultType(ex, request);
  }

  @ExceptionHandler
  public ResponseEntity<Problem> handleCartoonNotFoundException(CartoonNotFoundException ex, NativeWebRequest request) {
    return createNotFoundResponseWithDefaultType(ex, request);
  }

  @ExceptionHandler
  public ResponseEntity<Problem> handleInvalidCartoonTypeException(InvalidCartoonTypeException ex, NativeWebRequest request) {
    return createBadRequestResponseWithDefaultType(ex, request);
  }

  private ResponseEntity<Problem> createBadRequestResponseWithDefaultType(Throwable ex, NativeWebRequest request) {
    return create(Status.BAD_REQUEST, ex, request, ErrorConstants.DEFAULT_TYPE);
  }

  private ResponseEntity<Problem> createNotFoundResponseWithDefaultType(Throwable ex, NativeWebRequest request) {
    return create(Status.NOT_FOUND, ex, request, ErrorConstants.DEFAULT_TYPE);
  }

}
