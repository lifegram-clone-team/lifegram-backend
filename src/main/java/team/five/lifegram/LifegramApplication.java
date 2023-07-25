package team.five.lifegram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class LifegramApplication {

    public static void main(String[] args) {
        SpringApplication.run(LifegramApplication.class, args);
    }

}
