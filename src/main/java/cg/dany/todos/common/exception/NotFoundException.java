package cg.dany.todos.common.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found", resourceName));
    }
}