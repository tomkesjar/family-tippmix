package kt.tippmix.repository;

import kt.tippmix.model.Game;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class GameRepository {

    private Map<String, Game> matchRepository;

    public GameRepository(Map<String, Game> matchRepository) {
        this.matchRepository = matchRepository;
    }

    public List<Game> findAll() {
        return matchRepository.values().stream().toList();
    }

    public void save(Game game) {
        matchRepository.put(game.getMatchDate().toString(), game);
    }
}
