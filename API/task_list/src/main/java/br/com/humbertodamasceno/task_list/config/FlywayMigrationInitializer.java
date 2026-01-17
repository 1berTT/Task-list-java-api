package br.com.humbertodamasceno.task_list.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;

import javax.sql.DataSource;

@Configuration
@Order(1)
public class FlywayMigrationInitializer implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private DataSource dataSource;

    @Bean
    @DependsOn("dataSource")
    public Flyway flywayBean() {
        System.out.println("=== CRIANDO BEAN DO FLYWAY ===");
        Flyway flywayInstance = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration")
                .baselineOnMigrate(true)
                .validateOnMigrate(true)
                .outOfOrder(false)
                .load();
        return flywayInstance;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Flyway flyway = event.getApplicationContext().getBean(Flyway.class);
        if (flyway != null) {
            System.out.println("=== EXECUTANDO FLYWAY MIGRATIONS ===");
            var result = flyway.migrate();
            System.out.println("=== FLYWAY MIGRATIONS CONCLUÍDAS ===");
            System.out.println("=== MIGRATIONS APLICADAS: " + result.migrationsExecuted + " ===");
        }
    }
}
