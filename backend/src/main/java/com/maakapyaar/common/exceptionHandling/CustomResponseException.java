package com.maakapyaar.common.exceptionHandling;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

public class CustomResponseException extends Exception{

    private String errorCode;

    private String error;

    private String httpStatus;

    private String href;

    private String methodType;

    private String timeStamp;


    public CustomResponseException(HttpServletRequest request,
                                   ErrorCodeEnum errorCodeEnum,
                                   String httpStatus){
        this.errorCode = errorCodeEnum.getErrorCode();
        this.error =  errorCodeEnum.getError();
        this.httpStatus = httpStatus;
        this.href = request.getRequestURI();
        this.methodType = request.getMethod();
        this.timeStamp = String.valueOf(Instant.now());
    }

}
