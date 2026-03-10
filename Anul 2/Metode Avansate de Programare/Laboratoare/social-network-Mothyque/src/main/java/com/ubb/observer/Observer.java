package com.ubb.observer;

import com.ubb.domain.event.Event;

public interface Observer<E extends Event>
{
    void update(E event);
}
