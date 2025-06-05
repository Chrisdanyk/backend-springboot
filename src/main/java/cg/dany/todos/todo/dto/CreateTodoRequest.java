package cg.dany.todos.todo.dto;

import lombok.Data;

@Data
public class CreateTodoRequest {
    private String title;
    private String description;
}
