package com.ubb.domain.friendship;

import com.ubb.domain.Entity;
import com.ubb.utils.Tuple;

import java.time.LocalDateTime;

public class Friendship extends Entity<Tuple<Integer, Integer>>
{
    private LocalDateTime date;
    private FriendshipStatus status;
    public Friendship(Integer id1, Integer id2)
    {
        super(new Tuple<>(id1, id2));
        this.date = LocalDateTime.now();
        this.status = FriendshipStatus.PENDING;
    }

    public Friendship(Integer id1, Integer id2, LocalDateTime date, FriendshipStatus status)
    {
        super(new Tuple<>(id1, id2));
        this.date = date;
        this.status = status;
    }

    public Integer getId1()
    {
        return this.getId().getLeft();
    }

    public Integer getId2()
    {
        return this.getId().getRight();
    }

    public LocalDateTime getDate()
    {
        return date;
    }

    public FriendshipStatus getStatus()
    {
        return status;
    }

    public void setStatus(FriendshipStatus status)
    {
        this.status = status;
    }
}
