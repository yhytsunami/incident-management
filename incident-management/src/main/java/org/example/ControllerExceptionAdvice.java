package org.example;

import jakarta.validation.ValidationException;
import lombok.extern.log4j.Log4j2;
import org.example.config.BizException;
import org.example.entity.ResEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Log4j2
@ControllerAdvice
public class ControllerExceptionAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Object handleValidationException(MethodArgumentNotValidException e){
        log.error("系统异常",e);
        ResEntity res = new ResEntity();
        res.setCode("500");
        res.setMessage("系统内部异常");
        ResponseEntity responseEntity = new ResponseEntity<>(res,HttpStatus.INTERNAL_SERVER_ERROR);
        return res;
    }

    @ExceptionHandler(BizException.class)
    @ResponseBody
    public Object handleBizException(Exception e){
        log.error("系统异常",e);
        ResEntity res = new ResEntity();
        res.setCode("500");
        res.setMessage("系统内部异常");
        ResponseEntity responseEntity = new ResponseEntity<>(res,HttpStatus.INTERNAL_SERVER_ERROR);
        return res;
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handleException(Exception e){
        log.error("系统异常",e);
        ResEntity res = new ResEntity();
        res.setCode("500");
        String message = e.getMessage();
        res.setMessage(message);
        ResponseEntity responseEntity = new ResponseEntity<>(res,HttpStatus.INTERNAL_SERVER_ERROR);
        return res;
    }

}
