package kt.tippmix.model;

public record ModifyBetRequest(
        long playerId,
        long matchId,
        Integer homeGoals,
        Integer awayGoals,
        Integer winner
) {}
