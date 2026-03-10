package com.ubb.service;

import com.ubb.domain.Message;
import com.ubb.domain.Person;
import com.ubb.domain.ReplyMessage;
import com.ubb.domain.User;
import com.ubb.domain.event.ChangeEventType;
import com.ubb.domain.event.EntityChangeEvent;
import com.ubb.repository.Repository;
import com.ubb.utils.paging.Page;
import com.ubb.utils.paging.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MessageService extends AbstractService<Message>
{
    private final Repository<Integer, Message> messageRepository;

    public MessageService(Repository<Integer, Message> messageRepository)
    {
        this.messageRepository = messageRepository;
    }

    public Message sendMessage(User from, List<User> to, String text)
    {
        Message message = new Message(0, from, to, text, LocalDateTime.now());
        messageRepository.save(message);
        notifyObservers(new EntityChangeEvent<>(ChangeEventType.ADD, message));
        return message;
    }

    public void sendReply(User from, Message originalMessage, String text)
    {
        List<User> to = new ArrayList<>();
        to.add(originalMessage.getFrom());
        ReplyMessage replyMessage = new ReplyMessage(0, from, to, text, LocalDateTime.now(), originalMessage);
        messageRepository.save(replyMessage);
        notifyObservers(new EntityChangeEvent<>(ChangeEventType.ADD, replyMessage));
    }

    public List<Message> getConversation(User user1, User user2)
    {
        return StreamSupport.stream(messageRepository.findAll().spliterator(), false)
                .filter(msg -> isMessageBetween(msg, user1, user2))
                .sorted(Comparator.comparing(Message::getDate))
                .collect(Collectors.toList());
    }

    private boolean isMessageBetween(Message msg, User user1, User user2)
    {
        if(msg.getTo().size() > 1)
        {
            return false;
        }
        boolean fromUser1ToUser2 = msg.getTo().contains(user2) && msg.getFrom().equals(user1);
        boolean fromUser2ToUser1 = msg.getTo().contains(user1) && msg.getFrom().equals(user2);
        return fromUser1ToUser2 || fromUser2ToUser1;
    }

    @Override
    public Iterable<Message> getAll()
    {
        return messageRepository.findAll();
    }

    @Override
    public Page<Message> findAllOnPage(Pageable pageable)
    {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public List<Message> getCommunityMessages()
    {
        return StreamSupport.stream(messageRepository.findAll().spliterator(), false)
                .filter(msg -> msg.getTo().size() > 1)
                .sorted(Comparator.comparing(Message::getDate))
                .collect(Collectors.toList());
    }

    public List<String> getSystemMessages()
    {
        return StreamSupport.stream(messageRepository.findAll().spliterator(), false)
                .filter(msg -> {
                    if(msg.getFrom() == null) return false;
                    int fromId = msg.getFrom().getId();
                    return fromId == 0;
                })
                .sorted(Comparator.comparing(Message::getDate))
                .map(Message::getMessage)
                .collect(Collectors.toList());
    }

    public void sendGlobalMessage(String senderName, String text)
    {
        User adminUser = new Person(0, "admin", "admin", "admin", "admin", "admin", "", "admin", 0);
        List<User> recipients = new ArrayList<>();
        recipients.add(adminUser);
        Message message = new Message(0, adminUser, recipients, text, LocalDateTime.now());
        messageRepository.save(message);
        notifyObservers(new EntityChangeEvent<>(ChangeEventType.ADD, message));
    }
}
