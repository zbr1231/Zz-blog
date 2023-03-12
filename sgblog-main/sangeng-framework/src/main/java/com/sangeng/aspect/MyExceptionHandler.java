package com.sangeng.aspect;

import com.sangeng.domain.ResponseResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//@ControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseResult handler(Exception exception){
        String message = exception.getMessage();
        System.out.println(message);
        return ResponseResult.errorResult(201,message);
    }
}
