package org.example.networking.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.example.networking.Services;
import org.example.networking.ServicesImpl;
import org.example.networking.StartRpcServer;
import org.example.repository.*;
import org.example.repository.db.ClientDBRepository;
import org.example.repository.db.MatchDBRepository;
import org.example.repository.db.TicketDBRepository;
import org.example.repository.db.UserDBRepository;
import org.example.repository.orm.ClientORMRepository;
import org.example.repository.orm.MatchORMRepository;
import org.example.service.ClientService;
import org.example.service.MatchService;
import org.example.service.TicketService;
import org.example.service.UserService;
import org.example.utils.WebSocketObserver;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Properties;

public class StartGrpcServer
{

    public static void main(String[] args) throws Exception
    {
        Properties props = new Properties();
        Properties serverProps = new Properties();
        try
        {
            props.load(StartRpcServer.class.getResourceAsStream("/db.properties"));
            serverProps.load(StartRpcServer.class.getResourceAsStream("/server.properties"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        UserRepository userRepo = new UserDBRepository(props.getProperty("url"));
//        MatchRepository matchRepo = new MatchDBRepository(props.getProperty("url"));
//        ClientRepository clientRepo = new ClientDBRepository(props.getProperty("url"));
        MatchRepository matchRepo = new MatchORMRepository();
        ClientRepository clientRepo = new ClientORMRepository();
        TicketRepository ticketRepo = new TicketDBRepository(props.getProperty("url"));

        SimpMessagingTemplate simpMessagingTemplate = new SimpMessagingTemplate(null);
        WebSocketObserver wsObserver = new WebSocketObserver(simpMessagingTemplate);

        UserService userService = new UserService(userRepo);
        MatchService matchService = new MatchService(matchRepo,  wsObserver);
        ClientService clientService = new ClientService(clientRepo);
        TicketService ticketService = new TicketService(ticketRepo, matchRepo);

        Services serverImpl = new ServicesImpl(userService, matchService, clientService, ticketService);
        int port = serverProps.getProperty("grpc.port") != null ? Integer.parseInt(serverProps.getProperty("grpc.port")) : 55555;
        Server server = ServerBuilder.forPort(port)
                .addService(new ProjectGrpcService(serverImpl, userService))
                .build();

        try
        {
            server.start();
            server.awaitTermination();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}