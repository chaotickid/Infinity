package com.maakapyaar.common.exceptionHandling;

import lombok.Data;
import lombok.Getter;

@Getter
public enum ErrorCodeEnum {

    //user-management
    ER1000("ER1000", "User not found"),


    //external error

    //general error
    GN1000("GN1000", "General error");

    private String errorCode;

    private String error;

    ErrorCodeEnum(String errorCode, String error) {
        this.errorCode = errorCode;
        this.error = error;
    }

}
