package eu.itcrafters.myproject.infrastructure.rest.exception;

import lombok.Getter;

@Getter
public class ForbiddenException extends RuntimeException {
    private final String message;

    public ForbiddenException(String message) {
        super(message);
        this.message = message;
    }
}
