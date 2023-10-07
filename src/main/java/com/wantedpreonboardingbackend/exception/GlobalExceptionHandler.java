package com.wantedpreonboardingbackend.exception;

import com.wantedpreonboardingbackend.dto.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataNotFoundException.class)
    private Result<?> dataNotFound(DataNotFoundException e) {
        return new Result<>(e.getMessage(), null);
    }

}
