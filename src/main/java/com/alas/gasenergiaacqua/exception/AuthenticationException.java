package com.alas.gasenergiaacqua.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AuthenticationException extends RuntimeException {

    final HttpStatus status = HttpStatus.UNAUTHORIZED;
    final String message;

    public AuthenticationException(String message) {
        this.message = message;
    }
}
