package kt.tippmix.controller;

import kt.tippmix.model.Game;
import kt.tippmix.model.User;
import kt.tippmix.repository.UserRepository;
import kt.tippmix.service.SecretNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/secretname")
@CrossOrigin(origins = "http://localhost:5173") // React dev server
public class SecretNameController {

    private final SecretNameService secretNameService;

    public SecretNameController(@Autowired SecretNameService secretNameService) {
        this.secretNameService = secretNameService;
    }

    @GetMapping()
    public ResponseEntity<Pair<String, String>> get(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Pair<String, String> secretName = secretNameService.getSecretName(user);
        return ResponseEntity.ok().body(secretName);
    }
}
