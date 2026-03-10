package com.ubb.service;

import com.ubb.domain.event.Event;
import com.ubb.observer.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractService<E> implements Service<E>
{
    private final List<Observer<Event>> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer<Event> observer)
    {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer<Event> e)
    {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(Event event)
    {
        for(Observer<Event> observer : observers)
        {
            observer.update(event);
        }
    }
}
