package com.epam.esm.api.validation;

public interface Validator<T> {

    ValidationResult validate(T entity);

}
