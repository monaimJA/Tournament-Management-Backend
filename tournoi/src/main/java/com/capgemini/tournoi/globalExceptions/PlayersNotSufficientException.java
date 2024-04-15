package com.capgemini.tournoi.globalExceptions;

public class PlayersNotSufficientException extends Exception {
    public PlayersNotSufficientException(String message){
        super(message);
    }
}
