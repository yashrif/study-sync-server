package com.amadeus.studysync;

import com.amadeus.studysync.auth.AuthenticationService;
import com.amadeus.studysync.auth.RegisterRequest;
import com.amadeus.studysync.user.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class StudysyncApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudysyncApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(
            AuthenticationService service
    ) {
        return args -> {
            try {
                RegisterRequest admin = RegisterRequest.builder()
                        .firstName("Amadeus")
                        .lastName("Admin")
                        .email("amadeus@mail.com")
                        .password("amadeus123")
                        .role(Role.ADMIN)
                        .build();
                System.out.println("\nAdmin created with email: "
                        + admin.getEmail() + ", password: "
                        + admin.getPassword() + "and token: "
                        + service.register(admin).getAccessToken());
            } catch (Exception e) {
                System.out.println("\n" + e.getMessage());
            }
        };
    }
}
