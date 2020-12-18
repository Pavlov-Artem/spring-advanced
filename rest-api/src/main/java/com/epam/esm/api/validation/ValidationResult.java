package com.epam.esm.api.validation;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {

    private final List<String> errorMessages = new ArrayList<>();

    public void ifNotEmptyAddErrorMessage(String message) {

        if (!message.trim().isEmpty()){
            errorMessages.add(message);
        }
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public boolean isValid() {
        return errorMessages.isEmpty();
    }
}
