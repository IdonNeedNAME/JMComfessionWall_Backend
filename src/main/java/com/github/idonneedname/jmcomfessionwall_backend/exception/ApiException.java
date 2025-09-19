package com.github.idonneedname.jmcomfessionwall_backend.exception;


import com.github.idonneedname.jmcomfessionwall_backend.constant.ExceptionEnum;
import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
    private final Integer errorCode;
    private final String errorMsg;

    public ApiException(Integer errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public ApiException(Integer errorCode, String errorMsg, Throwable cause) {
        super(errorMsg, cause);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
    public ApiException(ExceptionEnum exceptionEnum) {
        this(exceptionEnum.getErrorCode(), exceptionEnum.getErrorMsg());
    }
    public ApiException(ExceptionEnum exceptionEnum, Throwable cause) {
        this(exceptionEnum.getErrorCode(), exceptionEnum.getErrorMsg(), cause);
    }
}
