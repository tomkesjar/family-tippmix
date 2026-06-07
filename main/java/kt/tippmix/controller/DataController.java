package kt.tippmix.controller;

import kt.tippmix.model.DetailedDataRow;
import kt.tippmix.service.DataService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/data")
@CrossOrigin(origins = "http://localhost:5173")
public class DataController {

    private final DataService dataService;

    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<DetailedDataRow>> getData() {
        return ResponseEntity.ok(dataService.getDetailedData());
    }

    @PostMapping("/email")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> sendDataEmail() {
        try {
            dataService.sendEmailWithData();
            return ResponseEntity.ok("Email sikeresen elküldve!");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Hiba: " + e.getMessage());
        }
    }
}
