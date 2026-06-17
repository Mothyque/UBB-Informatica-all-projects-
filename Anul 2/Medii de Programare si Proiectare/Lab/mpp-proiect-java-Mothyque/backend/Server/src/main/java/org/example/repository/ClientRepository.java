package org.example.repository;

import org.example.domain.Client;

import java.util.List;

public interface ClientRepository extends Repository<Integer, Client>
{
    List<Client> findClientsByNameAndAddress(String name, String address);

    Client findClientByNameAndAddress(String name, String address);
}
