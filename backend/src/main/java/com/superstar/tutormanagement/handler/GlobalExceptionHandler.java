package com.superstar.tutormanagement.handler;

import com.superstar.tutormanagement.enums.ResultCodeEnum;
import com.superstar.tutormanagement.exception.TutorManagementException;
import com.superstar.tutormanagement.utils.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResult methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        // 然后提取错误提示信息进行返回
        String errorMsg = objectError.getDefaultMessage();
        return CommonResult.fail(ResultCodeEnum.ILLEGAL_PARAM, errorMsg);
    }

    @ExceptionHandler(TutorManagementException.class)
    public CommonResult contractExceptionHandler(TutorManagementException e) {
        log.error(e.getErrorMsg());
        return CommonResult.fail(e.getErrorCode(), e.getErrorMsg());
    }
}