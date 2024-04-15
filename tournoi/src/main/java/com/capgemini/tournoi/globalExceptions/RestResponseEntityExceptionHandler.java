package com.capgemini.tournoi.globalExceptions;

import com.capgemini.tournoi.error.*;
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
    @ExceptionHandler(PlayerExistInAnotherTeamException.class)
    public ResponseEntity<ErrorMessage> playerExistInAnotherTeamException(PlayerExistInAnotherTeamException playerExistInAnotherTeamException){

        return new ResponseEntity<>(new ErrorMessage
                (playerExistInAnotherTeamException.getMessage(), HttpStatus.EXPECTATION_FAILED)
                ,HttpStatus.EXPECTATION_FAILED);
    }
    @ExceptionHandler(ChangePlayersOfTeamDuringTournamentException.class)
    public ResponseEntity<ErrorMessage> changePlayersOfTeamDuringTournamentException
            (ChangePlayersOfTeamDuringTournamentException
                     changePlayersOfTeamDuringTournamentException){

        return new ResponseEntity<>(new ErrorMessage
                (changePlayersOfTeamDuringTournamentException.getMessage(), HttpStatus.FORBIDDEN)
                ,HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(TournamentAlreadyInProgressException.class)
    public ResponseEntity<ErrorMessage> tournamentAlreadyInProgressException
            (TournamentAlreadyInProgressException
                     tournamentAlreadyInProgressException){

        return new ResponseEntity<>(new ErrorMessage
                (tournamentAlreadyInProgressException.getMessage(), HttpStatus.FORBIDDEN)
                ,HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(TournamentDateException.class)
    public ResponseEntity<ErrorMessage> tournamentDateException
            (TournamentDateException
                     tournamentDateException){

        return new ResponseEntity<>(new ErrorMessage
                (tournamentDateException.getMessage(), HttpStatus.FORBIDDEN)
                ,HttpStatus.FORBIDDEN);
    }
}
