package kt.tippmix.model;

import java.time.LocalDateTime;

public record GameInfo(
        LocalDateTime gameDate,
        String homeTeam,
        String awayTeam,
        Integer gameHomeGoals,
        Integer gameAwayGoals,
        Integer gameWinner
) {}
