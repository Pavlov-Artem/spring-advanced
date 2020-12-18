package com.epam.esm.service.exceptions;

import com.epam.esm.service.DAOException;

public class UnsupportedOperationException extends DAOException {

    private String operationName;

    public UnsupportedOperationException(String message, String operationName) {
        super(message);
        this.operationName = operationName;
    }

    public UnsupportedOperationException(String message, Throwable cause, String operationName) {
        super(message, cause);
        this.operationName = operationName;
    }

}
