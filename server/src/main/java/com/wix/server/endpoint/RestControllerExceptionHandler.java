package com.wix.server.endpoint;

import com.wix.server.exception.DuplicateEntityException;
import com.wix.server.exception.UnknownEntityException;
import com.wix.server.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by racastur on 25-03-2015.
 */
@ControllerAdvice
public class RestControllerExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<RestErrorMessage> handleExceptions(Exception e) {

        RestErrorMessage body = new RestErrorMessage();
        body.setErrorCode("1");
        body.setErrorMessage(e.getMessage());

        if (e instanceof ValidationException) {
            return new ResponseEntity<RestErrorMessage>(body, HttpStatus.BAD_REQUEST);
        } else if (e instanceof DuplicateEntityException) {
            return new ResponseEntity<RestErrorMessage>(body, HttpStatus.BAD_REQUEST);
        } else if (e instanceof UnknownEntityException) {
            return new ResponseEntity<RestErrorMessage>(body, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<RestErrorMessage>(body, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
