package com.sky.handler;

import com.sky.constant.MessageConstant;
import com.sky.exception.BaseException;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result handleAllExceptions(Exception ex) {
        log.error("未处理的异常：{}", ex.getMessage());
        return Result.error("服务器内部错误，请稍后重试");
    }


    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler(BaseException.class)
    public Result exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result SQLIntegrityConstraintViolationExceptionHandler(SQLIntegrityConstraintViolationException sqlIntegrityConstraintViolationException){
        log.error("异常信息：{}", sqlIntegrityConstraintViolationException.getMessage());
        String message = sqlIntegrityConstraintViolationException.getMessage();
        if (message.contains("Duplicate entry")){
            String[] s = message.split(" ");
            String username = s[2];
            message=username+ MessageConstant.ALREADY_EXISTS;
            return Result.error(message);
        }
        return Result.error(MessageConstant.UNKNOWN_ERROR);
    }

}
