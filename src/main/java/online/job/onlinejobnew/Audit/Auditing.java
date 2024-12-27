package online.job.onlinejobnew.Audit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.userdetails.UserDetails;

@Configuration
@EnableJpaAuditing
public class Auditing {

    @Bean
    public AuditorAware<UserDetails> auditorProvider() {
        return new AuditingSecurity();
    }
}
