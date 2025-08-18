package com.semicolon.ecommerceTask.infrastructure.adapter.controllers;

import com.semicolon.ecommerceTask.domain.exception.AdminNotFoundException;
import com.semicolon.ecommerceTask.domain.exception.ValidationException;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.responses.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleValidationException(ValidationException ex) {
        return ErrorResponseDto.builder()
            .message("Validation Error: " + ex.getMessage())
            .status(HttpStatus.BAD_REQUEST.value())
            .timestamp(LocalDateTime.now().toString())
            .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDto handleGenericException(Exception ex) {
        return ErrorResponseDto.builder()
            .message("An unexpected error occurred. Please try again later.")
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .timestamp(LocalDateTime.now().toString())
            .build();
    }

    @ExceptionHandler(AdminNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleAdminException(AdminNotFoundException ex) {
        HttpStatus status;
        if (ex.getMessage().contains("not found")) {status = HttpStatus.NOT_FOUND;
        } else if (ex.getMessage().contains("already exists")) {status = HttpStatus.CONFLICT;
        } else {status = HttpStatus.BAD_REQUEST;}
        log.warn("Admin error occurred with status {}: {}", status, ex.getMessage());
        ErrorResponseDto errorResponse = ErrorResponseDto.builder()
            .message("Admin Error: " + ex.getMessage())
            .status(status.value())
            .timestamp(LocalDateTime.now().toString())
            .build();
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<String> handleOptimisticLockingFailure(ObjectOptimisticLockingFailureException ex) {
        log.error("Optimistic locking failure: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body("The record was modified by another user. Please try again.");
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> handleGenericException(Exception ex) {
//        log.error("An unexpected error occurred: ", ex);
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body("An unexpected error occurred. Please contact support.");
//    }
}

