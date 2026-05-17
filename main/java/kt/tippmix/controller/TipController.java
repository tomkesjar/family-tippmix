package kt.tippmix.controller;


import kt.tippmix.model.Tip;
import kt.tippmix.repository.TipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/tip")
@CrossOrigin(origins = "http://localhost:5173") // React dev server
public class TipService {

    private TipRepository tipRepository;

    public TipService(@Autowired TipRepository tipRepository) {
        this.tipRepository = tipRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Tip>> getAll() {
        List<Tip> tipList = (List) tipRepository.findAll();
        return ResponseEntity.ok().body(tipList);
    }

    @GetMapping("/fromdate")
    public ResponseEntity<List<Tip>> getByDate(@RequestParam String date) {
        LocalDateTime dateTime = LocalDateTime.parse(date);
        List<Tip> upcomingGames = tipRepository.findAllAfterDate(dateTime);
        return ResponseEntity.ok().body(upcomingGames);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Tip>> getByUser(@RequestParam String user) {
        List<Tip> upcomingGames = tipRepository.findAllByUser(user);
        return ResponseEntity.ok().body(upcomingGames);
    }

    @PostMapping
    public void save(@RequestBody Tip newTip) {
        tipRepository.save(newTip);
    }
}
