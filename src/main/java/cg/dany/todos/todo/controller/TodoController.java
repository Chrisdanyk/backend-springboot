package cg.dany.todos.todo.controller;

import cg.dany.todos.todo.dto.CreateTodoRequest;
import cg.dany.todos.todo.dto.TodoDTO;
import cg.dany.todos.todo.dto.TodoSearchRequest;
import cg.dany.todos.todo.service.TodoService;
import cg.dany.todos.common.dto.PaginatedResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
    private static final Logger logger = LoggerFactory.getLogger(TodoController.class);
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public ResponseEntity<PaginatedResponse<TodoDTO>> getAllTodos(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDirection) {

        logger.info("Getting all todos with page: {}, pageSize: {}, sortBy: {}, sortDirection: {}",
                page, pageSize, sortBy, sortDirection);

        TodoSearchRequest searchRequest = new TodoSearchRequest();
        searchRequest.setPage(page);
        searchRequest.setPageSize(pageSize);
        searchRequest.setSortBy(sortBy);
        searchRequest.setSortDirection(sortDirection);

        return ResponseEntity.ok(todoService.getTodos(searchRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoDTO> getTodoById(@PathVariable UUID id) {
        return ResponseEntity.ok(todoService.getTodoById(id));
    }

    @PostMapping
    public ResponseEntity<TodoDTO> createTodo(@RequestBody CreateTodoRequest createTodoRequest) {
        return ResponseEntity.ok(todoService.createTodo(createTodoRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoDTO> updateTodo(@PathVariable UUID id, @RequestBody TodoDTO todoDTO) {
        return ResponseEntity.ok(todoService.updateTodo(id, todoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable UUID id) {
        todoService.deleteTodo(id);
        return ResponseEntity.ok().build();
    }
}