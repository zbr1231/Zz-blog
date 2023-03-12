package com.sangeng.handler.exception;

import com.sangeng.domain.ResponseResult;
import com.sangeng.enums.AppHttpCodeEnum;
import com.sangeng.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Optional;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SystemException.class)
    public ResponseResult systemExceptionHandler(SystemException e){
        //打印异常信息
        log.error("出现了异常！ {}",e);
        //从异常对象中获取提示信息封装返回
        return ResponseResult.errorResult(e.getCode(),e.getMsg());
    }

    //捕获器1
    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    public ResponseResult handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        if (log.isErrorEnabled()) {
            log.error(ex.getMessage(), ex);
        }
        return ResponseResult.errorResult(402,String.format("缺少必要参数[%s]", ex.getParameterName()));
    }
    //捕获器2
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseResult handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        if (log.isErrorEnabled()) {
            log.error(ex.getMessage(), ex);
        }
        BindingResult result = ex.getBindingResult();
        FieldError error = result.getFieldError();
        return ResponseResult.errorResult(402, null == error ? "捕获异常" : error.getDefaultMessage());
    }
    //捕获器3
    @ExceptionHandler(value = {BindException.class})
    public ResponseResult handleBindException(BindException ex) {
        if (log.isErrorEnabled()) {
            log.error(ex.getMessage(), ex);
        }
        BindingResult result = ex.getBindingResult();
        FieldError error = result.getFieldError();
        return ResponseResult.errorResult(402, null == error ? "捕获异常" : error.getDefaultMessage());
    }
    //捕获器4
    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseResult handleConstraintViolationException(ConstraintViolationException ex) {
        if (log.isErrorEnabled()) {
            log.error(ex.getMessage(), ex);
        }
        Optional<ConstraintViolation<?>> first = ex.getConstraintViolations().stream().findFirst();
        return ResponseResult.errorResult(402, first.isPresent() ? first.get().getMessage() : "捕获异常");
    }

    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandler(Exception e){
        //打印异常信息
        log.error("出现了异常！ {}",e);
        //从异常对象中获取提示信息封装返回
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),e.getMessage());
    }
//    //其他所有异常捕获器
//    @ExceptionHandler(Exception.class)
//    public ResponseResult otherErrorDispose(Exception e) {
//        // 打印错误日志
//        log.error("错误代码({}),错误信息({})", 402, e.getMessage());
//        e.printStackTrace();
//        return ResponseResult.errorResult(402, e.getMessage());
//    }

}
