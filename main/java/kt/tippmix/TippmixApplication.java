package kt.tippmix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;

@SpringBootApplication
// if we hit any API we would get a 401 unauthorized error without it
//@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class TippmixApplication {

	public static void main(String[] args) {
		SpringApplication.run(TippmixApplication.class, args);
	}

}
