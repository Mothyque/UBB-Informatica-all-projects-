package org.example.service;

import org.example.domain.Entity;
import org.example.repository.Repository;
import org.example.utils.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Service<ID, E extends Entity<ID>>
{
    protected final Repository<ID, E> repository;

    private List<Observer> observers = new ArrayList<>();

    public Service(Repository<ID, E> repository)
    {
        this.repository = repository;
    }

    public void add(E entity)
    {
        repository.save(entity);
        updateObservers();
    }

    public void delete(ID id)
    {
        repository.delete(id);
        updateObservers();
    }

    public void update(E entity)
    {
        repository.update(entity);
        updateObservers();
    }

    public Optional<E> findOne(ID id)
    {
        return repository.findOne(id);
    }

    public Iterable<E> findAll()
    {
        return repository.findAll();
    }

    public int size()
    {
        return repository.size();
    }

    public void addObserver(Observer observer)
    {
        observers.add(observer);
    }

    public void updateObservers()
    {
        for (Observer observer : observers)
        {
            observer.update();
        }
    }
}
