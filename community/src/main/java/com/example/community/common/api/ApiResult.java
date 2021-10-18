package com.example.community.common.api;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Optional;


@Data
@NoArgsConstructor

public class ApiResult<T> implements Serializable {

    private static final long serialVersionUID = -4153430394359594346L;
    private long code;
    private T data;
    private String message;

    public ApiResult(long code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ApiResult(IErrorCode errorCode) {
        errorCode = Optional.ofNullable(errorCode).orElse(ApiErrorCode.FAILED);//orElseGet
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public static <T> ApiResult<T> success() {
        return new ApiResult<T>(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMessage(), null);
    }

    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<T>(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMessage(), data);
    }

    public static <T> ApiResult<T> success(T data, String message) {
        return new ApiResult<T>(ApiErrorCode.SUCCESS.getCode(), message, data);
    }

    public static <T> ApiResult<T> failed() {
        return failed(ApiErrorCode.FAILED);
    }

    public static <T> ApiResult<T> failed(String message) {
        return new ApiResult<T>(ApiErrorCode.FAILED.getCode(), message, null);
    }

    public static <T> ApiResult<T> failed(IErrorCode errorCode) {
        return new ApiResult<T>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    public static <T> ApiResult<T> failed(IErrorCode errorCode, String message) {
        return new ApiResult<T>(errorCode.getCode(), message, null);
    }

    public static <T> ApiResult<T> validateFailed() {
        return failed(ApiErrorCode.VALIDATE_FAILED);
    }

    public static <T> ApiResult<T> validateFailed(String message) {
        return new ApiResult<T>(ApiErrorCode.VALIDATE_FAILED.getCode(), message, null);
    }

    public static <T> ApiResult<T> unauthorized(T data) {
        return new ApiResult<T>(ApiErrorCode.UNAUTHORIZED.getCode(), ApiErrorCode.UNAUTHORIZED.getMessage(), data);
    }

    public static <T> ApiResult<T> forbidden(T data) {
        return new ApiResult<T>(ApiErrorCode.FORBIDDEN.getCode(), ApiErrorCode.FORBIDDEN.getMessage(), data);
    }
}
