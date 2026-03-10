package com.ubb.domain.event;

import com.ubb.domain.Entity;
import com.ubb.utils.Tuple;

public class EventParticipant extends Entity<Tuple<Integer, Integer>>
{
    public EventParticipant(Integer eventId, Integer userId)
    {
        Tuple<Integer, Integer> id = new Tuple<>(eventId, userId);
        setId(id);
    }

    public Integer getEventId()
    {
        return getId().getLeft();
    }

    public Integer getUserId()
    {
        return getId().getRight();
    }
}
