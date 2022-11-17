package com.twms.wms.exceptions;

import com.twms.wms.dtos.InvalidArgumentDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class InvalidArgumentExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<InvalidArgumentDTO>> handleMethodArgumetNotValid(MethodArgumentNotValidException e) {

        List<InvalidArgumentDTO> errorInformation = new ArrayList<>();

        e.getBindingResult().getFieldErrors().forEach(
                error -> {
                    String field = error.getField();
                    String feedbackMessage = error.getDefaultMessage();
                    String providedValue = (String) error.getRejectedValue();
                    errorInformation.add(new InvalidArgumentDTO(field, feedbackMessage, providedValue));
                }
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorInformation);
    }
}
