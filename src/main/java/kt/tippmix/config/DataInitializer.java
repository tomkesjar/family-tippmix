package kt.tippmix.config;

import kt.tippmix.model.User;
import kt.tippmix.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Ensures the admin user exists every time the application starts.
 * The check is idempotent — if the email is already present, nothing is written.
 * The password is BCrypt-encoded so Spring Security's login flow accepts it.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository  userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository  = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        try {
            if (userRepository.findByEmail("admin1@mail.hu").isEmpty()) {
                String pw = "admin1";
                User admin = new User();
                admin.setUserName("Admin1");
                admin.setEmail("admin1@mail.hu");
                admin.setPw(passwordEncoder.encode(pw));
                admin.setOther(pw);
                admin.setProvider(User.AuthProvider.LOCAL_USER);
                admin.setRole(User.Role.ADMIN);
                userRepository.save(admin);
                System.out.println("[DataInitializer] Admin user created.");
            } else {
                System.out.println("[DataInitializer] Admin user already exists, skipping.");
            }
        } catch (Exception e) {
            System.err.println("[DataInitializer] Failed to create admin user: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
