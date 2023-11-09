package com.mindata.superheroe.util;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionTimeLogger {

    private static final Logger logger = LoggerFactory.getLogger(ExecutionTimeLogger.class);

    @Around("@annotation(executionTimeLog)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint, ExecutionTimeLog executionTimeLog) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        String methodName = joinPoint.getSignature().toShortString();

        String description = executionTimeLog.value().isEmpty() ? methodName : executionTimeLog.value();

        logger.info("{} service executed in {} ms", description, executionTime);

        return result;
    }
}