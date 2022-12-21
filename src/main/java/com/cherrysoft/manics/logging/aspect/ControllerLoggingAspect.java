package com.cherrysoft.manics.logging.aspect;

import com.cherrysoft.manics.logging.aspect.utils.LoggingUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControllerLoggingAspect {

  @Around("execution(* com.cherrysoft.manics.web.v2.controller..*.*(..))")
  public Object logAroundMethods(final ProceedingJoinPoint joinPoint) throws Throwable {
    return LoggingUtils.logMethodAround(joinPoint);
  }

}
