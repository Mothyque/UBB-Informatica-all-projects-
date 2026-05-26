package org.example.service;

import org.example.domain.Client;
import org.example.domain.ClientTicketDTO;
import org.example.domain.Match;
import org.example.domain.Ticket;
import org.example.repository.MatchRepository;
import org.example.repository.TicketRepository;

import java.util.List;

public class TicketService extends Service<Integer, Ticket>
{
    private final TicketRepository ticketRepository;
    private final MatchRepository matchRepository;

    public TicketService(TicketRepository ticketRepository, MatchRepository matchRepository)
    {
        super(ticketRepository);
        this.ticketRepository = ticketRepository;
        this.matchRepository = matchRepository;
    }

    public Ticket getTicket(Integer clientId, Integer matchId)
    {
        int ticketId = ticketRepository.getTicketId(clientId, matchId);
        if (ticketId != -1)
        {
            return ticketRepository.findOne(ticketId).orElse(null);
        }
        return null;
    }

    public List<ClientTicketDTO> getTicketByNameAndAddress(String clientName, String clientAddress)
    {
        return  ticketRepository.getClientTicketsByCriteria(clientName, clientAddress);
    }

    public void removeTickets(Integer clientId, Integer matchId, int number)
    {
        for (int i = 0; i < number; i++)
        {
            int ticketId = ticketRepository.getTicketId(clientId, matchId);
            if (ticketId != -1)
            {
                ticketRepository.delete(ticketId);
            }
        }
        updateObservers();
    }

    public void updateClientTickets(Client client, Match match, int diff)
    {
        if (diff > 0)
        {
            int currentAvailable = match.getAvailableSeats();
            if (currentAvailable < diff)
            {
                throw new IllegalArgumentException("Not enough available seats for the match.");
            }
            for (int i = 0; i < diff; i++)
            {
                int seatNumber = match.getTotalSeats() - currentAvailable + i + 1;
                ticketRepository.save(new Ticket(client.getId(), match.getId(), "Seat " + seatNumber));
            }
        }
        else
        {
            removeTickets(client.getId(), match.getId(), Math.abs(diff));
        }
        match.setAvailableSeats(match.getAvailableSeats() - diff);
        matchRepository.update(match);
        updateObservers();
    }

    public List<ClientTicketDTO> getFilteredTickets(String name, String address)
    {
        return ticketRepository.getClientTicketsByCriteria(name, address);
    }
}
