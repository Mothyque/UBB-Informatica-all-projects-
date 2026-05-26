package org.example.utils;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class WebSocketObserver implements Observer
{
    private final SimpMessagingTemplate simpMessagingTemplate;

    public WebSocketObserver(SimpMessagingTemplate simpMessagingTemplate)
    {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Override
    public void update()
    {
        simpMessagingTemplate.convertAndSend("/topic/matches", "REFRESH_MATCHES");
    }
}
