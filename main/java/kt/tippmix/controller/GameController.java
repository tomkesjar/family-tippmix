package kt.tippmix.controller;

import kt.tippmix.model.DateRequest;
import kt.tippmix.model.Game;
import kt.tippmix.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/game")
@CrossOrigin(origins = "http://localhost:5173") // React dev server
public class GameController {

    private GameService gameService;

    public GameController(@Autowired GameService gameService) {
        this.gameService = gameService;
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

    @GetMapping("/fromdate")
    public ResponseEntity<List<Game>> getAllAfterDate(@RequestBody DateRequest dateRequest) {
        List<Game> upcomingGames = gameService.getUpcomingGames(dateRequest.getDate());
        return ResponseEntity.ok().body(upcomingGames);
    }

    // Create a new match game, Allowed user: ADMIN
    @PostMapping("/newgame")
    public void save(@RequestBody Game newGame) {
        gameService.save(newGame);
    }
}
