package cg.dany.todos.todo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import cg.dany.todos.common.exception.BadRequestException;
import cg.dany.todos.todo.dto.CreateTodoRequest;
import cg.dany.todos.todo.dto.TodoDTO;
import cg.dany.todos.todo.service.TodoService;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public List<TodoDTO> getAllTodos() {
        return todoService.getAllTodosByUserId(null);
    }

    @GetMapping("/{id}")
    public TodoDTO getTodoById(@PathVariable UUID id) {
        return todoService.getTodoById(id);
    }

    @PostMapping
    public TodoDTO createTodo(@RequestBody CreateTodoRequest CreateTodoRequest) {
        return todoService.createTodo(CreateTodoRequest);
    }

    @PutMapping("/{id}")
    public TodoDTO updateTodo(@PathVariable UUID id, @RequestBody TodoDTO todo) {
        return todoService.updateTodo(id, todo);
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable UUID id) {
        todoService.deleteTodo(id);
    }
}