package com.capgemini.tournoi.error;

public class ChangePlayersOfTeamDuringTournamentException extends Exception{
    public ChangePlayersOfTeamDuringTournamentException() {
        super();
    }

    public ChangePlayersOfTeamDuringTournamentException(String message) {
        super(message);
    }

    public ChangePlayersOfTeamDuringTournamentException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChangePlayersOfTeamDuringTournamentException(Throwable cause) {
        super(cause);
    }

    protected ChangePlayersOfTeamDuringTournamentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
