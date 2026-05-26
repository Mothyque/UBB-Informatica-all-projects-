package org.example.networking;

import org.example.repository.*;
import org.example.repository.db.ClientDBRepository;
import org.example.repository.db.MatchDBRepository;
import org.example.repository.db.TicketDBRepository;
import org.example.repository.db.UserDBRepository;
import org.example.service.ClientService;
import org.example.service.MatchService;
import org.example.service.TicketService;
import org.example.service.UserService;
import org.example.utils.WebSocketObserver;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.io.IOException;
import java.util.Properties;

public class StartRpcServer
{
    private static int defaultPort = 5555;

    public static void main(String[] args)
    {
        Properties serverProps = new Properties();
        try
        {
            serverProps.load(StartRpcServer.class.getResourceAsStream("/db.properties"));
        }
        catch (IOException e)
        {
            System.err.println("Cannot find db.properties " + e);
            return;
        }
        String url = serverProps.getProperty("url");
        UserRepository userRepo = new UserDBRepository(url);
        MatchRepository matchRepo = new MatchDBRepository(url);
        ClientRepository clientRepo = new ClientDBRepository(url);
        TicketRepository ticketRepo = new TicketDBRepository(url);

        SimpMessagingTemplate simpMessagingTemplate = new SimpMessagingTemplate(null);
        WebSocketObserver webSocketObserver = new WebSocketObserver(simpMessagingTemplate);

        UserService userService = new UserService(userRepo);
        MatchService matchService = new MatchService(matchRepo,  webSocketObserver);
        ClientService clientService = new ClientService(clientRepo);
        TicketService ticketService = new TicketService(ticketRepo, matchRepo);

        org.example.networking.Services serverImpl = new ServicesImpl(userService, matchService, clientService, ticketService);

        int serverPort = defaultPort;
        AbstractServer server = new RpcConcurrentServer(serverPort, serverImpl);
        try
        {
            server.start();
        }
        catch (Exception e)
        {
            System.err.println("Error starting the server: " + e.getMessage());
        }
    }

}