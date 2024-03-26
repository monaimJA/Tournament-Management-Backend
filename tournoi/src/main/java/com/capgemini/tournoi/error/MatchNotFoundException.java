package com.capgemini.tournoi.error;

public class MatchNotFoundException extends Exception {
    public MatchNotFoundException() {
    }

    public MatchNotFoundException(String message) {
        super(message);
    }

    public MatchNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MatchNotFoundException(Throwable cause) {
        super(cause);
    }

    public MatchNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

