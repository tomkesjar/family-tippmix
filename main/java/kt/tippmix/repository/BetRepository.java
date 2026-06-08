package kt.tippmix.repository;

import kt.tippmix.model.AllBetsRow;
import kt.tippmix.model.Bet;
import kt.tippmix.model.BetHistoryRow;
import kt.tippmix.model.DetailedDataRow;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface BetRepository extends CrudRepository<Bet, Long> {

//    @Query("SELECT g FROM Game g WHERE g.gameDate >= :dateTime")
//    List<Tip> findAllAfterDate(LocalDateTime dateTime);

    @Query("SELECT b FROM Bet b WHERE b.playerId = :playerId")
    List<Bet> findAllByUser(Long playerId);

    @Query("SELECT b FROM Bet b WHERE b.matchId = :matchId")
    List<Bet> findAllGameId(Long matchId);

    @Query("""
           SELECT new kt.tippmix.model.BetHistoryRow(
               b.matchId, g.gameDate,
               g.homeTeam, g.awayTeam,
               g.homeGoals, g.awayGoals, g.winner,
               b.homeGoals, b.awayGoals, b.winner, b.point,
               g.knockout)
           FROM Bet b, Game g
           WHERE b.matchId = g.id AND b.playerId = :playerId
           ORDER BY g.gameDate
           """)
    List<BetHistoryRow> findHistoryByPlayerId(@Param("playerId") Long playerId);

//    @Query("SELECT t FROM Tip t WHERE t.username = :userName AND t.gameDate >= :dateTime")
//    List<Tip> findUpcomingByUser(String userName, LocalDateTime dateTime);

    @Query("""
           SELECT new kt.tippmix.model.AllBetsRow(
               g.gameDate, g.homeTeam, g.awayTeam,
               g.homeGoals, g.awayGoals, g.winner,
               g.knockout,
               b.id, b.matchId, b.playerId,
               b.homeGoals, b.awayGoals, b.winner, b.point, b.exact,
               u.secretName, u.secretFileName)
           FROM Bet b, Game g, User u
           WHERE b.matchId = g.id AND u.id = b.playerId
             AND b.point IS NOT NULL AND b.exact IS NOT NULL
           ORDER BY g.gameDate, u.secretName
           """)
    List<AllBetsRow> findAllEvaluated();

    @Query("""
           SELECT new kt.tippmix.model.DetailedDataRow(
               u.id, u.userName, u.other, u.secretName, u.favouriteNation, u.goalScorerNationality, u.mostGoals,
               g.id, g.gameDate, g.homeTeam, g.awayTeam, g.homeGoals, g.awayGoals, g.winner,
               b.id, b.homeGoals, b.awayGoals, b.winner, b.point, b.exact,
               p.matchPoint, p.winnerPoint, p.goalPoint, p.mostGoalPoint, p.bonusPoint, p.total)
           FROM Bet b, Game g, User u, Point p
           WHERE b.matchId = g.id AND u.id = b.playerId AND p.playerId = u.id
           ORDER BY u.secretName, g.gameDate
           """)
    List<DetailedDataRow> findDetailedData();

    @Modifying
    @Transactional
    @Query("DELETE FROM Bet b WHERE b.playerId = :playerId AND b.matchId = :matchId")
    void deletePreviousBets(@Param("playerId") Long playerId, @Param("matchId") Long matchId);




}
