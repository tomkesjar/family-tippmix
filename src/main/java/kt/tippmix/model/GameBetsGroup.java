package kt.tippmix.model;

import java.util.List;

/** Controller-level response: one game paired with every player's bet for that game. */
public record GameBetsGroup(
        GameInfo           game,
        List<PlayerBetPair> playerBets
) {}
