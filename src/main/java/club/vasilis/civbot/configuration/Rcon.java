package club.vasilis.civbot.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "rcon")
public class Rcon {
    private String serverAddress;
    private String serverPort;
    private String password;
}
