package kt.tippmix.security;

import kt.tippmix.model.User;
import kt.tippmix.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

//    private final UserRepository userRepository;
//
//    public CustomOAuth2UserService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest request)
//            throws OAuth2AuthenticationException {
//
//        OAuth2User user = super.loadUser(request);
//
//        String email = user.getAttribute("email");
//        String name = user.getAttribute("name");
//
//        userRepository.findByEmail(email).orElseGet(() -> {;
//            String provider = request.getClientRegistration().getRegistrationId();
//            User newUser = new User(name, null, null, null, email, User.AuthProvider.valueOf(provider), User.Role.PLAYER);
//            return userRepository.save(newUser);
//        });
//
//        return user;
//    }
}