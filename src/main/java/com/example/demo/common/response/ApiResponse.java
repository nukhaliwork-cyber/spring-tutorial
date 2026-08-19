package com.example.demo.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Slf4j
public class ApiResponse<T> {

    private Boolean success = true;

    private String message;

    private T data;

    private Object errors;

    private PaginationMeta meta;

    private LocalDateTime timestamp = LocalDateTime.now();

    public static <T> ApiResponseBuilder<T> builder() {
        return new ApiResponseBuilder<>();
    }

    // =========================================================================
    // Fluent Builder & Response Helper
    // =========================================================================
    public static class ApiResponseBuilder<T> {
        private Boolean success = true;
        private String message;
        private T data;
        private Object errors;
        private PaginationMeta meta;
        private LocalDateTime timestamp = LocalDateTime.now();
        private HttpStatus httpStatus = HttpStatus.OK;

        public ApiResponseBuilder<T> success(String message) {
            this.success = true;
            this.message = message;
            return this;
        }

        public ApiResponseBuilder<T> success(Boolean success) {
            this.success = success;
            return this;
        }

        public ApiResponseBuilder<T> message(String message) {
            this.message = message;
            return this;
        }

        public ApiResponseBuilder<T> error(String message) {
            this.success = false;
            this.message = message;
            if (this.httpStatus == HttpStatus.OK) {
                this.httpStatus = HttpStatus.BAD_REQUEST;
            }
            return this;
        }

        public ApiResponseBuilder<T> errorWithLog(String message) {
            log.error("API Error: {}", message);
            return this.error(message);
        }

        public ApiResponseBuilder<T> errorWithLog(String message, Throwable throwable) {
            log.error("API Exception: {}", message, throwable);
            return this.error(message);
        }

        public ApiResponseBuilder<T> data(T data) {
            this.data = data;
            return this;
        }

        public ApiResponseBuilder<T> setData(T data) {
            this.data = data;
            return this;
        }

        public ApiResponseBuilder<T> errors(Object errors) {
            this.errors = errors;
            return this;
        }

        public ApiResponseBuilder<T> meta(PaginationMeta meta) {
            this.meta = meta;
            return this;
        }

        public ApiResponseBuilder<T> setPaginatedData(T data, Page<?> page) {
            this.data = data;
            this.meta = PaginationMeta.fromPage(page);
            return this;
        }

        public ApiResponseBuilder<T> timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ApiResponseBuilder<T> status(HttpStatus status) {
            this.httpStatus = status;
            return this;
        }

        public ApiResponse<T> build() {
            return new ApiResponse<>(
                    this.success,
                    this.message,
                    this.data,
                    this.errors,
                    this.meta,
                    this.timestamp
            );
        }

        public ResponseEntity<ApiResponse<T>> response() {
            ApiResponse<T> body = this.build();
            return new ResponseEntity<>(body, this.httpStatus);
        }

        public ResponseEntity<ApiResponse<T>> response(HttpStatus status) {
            this.httpStatus = status;
            return this.response();
        }
    }

    // =========================================================================
    // Static Quick Helpers
    // =========================================================================
    public static <T> ResponseEntity<ApiResponse<T>> ok(String message, T data) {
        return ApiResponse.<T>builder()
                .success(message)
                .setData(data)
                .status(HttpStatus.OK)
                .response();
    }

    public static <T> ResponseEntity<ApiResponse<T>> ok(T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .setData(data)
                .status(HttpStatus.OK)
                .response();
    }

    public static <T> ResponseEntity<ApiResponse<T>> created(String message, T data) {
        return ApiResponse.<T>builder()
                .success(message)
                .setData(data)
                .status(HttpStatus.CREATED)
                .response();
    }

    public static <T> ResponseEntity<ApiResponse<T>> fail(String message, HttpStatus status) {
        return ApiResponse.<T>builder()
                .error(message)
                .status(status)
                .response();
    }

    public static <T> ResponseEntity<ApiResponse<T>> failWithErrors(String message, Object errors, HttpStatus status) {
        return ApiResponse.<T>builder()
                .error(message)
                .errors(errors)
                .status(status)
                .response();
    }
}
