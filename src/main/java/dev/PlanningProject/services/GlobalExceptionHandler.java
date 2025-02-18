package dev.PlanningProject.services;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String field = error.getField();
            String message = error.getDefaultMessage() != null ? error.getDefaultMessage() : "Validation error";
            errors.put(field, message);
        });
        logger.error("Validation error occurred: {}", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseBody
    public ResponseEntity<Map<String, String>> handleEntityNotFoundException(EntityNotFoundException ex) {
        String errorMessage = ex.getMessage() != null ? ex.getMessage() : "Resource not found";
        logger.error("EntityNotFoundException: {}", errorMessage);
        Map<String, String> errors = new HashMap<>();
        errors.put("error", "Resource not found: " + errorMessage);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        String errorMessage = ex.getMessage() != null ? ex.getMessage() : "Invalid input";
        logger.error("IllegalArgumentException: {}", errorMessage);
        Map<String, String> errors = new HashMap<>();
        errors.put("error", "Invalid input: " + errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseBody
    public ResponseEntity<Map<String, String>> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        String errorMessage = ex.getMessage() != null ? ex.getMessage() : "User not found";
        logger.error("UsernameNotFoundException: {}", errorMessage);
        Map<String, String> errors = new HashMap<>();
        errors.put("error", "Authentication failed: " + errorMessage);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errors);
    }

//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(Exception.class)
//    public Map<String, String> handleGenericException(Exception ex) {
//        String errorMessage = ex.getMessage() != null ? ex.getMessage() : "An unexpected error occurred";
//        logger.error("Unhandled exception: {}", errorMessage, ex);
//        Map<String, String> errors = new HashMap<>();
//        errors.put("error", "An unexpected error occurred. Please try again later.");
//        return errors;
//    }
}
