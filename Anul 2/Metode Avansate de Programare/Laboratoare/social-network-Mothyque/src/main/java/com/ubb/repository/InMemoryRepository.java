package com.ubb.repository;

import com.ubb.domain.Entity;
import com.ubb.validators.DuplicateException;
import com.ubb.validators.InputException;
import com.ubb.validators.Validator;

import java.util.Map;
import java.util.Optional;

public class InMemoryRepository<ID, E extends Entity<ID>> implements Repository<ID, E>
{
    private final Map<ID, E> entities;

    public InMemoryRepository(Map<ID, E> entities)
    {
        this.entities = entities;
    }

    @Override
    public Optional<E> findOne(ID id)
    {
        if(id == null)
            throw new InputException("ID must not be null!");
        return Optional.ofNullable(entities.get(id));
    }

    @Override
    public Iterable<E> findAll()
    {
        return entities.values();
    }

    @Override
    public Optional<E> save(E entity)
    {
        if(entity == null)
            throw new InputException("Entity must not be null!");
        if(entities.containsKey(entity.getId()))
            throw new DuplicateException("Entity with the given ID already exists!");
        entities.put(entity.getId(), entity);
        return Optional.of(entity);
    }

    @Override
    public Optional<E> delete(ID id)
    {
        if(id == null)
            throw new InputException("ID must not be null!");
        return Optional.ofNullable(entities.remove(id));
    }

    @Override
    public Optional<E> update(E entity)
    {
        if(entity == null)
            throw new InputException("Entity must not be null!");
        if(!entities.containsKey(entity.getId()))
            return Optional.empty();
        entities.put(entity.getId(), entity);
        return Optional.of(entity);
    }

    @Override
    public int size()
    {
        return entities.size();
    }
}
