package com.epam.esm.service.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {

    private final Long entityId;

    public EntityNotFoundException(Long entityId) {
        this.entityId = entityId;
    }

    public EntityNotFoundException(String message, Long entityId) {
        super(message);
        this.entityId = entityId;
    }

    public EntityNotFoundException(String message, Throwable cause, Long entityId) {
        super(message, cause);
        this.entityId = entityId;
    }

    public EntityNotFoundException(Throwable cause, Long entityId) {
        super(cause);
        this.entityId = entityId;
    }

    public EntityNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Long entityId) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.entityId = entityId;
    }

    public Long getEntityId() {
        return entityId;
    }
}
