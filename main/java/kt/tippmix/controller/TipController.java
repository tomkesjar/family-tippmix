package kt.tippmix.controller;


import kt.tippmix.model.Tip;
import kt.tippmix.model.User;
import kt.tippmix.service.TipService;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tip")
@CrossOrigin(origins = "http://localhost:5173") // React dev server
public class TipController {

    private TipService tipService;

    public TipController(@Autowired TipService tipService) {
        this.tipService = tipService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Tip>> getAll() {
        List<Tip> tipList = (List) tipService.getAll();
        return ResponseEntity.ok().body(tipList);
    }

    @GetMapping("/fromdate")
    public ResponseEntity<List<Tip>> getByDate(@RequestParam String date) {
        List<Tip> upcomingGames = tipService.getByDate(date);
        return ResponseEntity.ok().body(upcomingGames);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Tip>> getByUser(@RequestParam String user) {
        List<Tip> upcomingGames = tipService.getByUser(user);
        return ResponseEntity.ok().body(upcomingGames);
    }

    //ot good, we need to use logged in users!
    @PostMapping("/save")
    public ResponseEntity<?> save(Authentication authentication, @RequestBody List<Tip> newTip) {
        User user = (User) authentication.getPrincipal();
        try {
            newTip.forEach(bet -> {
                Tip saveResponse = tipService.save(bet);
            });
            return ResponseEntity.ok("Saved " + newTip.size() + " bets");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Could not save bets, please try again");
        }
    }
}
