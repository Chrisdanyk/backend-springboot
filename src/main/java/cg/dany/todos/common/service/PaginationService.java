package cg.dany.todos.common.service;

import cg.dany.todos.common.dto.BaseSearchRequest;
import cg.dany.todos.common.dto.PaginatedResponse;
import cg.dany.todos.config.PaginationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class PaginationService<T, D, S extends BaseSearchRequest> {
    private static final Logger logger = LoggerFactory.getLogger(PaginationService.class);
    protected final PaginationConfig paginationConfig;

    protected PaginationService(PaginationConfig paginationConfig) {
        this.paginationConfig = paginationConfig;
        logger.info("PaginationService initialized with default page size: {}", paginationConfig.getDefaultPageSize());
    }

    protected Pageable createPageable(S searchRequest, List<String> allowedSortFields) {
        // Default to page 0 if not specified
        int page = searchRequest.getPage() != null ? searchRequest.getPage() : 0;

        // Use the configured default page size if not specified in the request
        int size = searchRequest.getPageSize() != null ? searchRequest.getPageSize()
                : paginationConfig.getDefaultPageSize();

        logger.info("Default page size from config: {}", paginationConfig.getDefaultPageSize());
        logger.info("Creating pageable with page: {} and size: {}", page, size);

        Sort sort = Sort.unsorted();
        if (searchRequest.getSortBy() != null && allowedSortFields.contains(searchRequest.getSortBy())) {
            sort = Sort.by(
                    searchRequest.getSortDirection() != null
                            && searchRequest.getSortDirection().equalsIgnoreCase("desc")
                                    ? Sort.Direction.DESC
                                    : Sort.Direction.ASC,
                    searchRequest.getSortBy());
        }

        return PageRequest.of(page, size, sort);
    }

    protected PaginatedResponse<D> createPaginatedResponse(Page<T> page, S searchRequest, Function<T, D> converter) {
        logger.info("Creating paginated response for page: {} with size: {} and total items: {}",
                page.getNumber(), page.getSize(), page.getTotalElements());

        List<D> results = page.getContent().stream()
                .map(converter)
                .collect(Collectors.toList());

        return PaginatedResponse.<D>builder()
                .pageSize(page.getSize())
                .items((int) page.getTotalElements())
                .pages(page.getTotalPages())
                .page(page.getNumber() + 1)
                .next(page.hasNext() ? page.getNumber() + 2 : null)
                .previous(page.hasPrevious() ? page.getNumber() : null)
                .results(results)
                .build();
    }
}