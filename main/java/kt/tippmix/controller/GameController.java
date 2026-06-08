package kt.tippmix.controller;

import kt.tippmix.model.Bet;
import kt.tippmix.model.DateRequest;
import kt.tippmix.model.Game;
import kt.tippmix.model.GameResultUpdate;
import kt.tippmix.service.BetService;
import kt.tippmix.service.GameService;
import kt.tippmix.service.PointCalculator;
import kt.tippmix.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/game")
@CrossOrigin(origins = "http://localhost:5173")
public class GameController {

    private GameService gameService;
    private PointCalculator pointCalculator;

    public GameController(@Autowired GameService gameService, PointCalculator pointCalculator) {
        this.gameService = gameService;
        this.pointCalculator = pointCalculator;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Game>> getAll() {
        List<Game> gameList = (List) gameService.getAll();
        return ResponseEntity.ok().body(gameList);
    }

    @GetMapping("/id")
    public ResponseEntity<Game> getById(@RequestBody Long id) {
        return gameService.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/fromdate")
    public ResponseEntity<List<Game>> getAllAfterDate(@RequestBody DateRequest dateRequest) {
        List<Game> upcomingGames = gameService.getUpcomingGames(dateRequest.getDate());
        upcomingGames.sort(Comparator.comparing(Game::getGameDate));
        return ResponseEntity.ok().body(upcomingGames);
    }

    /** Save a single new game. Admin only. */
    @PostMapping("/newgame")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> save(@RequestBody Game newGame) {
        gameService.save(newGame);
        return ResponseEntity.ok("Game saved");
    }

    /** Save a batch of new games. Admin only. */
    @PostMapping("/newgames")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> saveAll(@RequestBody List<Game> newGames) {
        gameService.saveAll(newGames);
        return ResponseEntity.ok("Saved " + newGames.size() + " games");
    }

    /** Update homeGoals / awayGoals / winner for existing games. Admin only. */
    @PostMapping("/gameresults")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> saveGameResults(@RequestBody List<GameResultUpdate> updates) {
        gameService.updateResults(updates);
        List<Game> gameList = updates.stream().map(e -> gameService.getById(e.getGameId()).orElseThrow()).toList();
        pointCalculator.updatePoints(gameList);
        return ResponseEntity.ok("Updated results for " + updates.size() + " games");
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteGameResults(@RequestBody GameResultUpdate updates) {
        gameService.deleteGame(updates.getGameId());
        return ResponseEntity.ok("Meccs sikeresen törölve.");
    }
}
