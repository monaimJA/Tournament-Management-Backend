package com.monaim.tournoi.error;

public class ScoreNotFoundException extends Exception{
    public ScoreNotFoundException() {
    }

    public ScoreNotFoundException(String message) {
        super(message);
    }

    public ScoreNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ScoreNotFoundException(Throwable cause) {
        super(cause);
    }

    public ScoreNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
