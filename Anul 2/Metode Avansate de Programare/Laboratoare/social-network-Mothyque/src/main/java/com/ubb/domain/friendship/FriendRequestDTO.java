package com.ubb.domain.friendship;
import java.time.format.DateTimeFormatter;

public class FriendRequestDTO
{
    private Friendship entity;
    private String senderName;

    public FriendRequestDTO(Friendship entity, String senderName)
    {
        this.entity = entity;
        this.senderName = senderName;
    }

    public Friendship getEntity()
    {
        return entity;
    }

    public String getSenderName()
    {
        return senderName;
    }

    public String getDate()
    {
        if (entity != null && entity.getDate() != null)
        {
            return entity.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        }
        return "";
    }

    public String getStatus()
    {
        if (entity != null && entity.getStatus() != null)
        {
            return entity.getStatus().toString();
        }
        return "";
    }
}
