package PCGamesGroup.PCGamesBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class PcGamesBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PcGamesBackendApplication.class, args);
	}
}
