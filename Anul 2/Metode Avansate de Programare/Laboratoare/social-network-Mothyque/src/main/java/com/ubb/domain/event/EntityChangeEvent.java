package com.ubb.domain.event;

import com.ubb.domain.Entity;

public class EntityChangeEvent<E extends Entity<?>> extends Event
{
    private final ChangeEventType type;
    private final E entity;

    public EntityChangeEvent(ChangeEventType type, E entity)
    {
        super();
        this.type = type;
        this.entity = entity;
    }

    public ChangeEventType getType()
    {
        return type;
    }

    public E getEntity()
    {
        return entity;
    }

}
