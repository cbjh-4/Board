package org.zerock.b01.controller.advice;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.util.HashMap;
import java.util.Map;

//JSON 타입 @Vaild BindingResult 세부 설정 
@RestControllerAdvice
@Log4j2
public class CustomRestAdvice {
    @ExceptionHandler(BindException.class)
    public ResponseEntity<Map<String, String>> handleBindException(BindException e) {
        log.error(e);
        Map<String, String> errorMap = new HashMap<>();
        if(e.hasErrors()){
            BindingResult bindingResult = e.getBindingResult();
            
            bindingResult.getFieldErrors().forEach(fieldError -> {
                errorMap.put(fieldError.getField(), fieldError.getCode());
            });
        }
        //에러가 있을때 CustomRestAdvice가 호출되므로 예외가 있는지 체크할 필요가 없다.
        return ResponseEntity.badRequest().body(errorMap);
    }
}
