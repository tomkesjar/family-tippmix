package kt.tippmix.service;

import kt.tippmix.model.Game;
import kt.tippmix.repository.GameRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin(origins = "http://localhost:3000") // React dev server
public class MatchService {

    private GameRepository matchRepository;

    public MatchService(GameRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    // GET all
    @GetMapping
    public List<Game> getTodos() {
        return matchRepository.findAll();
    }

    // POST new
    @PostMapping
    public void createTodo(@RequestBody Game todo) {
        matchRepository.save(todo);
    }

}
