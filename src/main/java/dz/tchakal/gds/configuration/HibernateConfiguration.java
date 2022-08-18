package dz.tchakal.gds.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // pour indiquer a spring qu'on a utiliser les JPA auditings
@Configuration
public class HibernateConfiguration {
}
