package com.ubb.observer;

import com.ubb.domain.event.Event;

public interface Observable <E extends Event>
{
    void addObserver(Observer<E> e);
    void removeObserver(Observer<E> e);
    void notifyObservers(E event);
}
