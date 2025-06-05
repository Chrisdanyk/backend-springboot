package cg.dany.todos.config;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

@Data
@Configuration
@ConfigurationProperties(prefix = "pagination")
public class PaginationConfig {
    private static final Logger logger = LoggerFactory.getLogger(PaginationConfig.class);

    private int defaultPageSize = 5;
    private String defaultSortField = "createdAt";
    private String defaultSortDirection = "desc";

    @PostConstruct
    public void init() {
        logger.info("PaginationConfig initialized with defaultPageSize: {}", defaultPageSize);
    }
}