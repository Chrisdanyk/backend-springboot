package cg.dany.todos.common.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PaginatedResponse<T> {
    private int pageSize;
    private int items;
    private int pages;
    private int page;
    private Integer next;
    private Integer previous;
    private List<T> results;
}