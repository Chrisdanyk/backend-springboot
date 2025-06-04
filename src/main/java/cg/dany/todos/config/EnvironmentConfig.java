package cg.dany.todos.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

@Configuration
@PropertySource(value = "classpath:.env", ignoreResourceNotFound = true)
public class EnvironmentConfig {

    @Autowired
    private Environment env;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setLocation(new ClassPathResource(".env"));
        configurer.setIgnoreResourceNotFound(true);
        return configurer;
    }

    @Bean
    public String dbUrl() {
        return env.getProperty("DB_URL");
    }

    @Bean
    public String dbUsername() {
        return env.getProperty("DB_USERNAME");
    }

    @Bean
    public String dbPassword() {
        return env.getProperty("DB_PASSWORD");
    }
}