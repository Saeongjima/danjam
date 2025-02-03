package site.danjam.mate.mate_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class MateServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MateServiceApplication.class, args);
    }

}
