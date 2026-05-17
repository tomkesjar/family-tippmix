package kt.tippmix.secretnamegenerator;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "allowed")
public class AllowedSecretNames {

    private List<String> secretNames;
    private List<String> secretFileNames;

    public List<String> getSecretNames() {
        return secretNames;
    }

    public void setSecretNames(List<String> secretNames) {
        this.secretNames = secretNames;
    }

    public List<String> getSecretFileNames() {
        return secretFileNames;
    }

    public void setSecretFileNames(List<String> secretFileNames) {
        this.secretFileNames = secretFileNames;
    }
}
