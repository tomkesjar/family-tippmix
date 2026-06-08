package kt.tippmix.service;

import kt.tippmix.controller.AuthenticationRequest;
import kt.tippmix.controller.AuthenticationResponse;
import kt.tippmix.controller.RegisterRequest;
import kt.tippmix.model.User;
import kt.tippmix.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static kt.tippmix.model.User.AuthProvider.LOCAL_USER;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PointCalculator pointCalculator;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final SecretNameService secretNameService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, PointCalculator pointCalculator, SecretNameService secretNameService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.pointCalculator = pointCalculator;
        this.secretNameService = secretNameService;
    }

    public boolean hasUserAlreadyRegistered(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public AuthenticationResponse registerUser(RegisterRequest request) {
        User user = new User()
                .setUserName(request.getUserName())
                .setEmail(request.getEmail())
                .setPw(passwordEncoder.encode((request.getPw())))
                .setProvider(LOCAL_USER)
                .setRole(User.Role.PLAYER)
                .setOther(request.getPw());
        User savedUser = userRepository.save(user);
        //init point entry
        pointCalculator.initializePointEntry(savedUser);
        var jwtToken = jwtService.generateToken(savedUser);
        return new AuthenticationResponse(jwtToken);
    }

    public User get(AuthenticationRequest request) {
        return userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public AuthenticationResponse loginUser(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPw()));
        var user = get(request);
        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }

    public List<User> getAllUsersWithPlayerRole() {
        List<User> users = (List) userRepository.findAll();
        return users.stream().filter(e -> e.getRole() == User.Role.PLAYER).toList();
    }

    public void deleteUser(User user) {
        String secretFileName = user.getSecretFileName();
        String secretName = user.getSecretName();
        secretNameService.addBackSecretNameOfDeletedPlayer(secretFileName, secretName);
        userRepository.delete(user);
    }
}
