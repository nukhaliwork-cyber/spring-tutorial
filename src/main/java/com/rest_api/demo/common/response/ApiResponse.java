package com.rest_api.demo.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@JsonInclude(JsonInclude.Include.ALWAYS)
public class ApiResponse<T> {

    private Boolean success;
    private Boolean error;
    private String message;
    private T data;
    private int statusCode = HttpStatus.OK.value();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PaginationMeta meta;

    public ApiResponse() {
    }

    public static <T> ApiResponse<T> create() {
        return new ApiResponse<>();
    }

    public ApiResponse<T> success(String message, int statusCode) {
        this.success = true;
        this.error = false;
        this.message = message;
        this.statusCode = statusCode;
        return this;
    }

    public ApiResponse<T> success(String message) {
        return success(message, HttpStatus.OK.value());
    }

    public ApiResponse<T> success() {
        return success(null, HttpStatus.OK.value());
    }

    public ApiResponse<T> error(String message, int statusCode) {
        this.success = false;
        this.error = true;
        this.message = message;
        this.statusCode = statusCode;
        return this;
    }

    public ApiResponse<T> error(String message) {
        return error(message, HttpStatus.BAD_REQUEST.value());
    }

    public ApiResponse<T> data(T data) {
        this.data = data;
        return this;
    }

    public ApiResponse<T> setData(T data) {
        this.data = data;
        return this;
    }

    public ApiResponse<T> meta(PaginationMeta meta) {
        this.meta = meta;
        return this;
    }

    public ApiResponse<T> statusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public ResponseEntity<ApiResponse<T>> response() {
        return ResponseEntity.status(this.statusCode).body(this);
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public PaginationMeta getMeta() {
        return meta;
    }

    public void setMeta(PaginationMeta meta) {
        this.meta = meta;
    }
}
