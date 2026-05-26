package org.example.repository;

import org.example.domain.ClientTicketDTO;
import org.example.domain.Ticket;

import java.util.List;

public interface TicketRepository extends Repository<Integer, Ticket>
{
    int getNumberOfTicketsAtMatch(int clientId, int matchId);

    int getTicketId(int clientId, int matchId);

    List<ClientTicketDTO> getClientTicketsByCriteria(String name, String address);
}
