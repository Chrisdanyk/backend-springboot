package cg.dany.todos.todo.service;

import cg.dany.todos.todo.model.Todo;
import cg.dany.todos.todo.dto.CreateTodoRequest;
import cg.dany.todos.todo.dto.TodoDTO;
import cg.dany.todos.todo.dto.TodoSearchRequest;
import cg.dany.todos.todo.repository.TodoRepository;
import cg.dany.todos.common.exception.NotFoundException;
import cg.dany.todos.common.exception.BadRequestException;
import cg.dany.todos.common.dto.PaginatedResponse;
import cg.dany.todos.common.service.PaginationService;
import cg.dany.todos.config.PaginationConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class TodoService extends PaginationService<Todo, TodoDTO, TodoSearchRequest> {
    private static final Logger logger = LoggerFactory.getLogger(TodoService.class);
    private final TodoRepository todoRepository;
    private static final List<String> ALLOWED_SORT_FIELDS = Arrays.asList(
            "title", "completed", "createdAt", "updatedAt");

    public TodoService(TodoRepository todoRepository, PaginationConfig paginationConfig) {
        super(paginationConfig);
        this.todoRepository = todoRepository;
        logger.info("TodoService initialized with default page size: {}", paginationConfig.getDefaultPageSize());
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<TodoDTO> getTodos(TodoSearchRequest searchRequest) {
        Pageable pageable = createPageable(searchRequest, ALLOWED_SORT_FIELDS);
        logger.info("Creating pageable with page size: {}", pageable.getPageSize());

        Page<Todo> todoPage = todoRepository.findAll(pageable);
        logger.info("Retrieved {} todos for page {} with size {}",
                todoPage.getContent().size(),
                todoPage.getNumber(),
                todoPage.getSize());

        return createPaginatedResponse(todoPage, searchRequest, this::convertToDTO);
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
