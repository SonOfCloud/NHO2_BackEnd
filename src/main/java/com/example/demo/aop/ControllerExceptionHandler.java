package com.example.demo.aop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Map<String,Object> handlerUserNotExistException(Exception ex, HttpServletResponse response){
        log.error("Exception detail:{}", ex);
        Map<String,Object> result=new HashMap<>();
        if(ex instanceof MethodArgumentNotValidException){
            result.put("data", null);
            result.put("error", argumentValidErrorMsg((MethodArgumentNotValidException) ex));
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return result;
        }
        result.put("data", null);
        result.put("error", "Internal server error");
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return result;
    }

    private String argumentValidErrorMsg(MethodArgumentNotValidException ex){
        Iterator iterator = ex.getBindingResult().getAllErrors().iterator();

        StringBuilder errorMsg = new StringBuilder();
        while(iterator.hasNext()) {
            ObjectError error = (ObjectError)iterator.next();
            errorMsg.append("[").append(error.getDefaultMessage()).append("] ");
        }
        return errorMsg.toString();
    }
}
