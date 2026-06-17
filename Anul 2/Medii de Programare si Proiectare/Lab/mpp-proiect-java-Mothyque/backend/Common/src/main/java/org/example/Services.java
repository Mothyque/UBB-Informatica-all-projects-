package org.example.networking;

import org.example.domain.Client;
import org.example.domain.ClientTicketDTO;
import org.example.domain.Match;
import org.example.domain.User;
import org.example.utils.Observer;

import java.util.List;

public interface Services
{
    void login(User user, Observer client) throws Exception;
    void logout(User user, Observer client) throws Exception;
    Match[] getAllMatches() throws Exception;
    void buyTickets(Match match, String clientName, String clientAddress, int numberOfTickets) throws Exception;
    List<ClientTicketDTO> filterTickets(String name, String address) throws Exception;
    void updateTickets(Match match, Client client, int oldQuantity, int newQuantity) throws Exception;
    void addObserver(Integer userId, Observer client);
}