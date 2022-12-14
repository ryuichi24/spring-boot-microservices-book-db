package com.juniordevmind.shared.errors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = { NotFoundException.class, BadRequestException.class })
    protected ResponseEntity<Object> handleHttpException(HttpException ex, WebRequest request) {
        List<String> errorMessages = Arrays.asList(ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(errorMessages, ex.getHttpStatus());
        return this.handleExceptionInternal(ex, errorResponse, new HttpHeaders(), ex.getHttpStatus(),
                request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse errorResponse = _buildErrorResponse(ex.getBindingResult());
        return this.handleExceptionInternal(ex, errorResponse, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        return this.handleExceptionInternal(ex,
                new ErrorResponse(new ArrayList<>(Arrays.asList(ex.getMessage())), HttpStatus.BAD_REQUEST), headers,
                HttpStatus.BAD_REQUEST, request);
    }

    private ErrorResponse _buildErrorResponse(BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        List<String> errorMessages = new ArrayList<>();
        for (final FieldError error : fieldErrors) {
            errorMessages.add(error.getField() + ": " + error.getDefaultMessage());
        }
        ErrorResponse errorResponse = new ErrorResponse(errorMessages, HttpStatus.BAD_REQUEST);
        return errorResponse;
    }

}
