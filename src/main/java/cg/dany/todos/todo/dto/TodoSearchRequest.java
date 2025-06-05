package cg.dany.todos.todo.dto;

import cg.dany.todos.common.dto.BaseSearchRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TodoSearchRequest extends BaseSearchRequest {
    private String title;
    private Boolean completed;
}