package dev.PlanningProject.services;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandler {

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public HttpStatusCode  handleValidationExceptions(MethodArgumentNotValidException ex) {
//        return ex.getStatusCode();
//    }

    //при такой реализации возвращает 404
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return errors;
    }

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(HttpMessageNotReadableException.class)
//    public Map<String, String> handleHttpMessageNotReadableException(
//            HttpMessageNotReadableException ex) {
//        System.out.println("HttpMessageNotReadableException handled");
//        Map<String, String> errors = new HashMap<>();
//        errors.put("error", "HttpMessageNotReadableException");
//        return errors;
//    }

}
