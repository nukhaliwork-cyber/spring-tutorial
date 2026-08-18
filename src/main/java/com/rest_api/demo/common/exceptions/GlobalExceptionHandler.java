package com.rest_api.demo.common.exceptions;

import com.rest_api.demo.common.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404 Not Found Exception
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleResourceNotFound(ResourceNotFoundException ex) {
        return ApiResponse.<Object>create()
                .error(ex.getMessage(), HttpStatus.NOT_FOUND.value())
                .response();
    }

    // 422 Unprocessable Entity - Validation Exceptions (@Valid xətaları)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ApiResponse.<Map<String, String>>create()
                .error("Daxil edilən məlumatlarda xəta var", HttpStatus.UNPROCESSABLE_ENTITY.value())
                .data(errors)
                .response();
    }

    // 500 Daxili Server Xətaları
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGenericException(Exception ex) {
        return ApiResponse.<Object>create()
                .error(ex.getMessage() != null ? ex.getMessage() : "Gözlənilməz server xətası baş verdi", 
                       HttpStatus.INTERNAL_SERVER_ERROR.value())
                .response();
    }
}
