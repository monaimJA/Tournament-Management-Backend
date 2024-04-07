package com.capgemini.tournoi.error;

public class TournamentAlreadyInProgressException extends Exception{
    public TournamentAlreadyInProgressException() {
        super();
    }

    public TournamentAlreadyInProgressException(String message) {
        super(message);
    }

    public TournamentAlreadyInProgressException(String message, Throwable cause) {
        super(message, cause);
    }

    public TournamentAlreadyInProgressException(Throwable cause) {
        super(cause);
    }

    protected TournamentAlreadyInProgressException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
