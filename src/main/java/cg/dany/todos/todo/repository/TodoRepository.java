package cg.dany.todos.todo.repository;

import cg.dany.todos.todo.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TodoRepository extends JpaRepository<Todo, UUID> {
    List<Todo> findByUserId(UUID userId);

    List<Todo> findByUserIdAndCompleted(UUID userId, boolean completed);
}