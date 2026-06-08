package kt.tippmix.service;

import kt.tippmix.model.*;
import kt.tippmix.repository.BetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BetService {

    private BetRepository betRepository;

    public BetService(@Autowired BetRepository betRepository) {
        this.betRepository = betRepository;
    }

    public List<Bet> getAll() {
        List<Bet> tipList = (List) betRepository.findAll();
        return tipList;
    }

    /**
     * Groups evaluated bets as Map&lt;GameInfo, Map&lt;PlayerInfo, BetInfo&gt;&gt;.
     * Insertion order matches the query ordering (game date, then player secret name).
     */
    public Map<GameInfo, Map<PlayerInfo, BetInfo>> getAllEvaluated() {
        List<AllBetsRow> rows = betRepository.findAllEvaluated();
        Map<GameInfo, Map<PlayerInfo, BetInfo>> grouped = new LinkedHashMap<>();

        for (AllBetsRow row : rows) {
            GameInfo  game   = new GameInfo(row.getGameDate(), row.getHomeTeam(), row.getAwayTeam(),
                                            row.getGameHomeGoals(), row.getGameAwayGoals(), row.getGameWinner(),
                                            row.isKnockout());
            PlayerInfo player = new PlayerInfo(row.getSecretName(), row.getSecretFileName());
            BetInfo    bet    = new BetInfo(row.getBetId(), row.getMatchId(), row.getPlayerId(),
                                            row.getBetHomeGoals(), row.getBetAwayGoals(), row.getBetWinner(),
                                            row.getPoint(), row.getExact());

            grouped.computeIfAbsent(game, k -> new LinkedHashMap<>()).put(player, bet);
        }

        return grouped;
    }

    public List<Bet> getAllByGameId(Long gameId) {
        List<Bet> tipList = (List) betRepository.findAllGameId(gameId);
        return tipList;
    }

    public List<Bet> getAllByPlayerId(Long playerId) {
        return betRepository.findAllByUser(playerId);
    }

    public List<BetHistoryRow> getHistoryByPlayerId(Long playerId) {
        return betRepository.findHistoryByPlayerId(playerId);
    }

//    public List<Tip> getByDate(String date) {
//        LocalDateTime dateTime = LocalDateTime.parse(date);
//        return tipRepository.findAllAfterDate(dateTime);
//    }
//
//    public List<Tip> getByUser(String user) {
//        return tipRepository.findAllByUser(user);
//    }

    public Bet save(Bet newBet) {
        return betRepository.save(newBet);
    }

    public void clearPreviousBets(Bet newBet) {
        betRepository.deletePreviousBets(newBet.getPlayerId(), newBet.getMatchId());
    }

    /**
     * Inserts a new bet if none exists for the given player+match, otherwise updates
     * homeGoals, awayGoals and winner on the existing row. Point and exact are left untouched.
     */
    public Bet upsertBet(long matchId, long playerId, Integer homeGoals, Integer awayGoals, Integer winner) {
        Optional<Bet> existing = betRepository.findByMatchIdAndPlayerId(matchId, playerId);
        Bet bet = existing.orElseGet(Bet::new);
        bet.setMatchId(matchId);
        bet.setPlayerId(playerId);
        bet.setHomeGoals(homeGoals);
        bet.setAwayGoals(awayGoals);
        bet.setWinner(winner);
        return betRepository.save(bet);
    }

    public void deleteBet(Bet bet) {
        betRepository.delete(bet);
    }


}
