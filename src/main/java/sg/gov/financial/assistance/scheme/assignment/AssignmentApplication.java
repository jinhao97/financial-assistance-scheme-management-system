package sg.gov.financial.assistance.scheme.assignment;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import sg.gov.financial.assistance.scheme.assignment.auth.AuthenticationService;
import sg.gov.financial.assistance.scheme.assignment.dto.RegisterRequestDTO;
import sg.gov.financial.assistance.scheme.assignment.repository.AdministratorRepository;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class AssignmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(AssignmentApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(
            AuthenticationService service,
            AdministratorRepository repo
    ) {
        return args -> {
            boolean accountExist = repo.existsByUin("S1234567D");
            if (!accountExist) {
                var admin = new RegisterRequestDTO("S1234567D", "admin");
                service.register(admin);
            }
        };
    }
}
