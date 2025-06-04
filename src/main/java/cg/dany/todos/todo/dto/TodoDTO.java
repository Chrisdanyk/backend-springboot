package cg.dany.todos.todo.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TodoDTO {
    private UUID id;
    private String title;
    private boolean completed;
    private String description;
    private UUID userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}