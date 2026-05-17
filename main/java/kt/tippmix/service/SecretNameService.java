package kt.tippmix.service;

import kt.tippmix.model.User;
import kt.tippmix.repository.UserRepository;
import kt.tippmix.secretnamegenerator.AllowedSecretNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class SecretNameService {

    private Map<String, String> freeAllowedSecretNames;
    private Map<String, String> bookedAllowedSecretNames;
    private final UserRepository userRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(SecretNameService.class);

public SecretNameService(@Autowired AllowedSecretNames allowedSecretNames, @Autowired UserRepository userRepository) {
    this.userRepository = userRepository;
    this.freeAllowedSecretNames = generateAllowedSecretNames(allowedSecretNames);
    this.bookedAllowedSecretNames = new HashMap<>();
}

    public Pair<String,String> getSecretName(User user) {
        if (user.getSecretName() == null) {
            List<String> shuffledKeys = new ArrayList<>(freeAllowedSecretNames.keySet());
            Collections.shuffle(shuffledKeys);

            String selectedFileName = shuffledKeys.get(0);
            String selectedSecretName = freeAllowedSecretNames.remove(selectedFileName);

            user.setSecretName(selectedSecretName);
            user.setSecretFileName(selectedFileName);

            userRepository.save(user);
            LOGGER.info("SecretName {} was assigned to user {} and stored to database", selectedSecretName, user.getEmail());
            bookedAllowedSecretNames.put(selectedFileName, selectedSecretName);
            return Pair.of(selectedFileName, selectedSecretName);
        } else {
            return Pair.of(user.getSecretFileName(), user.getSecretName());
        }
    }

    private Map<String, String> generateAllowedSecretNames(AllowedSecretNames allowedSecretNames) {
        Map<String, String> result = new HashMap<>();
        List<String> names = allowedSecretNames.getSecretNames();
        List<String> fileNames = allowedSecretNames.getSecretFileNames();
        if (names.size() != fileNames.size()) {
            throw new IllegalStateException("Allowed names size must match with allowed filenames size");
        }
        Iterator<String> i1 = fileNames.iterator();
        Iterator<String> i2 = names.iterator();
        while (i1.hasNext() || i2.hasNext()) {
            result.put(i1.next(), i2.next());
        }

        return result;
    }
}
