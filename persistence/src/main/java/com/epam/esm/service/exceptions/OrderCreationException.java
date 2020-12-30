package com.epam.esm.service.exceptions;

import com.epam.esm.data.UserOrder;
import com.epam.esm.service.DAOException;

public class OrderCreationException extends DAOException {

    private final UserOrder notCreatedUserOrder;

    public OrderCreationException(String message, UserOrder notCreatedUserOrder) {
        super(message);
        this.notCreatedUserOrder = notCreatedUserOrder;
    }

    public OrderCreationException(String message, Throwable cause, UserOrder notCreatedUserOrder) {
        super(message, cause);
        this.notCreatedUserOrder = notCreatedUserOrder;
    }

    public UserOrder getNotCreatedUserOrder() {
        return notCreatedUserOrder;
    }
}
