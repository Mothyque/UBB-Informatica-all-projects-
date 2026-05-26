package org.example.networking.grpc;

import org.example.domain.ClientTicketDTO;
import org.example.domain.Match;
import org.example.domain.User;
import org.example.domain.Client;
import org.example.networking.protobuff.ProjectProtobufs;

public class ProtoUtils
{
    public static User getModel (ProjectProtobufs.User userProto)
    {
        User user = new User(userProto.getName(), userProto.getPassword());
        user.setId(userProto.getId());
        return user;
    }

    public static ProjectProtobufs.User getProto (User user)
    {
        return ProjectProtobufs.User.newBuilder()
                .setId(user.getId())
                .setName(user.getUsername())
                .setPassword(user.getPassword())
                .build();
    }

    public static Match getModel(ProjectProtobufs.Match matchProto)
    {
        Match match = new Match(matchProto.getTeamA(), matchProto.getTeamB(), matchProto.getMatchType(), matchProto.getTicketPrice(), matchProto.getTotalSeats(), matchProto.getAvailableSeats());
        match.setId(matchProto.getId());
        return match;
    }

    public static ProjectProtobufs.Match getProto(Match match)
    {
        return ProjectProtobufs.Match.newBuilder()
                .setId(match.getId())
                .setTeamA(match.getTeamA())
                .setTeamB(match.getTeamB())
                .setMatchType(match.getMatchType())
                .setTicketPrice(match.getTicketPrice())
                .setTotalSeats(match.getTotalSeats())
                .setAvailableSeats(match.getAvailableSeats())
                .build();
    }

    public static ProjectProtobufs.ClientTicketDTO getProto(ClientTicketDTO dto)
    {
        return ProjectProtobufs.ClientTicketDTO.newBuilder()
                .setClient(getProto(dto.getClient()))
                .setMatch(getProto(dto.getMatch()))
                .setNumberOfTickets(dto.getNumberOfTickets())
                .build();
    }

    public static ProjectProtobufs.Client getProto(Client client)
    {
        return ProjectProtobufs.Client.newBuilder()
                .setId(client.getId())
                .setName(client.getName())
                .setAddress(client.getAddress())
                .build();
    }

    public static Client getModel (ProjectProtobufs.Client clientProto)
    {
        Client client = new Client(clientProto.getName(), clientProto.getAddress());
        client.setId(clientProto.getId());
        return client;
    }
}