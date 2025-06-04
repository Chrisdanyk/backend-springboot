package cg.dany.todos.utils;

import cg.dany.todos.config.LiquibaseConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MigrationService {

    @Autowired
    private LiquibaseConfig liquibaseConfig;

    public void generateAndApplyMigration() {
        // This method will be used to trigger migrations when needed
        // For now, it's a placeholder as Liquibase handles migrations automatically
    }
}