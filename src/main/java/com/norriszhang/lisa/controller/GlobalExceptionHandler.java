package com.norriszhang.lisa.controller;

import com.norriszhang.lisa.dto.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

import javax.security.auth.login.LoginException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiError> handleException(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        if (ex instanceof AccessDeniedException) {
            return handleException(ex, headers, HttpStatus.FORBIDDEN, request);
        } else if (ex instanceof AuthenticationCredentialsNotFoundException) {
            return handleException(ex, headers, HttpStatus.UNAUTHORIZED, request);
        } else if (ex instanceof LoginException) {
            return handleException(ex, headers, HttpStatus.UNAUTHORIZED, request);
        } else {
            return handleException(ex, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
        }
    }

    private ResponseEntity<ApiError> handleException(Exception ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }
        return new ResponseEntity<>(ApiError.builder().errorMessage(ex.getMessage()).build(), headers, status);
    }
}
