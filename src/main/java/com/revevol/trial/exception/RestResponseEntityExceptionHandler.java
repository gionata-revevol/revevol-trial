package com.revevol.trial.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TrialRevevolException.class)
    public ResponseEntity<ErrorEntity> handleError(HttpServletRequest req, TrialRevevolException ex) {
        log.error("Error occurred: ", ex);
        final ErrorEntity errorEntity = new ErrorEntity(ex.getMessage(), req.getRequestURI());
        return ResponseEntity.status(ex.getStatusCode()).body(errorEntity);
    }

}