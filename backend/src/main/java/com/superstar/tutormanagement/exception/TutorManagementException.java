package com.superstar.tutormanagement.exception;

import com.superstar.tutormanagement.enums.ResultCodeEnum;
import lombok.Getter;

/**
 * 异常类（异常码，异常信息）
 */
@Getter
public class TutorManagementException extends RuntimeException{
    private final String errorCode;

    private final String errorMsg;

    public TutorManagementException(ResultCodeEnum resultCodeEnum) {
        this.errorCode = resultCodeEnum.getCode();
        this.errorMsg = resultCodeEnum.getMessage();
    }
}