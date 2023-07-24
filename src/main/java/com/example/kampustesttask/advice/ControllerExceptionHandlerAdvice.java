package com.example.kampustesttask.advice;

import com.example.kampustesttask.exception.CustomException;
import com.example.kampustesttask.exception.CustomExceptionHandler;
import com.example.kampustesttask.util.UtError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// обрабатываем исключения только из контроллеров, помеченных @CustomExceptionHandler
@RestControllerAdvice(annotations = CustomExceptionHandler.class)
public class ControllerExceptionHandlerAdvice {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Map<String, List<String>>> handleCustomException(Exception exception) {
        return ResponseEntity.badRequest().body(UtError.getMap(exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException exception) {
        List<String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> "Поле [" + fieldError.getField() + "] " + fieldError.getDefaultMessage())
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(UtError.getMap(errors));
    }

}
