package com.elema.common;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice(annotations = {RestController.class, Controller.class})// 指定Controller类型
public class GlobalExceptionHandler {

    /**
     * 用户名重复异常处理
     * @return Results<String>
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Results<String> exceptionHandler(SQLIntegrityConstraintViolationException ex){
        if (ex.getMessage().contains("Duplicate entry")){ // 是否包含异常打印信息
            String[] strings = ex.getMessage().split(" ");
            String msg = strings[2] + "已存在!";
            return Results.error(msg);
        }
        return Results.error("未知错误");
    }

    @ExceptionHandler(CustomException.class)
    public Results<String> exceptionHandler(CustomException ex){
        return Results.error(ex.getMessage());
    }
}
