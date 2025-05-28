package com.alas.gasenergiaacqua.exception;

import com.alas.gasenergiaacqua.dto.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Exception handler for NoSuchElementException
     * @param ex NoSuchElementException
     * @return ResponseEntity with error body and appropriate HTTP status
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ResponseMessage> handleNoSuchElementException(NoSuchElementException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ResponseMessage responseMessage = ResponseMessage.builder()
                .timestamp(Instant.now())
                .message(ex.getMessage())
                .build();
        System.err.println("NoSuchElementException: " + ex.getLocalizedMessage());
        return new ResponseEntity<>(responseMessage, status);
    }

    /**
     * Exception handler for ElementAlreadyPresentException.
     * @param ex ElementAlreadyPresentException
     * @return ResponseEntity with error body and appropriate HTTP status
     */
    @ExceptionHandler(ElementAlreadyPresentException.class)
    public ResponseEntity<ResponseMessage> handleElementAlreadyPresent(ElementAlreadyPresentException ex) {
        HttpStatus status = HttpStatus.CONFLICT;
        ResponseMessage responseMessage = ResponseMessage.builder()
                .timestamp(Instant.now())
                .message(ex.getMessage())
                .build();
        System.err.println("ElementAlreadyPresentException: " + ex.getLocalizedMessage());
        return new ResponseEntity<>(responseMessage, status);
    }

    /**
     * Exception handler for MethodArgumentNotValidException.
     * It is thrown every time an argument annotated with {@code @Validated}(or one that gets validated automatically) tries to get validated but fails.
     *
     * @param ex MethodArgumentNotValidException
     * @return ResponseEntity with error body and appropriate HTTP status
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseMessage> handleCustomException(MethodArgumentNotValidException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        List<String> errorMessages = List.of();

        if (ex.hasFieldErrors()) {
            errorMessages = ex.getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .map(fieldError -> String.format("Field '%s': %s (rejected value: '%s') ",
                            fieldError.getField(),
                            fieldError.getDefaultMessage(),
                            fieldError.getRejectedValue()))
                    .toList();
        } else if (ex.hasGlobalErrors()) {
            errorMessages = ex.getBindingResult()
                    .getAllErrors()
                    .stream()
                    .map(error -> String.format("Object '%s': %s",
                            error.getObjectName(),
                            error.getDefaultMessage()))
                    .toList();
        }

        ResponseMessage responseMessage = ResponseMessage.builder()
                .timestamp(Instant.now())
                .message(errorMessages.toString())
                .build();
        System.err.println("MethodArgumentNotValidException: " + ex.getLocalizedMessage());
        return ResponseEntity.status(status).body(responseMessage);
    }
}
