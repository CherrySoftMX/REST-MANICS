package com.cherrysoft.manics.logging.aspect.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.StopWatch;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoggingUtils {

  public static Object logMethodAround(final ProceedingJoinPoint joinPoint) throws Throwable {
    StopWatch timer = new StopWatch();
    timer.start();
    Object proceed = joinPoint.proceed();
    timer.stop();
    String methodWithClassName = getMethodWithClassName(joinPoint);
    Map<String, String> argsWithNames = getArgsWithNames(joinPoint);
    long totalTimeMillis = timer.getTotalTimeMillis();
    log.debug("Method: {} | Arguments: {} | Execution time: {} ms", methodWithClassName, argsWithNames, totalTimeMillis);
    return proceed;
  }

  public static void logAfterThrowing(final JoinPoint joinPoint, final Throwable exception) {
    String methodWithClassName = getMethodWithClassName(joinPoint);
    Map<String, String> argsWithNames = getArgsWithNames(joinPoint);
    String exceptionDescription = exception.getClass().getSimpleName() + ": " + exception.getMessage();
    log.error("Method: {} | Arguments: {} | Exception: {}", methodWithClassName, argsWithNames, exceptionDescription);
  }

  private static String getMethodWithClassName(JoinPoint joinPoint) {
    String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
    String methodName = joinPoint.getSignature().getName();
    return String.format("%s.%s", className, methodName);
  }

  private static Map<String, String> getArgsWithNames(final JoinPoint joinPoint) {
    MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
    String[] paramNames = methodSignature.getParameterNames();
    Object[] args = joinPoint.getArgs();
    Map<String, String> result = new HashMap<>();
    for (int i = 0; i < args.length; i++) {
      String paramName = paramNames[i];
      Object arg = args[i];
      result.put(paramName, arg.toString());
    }
    return result;
  }

}
