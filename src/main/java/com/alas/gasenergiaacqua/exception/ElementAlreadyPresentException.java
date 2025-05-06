package com.alas.gasenergiaacqua.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ElementAlreadyPresentException extends RuntimeException{
    final HttpStatus status = HttpStatus.CONFLICT;
    final String message;

    public ElementAlreadyPresentException(String message) {
        this.message = message;
    }
}
