package com.lj.stock.exceptionhandler;

import com.lj.stock.exception.MethodLimitException;
import com.lj.stock.model.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodLimitException.class)
    public Result handlerMethodLimitException(MethodLimitException methodLimitException){
        methodLimitException.printStackTrace();
        return Result.fail().message(methodLimitException.getMessage()).code(methodLimitException.getErrorCode());
    }

    @ExceptionHandler(Exception.class)
    public Result handlerMethodLimitException(Exception exception){
        exception.printStackTrace();
        return Result.fail().message("出错了,请稍后再试!");
    }

}
