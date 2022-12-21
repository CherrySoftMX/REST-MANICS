package com.cherrysoft.manics.logging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@Slf4j
public class RequestLoggerInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler
  ) {
    String endpoint = request.getRequestURI();
    String method = request.getMethod();
    String queryString = request.getQueryString();
    Map<String, String> headers = withAuthorizationHeaderObfuscated(extractHeaders(request));
    log.info("Endpoint: {} | Verb: {} | QueryString: {} | Headers: {}", endpoint, method, queryString, headers);
    return true;
  }

  private Map<String, String> extractHeaders(HttpServletRequest request) {
    return Collections.list(request.getHeaderNames())
        .stream()
        .collect(toMap(Function.identity(), request::getHeader));
  }

  private Map<String, String> withAuthorizationHeaderObfuscated(Map<String, String> headers) {
    String key = "authorization";
    if (headers.containsKey(key)) {
      headers.put(key, "Bearer *******");
    }
    return headers;
  }

}
