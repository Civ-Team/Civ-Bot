package club.vasilis.civbot;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class MiraiRobotApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiraiRobotApplication.class, args);
    }

}
