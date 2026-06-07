package kt.tippmix.model;

import java.time.LocalDateTime;

/**
 * Flat projection that joins the player, game, bet and point tables.
 * One row per bet — player/point fields repeat across a player's bets.
 */
public record DetailedDataRow(
        // ── Player ──────────────────────────────────────────────
        long   userId,
        String userName,
        String other,
        String secretName,
        String favouriteNation,
        String goalScorerNationality,
        String mostGoals,

        // ── Game ────────────────────────────────────────────────
        long          gameId,
        LocalDateTime gameDate,
        String        homeTeam,
        String        awayTeam,
        Integer       gameHomeGoals,
        Integer       gameAwayGoals,
        Integer       gameWinner,

        // ── Bet ─────────────────────────────────────────────────
        long    betId,
        Integer betHomeGoals,
        Integer betAwayGoals,
        Integer betWinner,
        Integer betPoint,
        Boolean betExact,

        // ── Point (per-player totals) ────────────────────────────
        Integer matchPoint,
        Integer winnerPoint,
        Integer goalPoint,
        Integer mostGoalPoint,
        Integer bonusPoint,
        Integer totalPoint
) {}
