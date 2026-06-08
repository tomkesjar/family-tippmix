package kt.tippmix.controller;

import kt.tippmix.model.RankingResponse;
import kt.tippmix.service.PointCalculator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/point")
@CrossOrigin(origins = "http://localhost:5173")
public class PointController {

    private final PointCalculator pointCalculator;

    public PointController(PointCalculator pointCalculator) {
        this.pointCalculator = pointCalculator;
    }

    @GetMapping("/ranking")
    public ResponseEntity<List<RankingResponse>> getRanking() {
        List<RankingResponse> ranking = pointCalculator.showPointsInRanking();
        return ResponseEntity.ok(ranking);
    }
}
