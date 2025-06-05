package cg.dany.todos.common.dto;

import lombok.Data;

@Data
public abstract class BaseSearchRequest {
    private Integer page;
    private Integer pageSize;
    private String sortBy;
    private String sortDirection;
}