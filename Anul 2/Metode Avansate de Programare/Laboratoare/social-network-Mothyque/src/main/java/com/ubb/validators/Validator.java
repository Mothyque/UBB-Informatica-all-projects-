package com.ubb.validators;

import java.util.List;

public interface Validator <T>
{
    void validate(T entity) throws ValidationException;
}
