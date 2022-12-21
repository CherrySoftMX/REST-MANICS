package com.cherrysoft.manics.logging.aspect;

import com.cherrysoft.manics.logging.aspect.utils.LoggingUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceExceptionLoggingAspect {

  @Around("execution(* com.cherrysoft.manics.service.v2..*.*(..))")
  public Object logAroundMethods(final ProceedingJoinPoint joinPoint) throws Throwable {
    return LoggingUtils.logMethodAround(joinPoint);
  }

  @AfterThrowing(
      pointcut = "execution(* com.cherrysoft.manics.service.v2..*.*(..))",
      throwing = "exception"
  )
  public void logAfterThrowing(final JoinPoint joinPoint, final Throwable exception) {
    LoggingUtils.logAfterThrowing(joinPoint, exception);
  }

}
