package com.infinity.common.exceptionHandling;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@Getter
public class CustomResponseException extends ResponseStatusException {

    private String errorCode;

    private String error;

    private HttpStatus httpStatus;

    private String href;

    private String methodType;

    private String timeStamp;

    private String traceId;

    private String spanId;


    public CustomResponseException(HttpServletRequest request,
                                   ErrorCodeEnum errorCodeEnum,
                                   HttpStatus httpStatus){
        super(httpStatus, errorCodeEnum.getError());
        this.errorCode = errorCodeEnum.getErrorCode();
        this.error =  errorCodeEnum.getError();
        this.httpStatus = httpStatus;
        this.href = request.getRequestURI();
        this.methodType = request.getMethod();
        this.timeStamp = String.valueOf(Instant.now());
    }

}