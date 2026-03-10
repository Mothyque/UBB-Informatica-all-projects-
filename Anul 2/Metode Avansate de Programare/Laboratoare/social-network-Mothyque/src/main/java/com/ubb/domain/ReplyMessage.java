package com.ubb.domain;

import java.time.LocalDateTime;
import java.util.List;

public class ReplyMessage extends Message
{
    private Message repliedMessage;

    public ReplyMessage(Integer id, User from, List<User> to, String message, LocalDateTime date, Message repliedMessage)
    {
        super(id, from, to, message, date);
        this.repliedMessage = repliedMessage;
    }

    public Message getRepliedMessage()
    {
        return repliedMessage;
    }
}
