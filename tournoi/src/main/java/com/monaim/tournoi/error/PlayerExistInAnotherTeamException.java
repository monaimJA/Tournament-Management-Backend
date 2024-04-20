package com.monaim.tournoi.error;

public class PlayerExistInAnotherTeamException extends Exception{
    public PlayerExistInAnotherTeamException() {
        super();
    }

    public PlayerExistInAnotherTeamException(String message) {
        super(message);
    }

    public PlayerExistInAnotherTeamException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlayerExistInAnotherTeamException(Throwable cause) {
        super(cause);
    }

    protected PlayerExistInAnotherTeamException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
