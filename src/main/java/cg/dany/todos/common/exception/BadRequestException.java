package cg.dany.todos.common.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("Invalid %s for %s : '%s'", fieldName, resourceName, fieldValue));
    }
}