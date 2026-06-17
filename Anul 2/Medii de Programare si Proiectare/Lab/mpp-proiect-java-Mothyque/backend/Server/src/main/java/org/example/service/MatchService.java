package org.example.service;

import org.example.domain.Match;
import org.example.repository.Repository;
import org.example.utils.WebSocketObserver;
import org.springframework.web.socket.WebSocketSession;

public class MatchService extends Service<Integer, Match>
{
    public MatchService(Repository<Integer, Match> repository, WebSocketObserver wsObserver)
    {
        super(repository);
        this.addObserver(wsObserver);
    }
}
