package dev.PlanningProject.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SaveLog {
    String action();
    String entityClass() default ""; //Назначается явно при операциях удаления(т.к. возвращает Long)
}
