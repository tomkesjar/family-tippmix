package kt.tippmix.controller;


import kt.tippmix.model.Bet;
import kt.tippmix.model.PlayerSummary;
import kt.tippmix.model.User;
import kt.tippmix.repository.UserRepository;
import kt.tippmix.service.AuthService;
import kt.tippmix.service.BetService;
import kt.tippmix.service.SecretNameService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import static kt.tippmix.model.User.AuthProvider.LOCAL_USER;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*")
//@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class AuthController {

    private final AuthService       authService;
    private final UserRepository    userRepository;
    private final SecretNameService secretNameService;
    private List<String> allowedEmailsForRegistration;
    private BetService betService;

    public AuthController(AuthService authService,
                          UserRepository userRepository,
                          SecretNameService secretNameService,
                          BetService betService,
                          @Value("${ALLOWED_EMAILS_FOR_REGISTRATION:}") String allowedEmailsForRegistration) {
        this.authService       = authService;
        this.userRepository    = userRepository;
        this.secretNameService = secretNameService;
        this.betService = betService;
        this.allowedEmailsForRegistration = Arrays.stream(allowedEmailsForRegistration.split(",")).map(String::toLowerCase).toList();
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {   //TODO KT: maybe return AuthenticationResponse
        if (authService.hasUserAlreadyRegistered(request.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        if (!allowedEmailsForRegistration.contains(request.getEmail().toLowerCase())) {
            return ResponseEntity.badRequest().body("Email is not registered as allowed email");
        }
        AuthenticationResponse response = authService.registerUser(request);

        return ResponseEntity.ok(response);
    }


    /** Returns the authenticated user's role. Safe to call from whitelisted path because
     *  the JWT filter still populates the SecurityContext when a valid token is present. */
    @GetMapping("/me")
    public ResponseEntity<?> getMe(Authentication authentication) {
        if (authentication == null || authentication.getPrincipal() instanceof String) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(Map.of("role", user.getRole().name()));
    }

    /** Returns full player info for every PLAYER. Admin-only. */
    @GetMapping("/players")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<PlayerSummary>> getPlayers() {
        List<PlayerSummary> players = ((List<User>) userRepository.findAll()).stream()
                .filter(u -> u.getRole() == User.Role.PLAYER)
                .map(u -> new PlayerSummary(u.getId(), u.getUserName(), u.getEmail(), u.getOther(), u.getSecretName(), u.getSecretFileName()))
                .toList();
        return ResponseEntity.ok(players);
    }

    /** Deletes a PLAYER by id and frees their secret name back into the pool. Admin-only. */
    @DeleteMapping("/player/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deletePlayer(@PathVariable long id) {
        Optional<User> optUser = userRepository.findById(id);
        if (optUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        User user = optUser.get();
        if (user.getRole() != User.Role.PLAYER) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User is not a PLAYER");
        }
        if (user.getSecretName() != null && user.getSecretFileName() != null) {
            secretNameService.addBackSecretNameOfDeletedPlayer(user.getSecretFileName(), user.getSecretName());
        }
        //clear bets
        List<Bet> allBetsByPlayer = betService.getAllByPlayerId(id);
        allBetsByPlayer.forEach(betService::deleteBet);
        //Clear user
        userRepository.delete(user);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {
        try {
            AuthenticationResponse response = authService.loginUser(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid password");
        }
    }

}