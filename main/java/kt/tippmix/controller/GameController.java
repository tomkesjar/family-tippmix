package kt.tippmix.controller;

import kt.tippmix.model.Game;
import kt.tippmix.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/game")
@CrossOrigin(origins = "http://localhost:5173") // React dev server
public class GameService {

    private GameRepository gameRepository;

    public GameService(@Autowired GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Game>> getAll() {
        List<Game> gameList = (List) gameRepository.findAll();
        return ResponseEntity.ok().body(gameList);
    }

    @GetMapping("/id")
    public ResponseEntity<Game> getById(@RequestParam Long id) {
        return gameRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/fromdate")
    public ResponseEntity<List<Game>> getByDate(@RequestParam String date) {
        LocalDateTime dateTime = LocalDateTime.parse(date);
        List<Game> upcomingGames = gameRepository.findAllAfterDate(dateTime);
        return ResponseEntity.ok().body(upcomingGames);
    }

    // Create a new match game, Allowed user: ADMIN
    @PostMapping("/new")
    public void save(@RequestBody Game newGame) {
        gameRepository.save(newGame);
    }
}
