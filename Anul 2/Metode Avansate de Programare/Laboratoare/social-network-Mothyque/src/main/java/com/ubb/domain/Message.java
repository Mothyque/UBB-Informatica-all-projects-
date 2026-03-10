package com.ubb.domain;

import java.time.LocalDateTime;
import java.util.List;

public class Message extends Entity<Integer>
{
    private User from;
    private List<User> to;
    private String message;
    private LocalDateTime date;

    public Message(Integer id, User from, List<User> to, String message, LocalDateTime date)
    {
        super(id);
        this.from = from;
        this.to = to;
        this.message = message;
        this.date = date;
    }

    public User getFrom()
    {
        return from;
    }

    public List<User> getTo()
    {
        return to;
    }

    public String getMessage()
    {
        return message;
    }

    public LocalDateTime getDate()
    {
        return date;
    }

    public void setDate(LocalDateTime date)
    {
        this.date = date;
    }
}
