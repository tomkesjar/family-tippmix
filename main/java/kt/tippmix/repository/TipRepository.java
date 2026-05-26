package kt.tippmix.repository;

import kt.tippmix.model.Game;
import kt.tippmix.model.Tip;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;


public interface TipRepository extends CrudRepository<Tip, Long> {

    @Query("SELECT g FROM Game g WHERE g.gameDate >= :dateTime")
    List<Tip> findAllAfterDate(LocalDateTime dateTime);

    @Query("SELECT t FROM Tip t WHERE t.username = :userName")
    List<Tip> findAllByUser(String userName);

    @Query("SELECT t FROM Tip t WHERE t.username = :userName AND t.gameDate >= :dateTime")
    List<Tip> findUpcomingByUser(String userName, LocalDateTime dateTime);




}
