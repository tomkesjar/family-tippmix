package kt.tippmix.repository;

import kt.tippmix.model.Game;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;


public interface GameRepository extends CrudRepository<Game, Long> {

//    @Query("SELECT g FROM Game g")
//    List<Game> findAll();
//
//    @Query("SELECT g FROM Game g WHERE g.id = :id")
//    @Query(value = "SELECT * FROM game WHERE id = :id", nativeQuery = true)
//    Game findById(@Param("id") int id);

//    @Modifying
//    @Query(value = "INSERT INTO game (hometeam, awayteam, date, homegoals, awaygoals, winner) " +
//            "VALUES (:#{#game.homeTeam}, :#{#game.awayTeam}, :#{#game.gameDate}, " +
//            ":#{#game.homeGoals}, :#{#game.awayGoals}, :#{#game.winner})",
//            nativeQuery = true)
//    void insertGame(@Param("game") Game game);

    @Query("SELECT g FROM Game g WHERE g.gameDate > :dateTime")
    List<Game> findAllAfterDate(LocalDateTime dateTime);
}
