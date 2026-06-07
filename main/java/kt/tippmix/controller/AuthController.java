package kt.tippmix.controller;


import kt.tippmix.model.PlayerSummary;
import kt.tippmix.model.User;
import kt.tippmix.repository.UserRepository;
import kt.tippmix.service.AuthService;
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

import static kt.tippmix.model.User.AuthProvider.LOCAL_USER;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*")
//@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class AuthController {

    private final AuthService    authService;
    private final UserRepository userRepository;
    private List<String> allowedEmailsForRegistration;

    public AuthController(AuthService authService, UserRepository userRepository, @Value("${ALLOWED_EMAILS_FOR_REGISTRATION:}") String allowedEmailsForRegistration) {
        this.authService    = authService;
        this.userRepository = userRepository;
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

    /** Returns id + secretName for every user with role PLAYER. Admin-only. */
    @GetMapping("/players")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<PlayerSummary>> getPlayers() {
        List<PlayerSummary> players = ((List<User>) userRepository.findAll()).stream()
                .filter(u -> u.getRole() == User.Role.PLAYER)
                .map(u -> new PlayerSummary(u.getId(), u.getSecretName()))
                .toList();
        return ResponseEntity.ok(players);
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