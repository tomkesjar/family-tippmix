package kt.tippmix.controller;


import kt.tippmix.model.User;
import kt.tippmix.repository.UserRepository;
import kt.tippmix.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

import static kt.tippmix.model.User.AuthProvider.LOCAL_USER;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*")
//@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {   //TODO KT: maybe return AuthenticationResponse
        if (authService.hasUserAlreadyRegistered(request.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        AuthenticationResponse response = authService.registerUser(request);

        return ResponseEntity.ok(response);
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
