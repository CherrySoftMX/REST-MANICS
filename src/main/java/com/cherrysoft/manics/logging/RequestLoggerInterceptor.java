package com.cherrysoft.manics.logging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

import static com.cherrysoft.manics.util.ToStringUtils.toJsonString;
import static java.util.Objects.requireNonNullElse;
import static java.util.stream.Collectors.toMap;

@Slf4j
public class RequestLoggerInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
    String uri = request.getRequestURI();
    String httpVerb = request.getMethod();
    String queryString = requireNonNullElse(request.getQueryString(), "");
    Map<String, String> headers = extractHeaders(request);
    var entriesToLog = Map.of("uri", uri, "httpVerb", httpVerb, "queryString", queryString, "headers", headers);
    log.info(toJsonString(entriesToLog));
    return true;
  }

  private Map<String, String> extractHeaders(HttpServletRequest request) {
    return Collections.list(request.getHeaderNames())
        .stream()
        .collect(toMap(Function.identity(), request::getHeader));
  }

}
