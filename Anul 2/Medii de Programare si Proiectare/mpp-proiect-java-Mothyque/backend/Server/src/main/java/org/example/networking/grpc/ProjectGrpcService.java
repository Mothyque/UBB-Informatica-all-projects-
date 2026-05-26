package org.example.networking.grpc;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.example.domain.ClientTicketDTO;
import org.example.domain.Match;
import org.example.domain.User;
import org.example.networking.Services;
import org.example.networking.protobuff.ProjectProtobufs;
import org.example.networking.protobuff.ProjectServiceGrpc;
import org.example.service.UserService;
import org.example.utils.Observer;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class ProjectGrpcService extends ProjectServiceGrpc.ProjectServiceImplBase {
    private final Services serverLogic;
    private final UserService userService;
    private final Map<Integer, StreamObserver<ProjectProtobufs.Empty>> loggedClients = new ConcurrentHashMap<>();

    public ProjectGrpcService(Services serverLogic, UserService userService) {
        this.serverLogic = serverLogic;
        this.userService = userService;
    }

    @Override
    public void login(ProjectProtobufs.User request, StreamObserver<ProjectProtobufs.User> responseObserver) {
        User user = ProtoUtils.getModel(request);
        try {
            serverLogic.login(user, null);
            User loggedUser = userService.findByUsername(user.getUsername());
            responseObserver.onNext(ProtoUtils.getProto(loggedUser));
            responseObserver.onCompleted();
        } catch (Exception ex) {
            responseObserver.onError(Status.INTERNAL
                    .withDescription(ex.getMessage())
                    .asRuntimeException()
            );
        }
    }

    @Override
    public void listenForUpdates(ProjectProtobufs.User request, StreamObserver<ProjectProtobufs.Empty> responseObserver)
    {
        Integer userId = request.getId();

        loggedClients.put(userId, responseObserver);

        Observer gRpcObserver = new Observer() {
            @Override
            public void update()
            {
                try
                {
                    responseObserver.onNext(ProjectProtobufs.Empty.getDefaultInstance());
                }
                catch (Exception ex)
                {
                    System.err.println("Error sending update to user " + userId + ": " + ex.getMessage());
                    loggedClients.remove(userId);
                }
            }
        };

        try
        {
            serverLogic.addObserver(userId, gRpcObserver);
        }
        catch (Exception ex)
        {
            responseObserver.onError(Status.INTERNAL
                    .withDescription(ex.getMessage())
                    .asRuntimeException()
            );
        }
    }

    private void handleUpdate()
    {
        ProjectProtobufs.Empty emptyResponse = ProjectProtobufs.Empty.newBuilder().build();
        for (Map.Entry<Integer, StreamObserver<ProjectProtobufs.Empty>> entry : loggedClients.entrySet())
        {
            try
            {
                entry.getValue().onNext(emptyResponse);
            }
            catch (Exception ex)
            {
                System.err.println("Error notifying user " + entry.getKey() + ": " + ex.getMessage());
                loggedClients.remove(entry.getKey());
            }
        }
    }

    @Override
    public void logout(ProjectProtobufs.User request, StreamObserver<ProjectProtobufs.Empty> responseObserver) {
        User user = ProtoUtils.getModel(request);
        Integer userId = request.getId();
        try {
            serverLogic.logout(user, null);
            loggedClients.remove(userId);
            responseObserver.onNext(ProjectProtobufs.Empty.getDefaultInstance());
            responseObserver.onCompleted();
        } catch (Exception ex) {
            responseObserver.onError(Status.INTERNAL
                    .withDescription(ex.getMessage())
                    .asRuntimeException()
            );
        }
    }

    @Override
    public void getAllMatches(ProjectProtobufs.Empty request, StreamObserver<ProjectProtobufs.MatchList> responseObserver)
    {
        try
        {
            Match[] matches = serverLogic.getAllMatches();
            ProjectProtobufs.MatchList.Builder matchListBuilder = ProjectProtobufs.MatchList.newBuilder();
            for (Match match : matches)
            {
                matchListBuilder.addMatches(ProtoUtils.getProto(match));
            }
            responseObserver.onNext(matchListBuilder.build());
            responseObserver.onCompleted();
        }
        catch (Exception ex)
        {
            responseObserver.onError(Status.INTERNAL
                    .withDescription(ex.getMessage())
                    .asRuntimeException()
            );
        }
    }

    @Override
    public void buyTicket(ProjectProtobufs.BuyTicketRequest request, StreamObserver<ProjectProtobufs.Empty> responseObserver)
    {
        try
        {
            Match match = ProtoUtils.getModel(request.getMatch());
            serverLogic.buyTickets(match, request.getClientName(), request.getClientAddress(), request.getNumberOfTickets());
            handleUpdate();
            responseObserver.onNext(ProjectProtobufs.Empty.getDefaultInstance());
            responseObserver.onCompleted();
        }
        catch (Exception ex)
        {
            responseObserver.onError(Status.INTERNAL
                    .withDescription(ex.getMessage())
                    .asRuntimeException()
            );
        }
    }

    @Override
    public void filterTickets(ProjectProtobufs.FilterRequest request, StreamObserver<ProjectProtobufs.TicketList> responseObserver) {
        try
        {
            List<ClientTicketDTO> dtos = serverLogic.filterTickets(request.getName(), request.getAddress());
            ProjectProtobufs.TicketList.Builder builder = ProjectProtobufs.TicketList.newBuilder();
            for (ClientTicketDTO dto : dtos)
            {
                builder.addTickets(ProtoUtils.getProto(dto));
            }
            responseObserver.onNext(builder.build());
            responseObserver.onCompleted();
        }
        catch (Exception e)
        {
            responseObserver.onError(io.grpc.Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
        }
    }

    @Override
    public void updateTicket(ProjectProtobufs.UpdateTicketRequest request, StreamObserver<ProjectProtobufs.Empty> responseObserver)
    {
        try
        {
            Match match = ProtoUtils.getModel(request.getMatch());
            org.example.domain.Client client = ProtoUtils.getModel(request.getClient());
            serverLogic.updateTickets(match, client, request.getOldQuantity(), request.getNewQuantity());
            handleUpdate();
            responseObserver.onNext(ProjectProtobufs.Empty.getDefaultInstance());
            responseObserver.onCompleted();
        }
        catch (Exception e)
        {
            responseObserver.onError(io.grpc.Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
        }
    }

}