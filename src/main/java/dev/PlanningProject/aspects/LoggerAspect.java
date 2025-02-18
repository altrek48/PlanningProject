package dev.PlanningProject.aspects;

import dev.PlanningProject.annotations.SaveLog;
import dev.PlanningProject.services.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.kafka.event.KafkaEvent;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@RequiredArgsConstructor
public class LoggerAspect {

    private final KafkaProducer kafkaProducer;

    @Pointcut("@annotation(dev.PlanningProject.annotations.SaveLog)")
    public void loggerMethod() {
    }

    @Around("loggerMethod()")
    public Object saveLog(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SaveLog annotation = method.getAnnotation(SaveLog.class);
        String eventType = annotation.action();
        String overrideClassName = annotation.entityClass();
        try {
            Object result = joinPoint.proceed();
            kafkaProducer.eventBuilder(result, eventType, overrideClassName, null);
            return result;
        } catch (Exception exception) {
            Object entity = joinPoint.getArgs()[0];
            kafkaProducer.eventBuilder(entity, eventType, overrideClassName, exception.getClass().getSimpleName());
            throw exception;
        }
    }
}
