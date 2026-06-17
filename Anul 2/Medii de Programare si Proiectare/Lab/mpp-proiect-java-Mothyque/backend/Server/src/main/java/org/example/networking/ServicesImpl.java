package org.example.networking;

import org.example.domain.*;
import org.example.service.ClientService;
import org.example.service.MatchService;
import org.example.service.TicketService;
import org.example.service.UserService;
import org.example.utils.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ServicesImpl implements org.example.networking.Services
{
    private UserService userService;
    private MatchService matchService;
    private ClientService clientService;
    private TicketService ticketService;
    private Map<Integer, Observer> loggedClients;
    private Set<Integer> activeUsersIds;

    public ServicesImpl(UserService userService, MatchService matchService, ClientService clientService, TicketService ticketService) 
    {
        this.userService = userService;
        this.matchService = matchService;
        this.clientService = clientService;
        this.ticketService = ticketService;
        loggedClients = new ConcurrentHashMap<>();
        activeUsersIds = ConcurrentHashMap.newKeySet();
    }

    @Override
    public synchronized void login(User user, Observer client) throws Exception
    {
        User userFound = userService.findByUsername(user.getUsername());
        if (userFound != null)
        {
            if(activeUsersIds.contains(userFound.getId()))
            {
                throw new Exception("User already logged in.");
            }
            else
            {
                User loggedUser = userService.authenticate(user.getUsername(), user.getPassword());
                if (loggedUser != null)
                {
                    activeUsersIds.add(loggedUser.getId());
                    if (client != null)
                    {
                        loggedClients.put(loggedUser.getId(), client);
                    }
                }
                else
                {
                    throw new Exception("Invalid username or password.");
                }
            }

        }
    }

    @Override
    public synchronized void logout(User user, Observer client) throws Exception
    {
        User userFound = userService.findByUsername(user.getUsername());
        if (userFound == null) return;

        activeUsersIds.remove(userFound.getId());
        loggedClients.remove(userFound.getId());
    }

    @Override
    public Match[] getAllMatches() throws Exception
    {
        try
        {
            Iterable<Match> matches = matchService.findAll();
            List<Match> matchList = new ArrayList<>();
            matches.forEach(matchList::add);
            return matchList.toArray(new Match[0]);
        }
        catch (Exception e)
        {
            throw new Exception("Error retrieving matches: " + e.getMessage());
        }
    }

    @Override
    public synchronized void buyTickets(Match match, String clientName, String clientAddress, int numberOfTickets) throws Exception
    {
        Match selectedMatch = matchService.findOne(match.getId()).orElseThrow(() -> new Exception("Match not found."));
        if (selectedMatch.getAvailableSeats() < numberOfTickets)
        {
            throw new Exception("Not enough available seats for the selected match.");
        }
        Client client = clientService.findByNameAndAddress(clientName, clientAddress);
        if (client == null)
        {
            client = new Client(clientName, clientAddress);
            clientService.add(client);
            client = clientService.findByNameAndAddress(clientName, clientAddress);
        }

        int currentAvailable = selectedMatch.getAvailableSeats();
        for (int i = 0; i < numberOfTickets; i++)
        {
            int seatNumber = selectedMatch.getTotalSeats() - currentAvailable + i + 1;
            Ticket newTicket = new Ticket(client.getId(), selectedMatch.getId(), "Seat " + seatNumber);
            ticketService.add(newTicket);
        }

        selectedMatch.setAvailableSeats(currentAvailable - numberOfTickets);
        matchService.update(selectedMatch);

        notifyAllClients();
    }

    @Override
    public List<ClientTicketDTO> filterTickets(String name, String address) throws Exception
    {
        try
        {
            return ticketService.getTicketByNameAndAddress(name, address);
        }
        catch (Exception e)
        {
            throw new Exception("Error filtering tickets: " + e.getMessage());
        }
    }

    @Override
    public synchronized void updateTickets(Match match, Client client, int oldQuantity, int newQuantity) throws Exception
    {
        Match matchDb = matchService.findOne(match.getId()).orElseThrow(() -> new Exception("Match not found."));
        int diff = newQuantity - oldQuantity;

        if (diff > 0 && matchDb.getAvailableSeats() < diff)
        {
            throw new Exception("Not enough available seats for the match.");
        }

        ticketService.updateClientTickets(client, matchDb, diff);
        notifyAllClients();
    }

    @Override
    public synchronized void addObserver(Integer userId, Observer client)
    {
        loggedClients.put(userId, client);
    }

    private void notifyAllClients()
    {
        for (Map.Entry<Integer, Observer> entry : loggedClients.entrySet())
        {
            try
            {
                entry.getValue().update();
            }
            catch (Exception e)
            {
                System.err.println("Error notifying client: " + e.getMessage());
            }
        }
    }
}