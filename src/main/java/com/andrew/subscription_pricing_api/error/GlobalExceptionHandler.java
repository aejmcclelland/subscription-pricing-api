package com.andrew.subscription_pricing_api.error;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        String field = null;
        String errorMessage = "Validation failed";
        if (fieldError != null) {
            field = fieldError.getField();
            errorMessage = fieldError.getDefaultMessage();
        }

        return new ApiErrorResponse(
                "Validation failed",
                400,
                System.currentTimeMillis(),
                field,
                errorMessage);
    }

}
