package com.cherrysoft.manics.exception.v2;

import com.cherrysoft.manics.util.ApplicationTimeUtils;
import com.cherrysoft.manics.util.ToStringUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
  private String timestamp;
  private Integer status;
  private String exception;
  private String message;
  private Map<String, String> validationErrors;

  public ErrorResponse(final Exception e, final HttpStatus status) {
    final StackTraceElement element = e.getStackTrace()[0];
    this.timestamp = ApplicationTimeUtils.getTimeString();
    this.status = status.value();
    this.exception = e.getClass().getName() + " at " + element.toString();
    this.message = e.getMessage();
  }

  public ErrorResponse(final HttpStatus status, final Map<String, String> validationErrors) {
    this.timestamp = ApplicationTimeUtils.getTimeString();
    this.status = status.value();
    this.validationErrors = new LinkedHashMap<>();
    this.validationErrors.putAll(validationErrors);
  }

  @Override
  public String toString() {
    return ToStringUtils.toJsonString(this);
  }

}
