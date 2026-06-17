package org.example.service;

import org.example.domain.Client;
import org.example.domain.Ticket;
import org.example.repository.ClientRepository;

public class ClientService extends Service<Integer, Client>
{
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository)
    {
        super(clientRepository);
        this.clientRepository = clientRepository;
    }

    public Client findByNameAndAddress(String name, String address)
    {
        return clientRepository.findClientByNameAndAddress(name, address);
    }
}
