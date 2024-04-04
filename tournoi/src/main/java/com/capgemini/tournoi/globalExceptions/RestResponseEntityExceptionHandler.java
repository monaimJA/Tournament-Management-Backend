package com.capgemini.tournoi.globalExceptions;

import com.capgemini.tournoi.error.ErrorMessage;
import com.capgemini.tournoi.error.PlayerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseStatus
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(PlayerNotFoundException.class)
    public ResponseEntity<ErrorMessage> playerNotFoundException(PlayerNotFoundException playerNotFoundException){

        return new ResponseEntity<>(new ErrorMessage
                (playerNotFoundException.getMessage(), HttpStatus.NOT_FOUND)
                ,HttpStatus.NOT_FOUND);
    }
}
