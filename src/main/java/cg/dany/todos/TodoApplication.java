package cg.dany.todos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import cg.dany.todos.config.PaginationConfig;

@SpringBootApplication
@EnableConfigurationProperties(PaginationConfig.class)
public class TodoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoApplication.class, args);
	}

}
