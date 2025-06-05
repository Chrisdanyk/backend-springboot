package cg.dany.todos.todo.service;

import cg.dany.todos.todo.model.Todo;
import cg.dany.todos.todo.dto.CreateTodoRequest;
import cg.dany.todos.todo.dto.TodoDTO;
import cg.dany.todos.todo.repository.TodoRepository;
import cg.dany.todos.common.exception.NotFoundException;
import cg.dany.todos.common.exception.BadRequestException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Transactional(readOnly = true)
    public List<TodoDTO> getAllTodosByUserId(UUID userId) {
        return todoRepository.findByUserId(userId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TodoDTO getTodoById(UUID todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new NotFoundException("Item not found"));
        return convertToDTO(todo);
    }

    @Transactional
    public TodoDTO createTodo(CreateTodoRequest createTodoRequest) {
        if (createTodoRequest.getTitle() == null ||
                createTodoRequest.getTitle().trim().isEmpty()) {
            throw new BadRequestException(
                    "Todo",
                    "title",
                    "Title cannot be empty");
        }

        Todo todo = new Todo();
        todo.setTitle(createTodoRequest.getTitle());
        todo.setDescription(createTodoRequest.getDescription());
        todo.setCompleted(false);

        Todo savedTodo = todoRepository.save(todo);
        return convertToDTO(savedTodo);
    }

    @Transactional
    public TodoDTO updateTodo(UUID todoId, TodoDTO todoDTO) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new NotFoundException(
                        "Item not found"));

        if (todoDTO.getTitle() == null || todoDTO.getTitle().trim().isEmpty()) {
            throw new BadRequestException(
                    "Todo",
                    "title",
                    "Title cannot be empty");
        }

        todo.setTitle(todoDTO.getTitle());
        todo.setDescription(todoDTO.getDescription());
        todo.setCompleted(todoDTO.isCompleted());

        Todo updatedTodo = todoRepository.save(todo);
        return convertToDTO(updatedTodo);
    }

    @Transactional
    public void deleteTodo(UUID todoId) {
        if (!todoRepository.existsById(todoId)) {
            throw new NotFoundException("Item not found");
        }
        todoRepository.deleteById(todoId);
    }

    private TodoDTO convertToDTO(Todo todo) {
        TodoDTO dto = new TodoDTO();
        dto.setId(todo.getId());
        dto.setTitle(todo.getTitle());
        dto.setDescription(todo.getDescription());
        dto.setCompleted(todo.isCompleted());
        dto.setUserId(todo.getUser() != null ? todo.getUser().getId() : null);
        dto.setCreatedAt(todo.getCreatedAt());
        dto.setUpdatedAt(todo.getUpdatedAt());
        return dto;
    }
}
