package com.example.demo.exception;

import com.example.demo.common.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Catches DTO validation errors (@Valid failures).
     * Maps field names to their specific validation messages (similar to Laravel $errors->all()).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        log.warn("Validation failed: {}", errors);

        return ApiResponse.failWithErrors(
                "Validasiya xətası baş verdi",
                errors,
                HttpStatus.UNPROCESSABLE_ENTITY
        );
    }

    /**
     * Catches 404 Resource Not Found errors.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleResourceNotFound(ResourceNotFoundException ex) {
        log.warn("Resource not found: {}", ex.getMessage());
        return ApiResponse.fail(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Catches 400 Bad Request errors.
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<Object>> handleBadRequest(BadRequestException ex) {
        log.warn("Bad request: {}", ex.getMessage());
        return ApiResponse.fail(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Catches all unhandled internal server exceptions (500).
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneralException(Exception ex) {
        log.error("Gözlənilməyən server xətası: ", ex);
        return ApiResponse.fail("Daxili server xətası baş verdi", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
