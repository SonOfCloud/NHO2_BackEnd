package com.example.demo.aop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
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


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Map<String, Object> handlerUserNotExistException(MethodArgumentNotValidException ex, HttpServletResponse response) {
        log.error("Exception detail:{}", ex);
        Map<String, Object> result = new HashMap<>();
        result.put("data", null);
        result.put("error", argumentValidErrorMsg(ex));
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return result;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public Map<String, Object> handlerUserNotExistException(HttpMessageNotReadableException ex, HttpServletResponse response) {
        log.error("Exception detail:{}", ex);
        Map<String, Object> result = new HashMap<>();
        result.put("data", null);
        result.put("error", "post请求body体不正确");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return result;
    }

    private String argumentValidErrorMsg(MethodArgumentNotValidException ex) {
        Iterator iterator = ex.getBindingResult().getAllErrors().iterator();

        StringBuilder errorMsg = new StringBuilder();
        while (iterator.hasNext()) {
            ObjectError error = (ObjectError) iterator.next();
            errorMsg.append("[").append(error.getDefaultMessage()).append("] ");
        }
        return errorMsg.toString();
    }
}
