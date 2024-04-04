package com.capgemini.tournoi.globalExceptions;

public class MaximumPlayersLimitException extends Exception {
    public MaximumPlayersLimitException(String message){
        super(message);
    }

}
