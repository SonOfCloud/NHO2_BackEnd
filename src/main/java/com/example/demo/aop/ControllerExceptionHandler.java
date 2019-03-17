package com.example.demo.aop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {


    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String,Object> handlerUserNotExistException(Exception ex){
        log.error("Exception detail:{}", ex.getMessage());
        Map<String,Object> result=new HashMap<>();
        result.put("data", null);
        result.put("error", "Internal server error");
        return result;
    }
}
