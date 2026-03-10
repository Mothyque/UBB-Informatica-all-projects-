package com.ubb.domain.event;

import com.ubb.domain.Entity;
import com.ubb.observer.Observable;
import com.ubb.observer.Observer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public abstract class Event extends Entity<Integer> implements Observable<Event>
{
    private final List<Observer<Event>> observers;
    private LocalDateTime date;
    public Event() {
        this.observers = new ArrayList<>();
        this.date = LocalDateTime.now();
    }

    public LocalDateTime getDate()
    {
        return date;
    }

    public void setDate(LocalDateTime date)
    {
        this.date = date;
    }

    @Override
    public void addObserver(Observer<Event> e)
    {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<Event> e)
    {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(Event e)
    {
        for (Observer<Event> o : observers)
        {
            o.update(e);
        }
    }

    @Override
    public String toString()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return "Event{" +
                "id=" + getId() +
                ", date=" + date.format(formatter) +
                '}';
    }
}
