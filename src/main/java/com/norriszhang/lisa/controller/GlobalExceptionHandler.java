package com.norriszhang.lisa.controller;

import com.norriszhang.lisa.dto.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiError> handleException(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        if (ex instanceof AccessDeniedException) {
            HttpStatus status = HttpStatus.FORBIDDEN;
            AccessDeniedException ade = (AccessDeniedException) ex;
            return handleAuthorizationException(ade, headers, status, request);
        } else {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            ApiError apiError = ApiError.builder().build();
            return handleExceptionInternal(ex, apiError, headers, status, request);
        }
    }

    private ResponseEntity<ApiError> handleAuthorizationException(AccessDeniedException ade, HttpHeaders headers, HttpStatus status, WebRequest request) {
        // get error info from ade and create ApiError instance
        ApiError apiError = ApiError.builder().errorMessage(ade.getMessage()).build();
        return handleExceptionInternal(ade, apiError, headers, status, request);
    }

    private ResponseEntity<ApiError> handleExceptionInternal(Exception ex, ApiError apiError, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);

        }
        return new ResponseEntity<>(apiError, headers, status);
    }
}
