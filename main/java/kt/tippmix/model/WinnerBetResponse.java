package kt.tippmix.model;

import org.springframework.data.util.Pair;

public record WinnerBetResponse(WinnerBetStatus status, String errorMessage, Pair<String, String> countriesSelected) {}
