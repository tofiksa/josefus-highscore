package no.josefushighscore.configure;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

public class FlywayDBConfiguration {

    @Bean
    public Flyway flyway(DataSource flywayDatasource, @Value("${spring.flyway.locations}") String locations) {
        Flyway flyway = Flyway.configure().dataSource(flywayDatasource).locations(locations).load();
        flyway.migrate();
        return flyway;
    }
}
