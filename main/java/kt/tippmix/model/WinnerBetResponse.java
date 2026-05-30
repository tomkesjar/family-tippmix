package kt.tippmix.model;

import org.springframework.data.util.Pair;

public record WinnerBetResponse(WinnerBetStatus status, String errorMessage, String winnerCountry, String goalScorer, String mostGoal ) {}
