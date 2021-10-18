package com.example.community.common.exception;

import com.example.community.common.api.IErrorCode;


public class ApiException extends RuntimeException {
    private IErrorCode errorCode;

    public ApiException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ApiException(String message) {
        super(message);
    }

    public IErrorCode getErrorCode() {
        return errorCode;
    }
}
