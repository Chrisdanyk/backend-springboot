package cg.dany.todos.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
public class LiquibaseConfig {

    @Autowired
    private Environment env;

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog("classpath:db/changelog/db.changelog-master.xml");
        liquibase.setContexts(env.getProperty("spring.liquibase.contexts", ""));
        liquibase.setDefaultSchema(env.getProperty("spring.liquibase.default-schema"));
        liquibase.setDropFirst(env.getProperty("spring.liquibase.drop-first", Boolean.class, false));
        return liquibase;
    }
}