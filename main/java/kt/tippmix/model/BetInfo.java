package kt.tippmix.model;

public record BetInfo(
        long    betId,
        long    matchId,
        long    playerId,
        Integer betHomeGoals,
        Integer betAwayGoals,
        Integer betWinner,
        Integer point,
        Boolean exact
) {}
