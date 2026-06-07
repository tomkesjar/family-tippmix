package kt.tippmix.repository;

import kt.tippmix.model.Point;
import kt.tippmix.model.RankingResponse;
import kt.tippmix.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PointRepository extends CrudRepository<Point, Long> {

    @Query("SELECT p FROM Point p WHERE p.playerId = :playerId")
    Optional<Point> findByUser(Long playerId);

    @Query("""
           SELECT new kt.tippmix.model.RankingResponse(
               u.secretFileName, u.secretName,
               p.matchPoint, p.winnerPoint, p.goalPoint, p.mostGoalPoint, p.bonusPoint, p.total,
               cast((SELECT count(b) FROM Bet b WHERE b.playerId = u.id AND b.exact = true) as Integer),
               u.favouriteNation, u.goalScorerNationality, u.mostGoals)
           FROM User u, Point p
           WHERE u.id = p.playerId AND u.role = :role
           ORDER BY p.total DESC,
                    (SELECT count(b) FROM Bet b WHERE b.playerId = u.id AND b.exact = true) DESC
           """)
    List<RankingResponse> getRanking(@Param("role") User.Role role);

}
