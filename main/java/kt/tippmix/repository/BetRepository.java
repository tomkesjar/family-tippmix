package kt.tippmix.repository;

import kt.tippmix.model.Bet;
import kt.tippmix.model.BetHistoryRow;
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

    @Query("""
           SELECT new kt.tippmix.model.BetHistoryRow(
               b.matchId, g.gameDate,
               g.homeTeam, g.awayTeam,
               g.homeGoals, g.awayGoals,
               b.homeGoals, b.awayGoals, b.winner, b.point)
           FROM Bet b, Game g
           WHERE b.matchId = g.id AND b.playerId = :playerId
           ORDER BY g.gameDate
           """)
    List<BetHistoryRow> findHistoryByPlayerId(@Param("playerId") Long playerId);

//    @Query("SELECT t FROM Tip t WHERE t.username = :userName AND t.gameDate >= :dateTime")
//    List<Tip> findUpcomingByUser(String userName, LocalDateTime dateTime);

    @Modifying
    @Transactional
    @Query("DELETE FROM Bet b WHERE b.playerId = :playerId AND b.matchId = :matchId")
    void deletePreviousBets(@Param("playerId") Long playerId, @Param("matchId") Long matchId);




}
