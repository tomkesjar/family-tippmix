package kt.tippmix.service;

import kt.tippmix.model.Game;
import kt.tippmix.model.GameResultUpdate;
import kt.tippmix.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class GameService {
    private GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<Game> getAll() {
        List<Game> games = (List) gameRepository.findAll();
        games.sort(Comparator.comparing(Game::getGameDate));
        return games;
    }

    public Optional<Game> getById(Long id) {
        return gameRepository.findById(id);
    }

    public List<Game> getUpcomingGames(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
        List<Game> upcomingGames = gameRepository.findAllAfterDate(dateTime);
        return upcomingGames;
    }

    public void save(Game newGame) {
        gameRepository.save(newGame);
    }

    public void saveAll(List<Game> games) {
        gameRepository.saveAll(games);
    }

    public void deleteGame(Long gameId) {
        gameRepository.deleteById(gameId);
    }

    public void updateResults(List<GameResultUpdate> updates) {
        updates.forEach(u ->
            gameRepository.findById(u.getGameId()).ifPresent(game -> {
                game.setHomeGoals(u.getHomeGoals());
                game.setAwayGoals(u.getAwayGoals());
                game.setWinner(u.getWinner());
                gameRepository.save(game);
            })
        );
    }
}
