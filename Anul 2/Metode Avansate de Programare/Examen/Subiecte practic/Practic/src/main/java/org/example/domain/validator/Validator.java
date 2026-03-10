package org.example.domain.validator;

import org.example.domain.exception.ValidationException;

public interface Validator<E> {
    void validate(E entity) throws ValidationException;
}