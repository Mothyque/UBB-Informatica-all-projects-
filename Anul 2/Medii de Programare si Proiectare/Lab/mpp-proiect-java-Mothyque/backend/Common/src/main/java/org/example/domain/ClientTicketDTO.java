package org.example.domain;

public class ClientTicketDTO extends Entity<Integer>
{
    private Client client;
    private Match match;
    private int numberOfTickets;

    public ClientTicketDTO(Client client, Match match, int numberOfTickets)
    {
        this.client = client;
        this.match = match;
        this.numberOfTickets = numberOfTickets;
    }

    public Client getClient()
    {
        return client;
    }

    public Match getMatch()
    {
        return match;
    }

    public int getNumberOfTickets()
    {
        return numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets)
    {
        this.numberOfTickets = numberOfTickets;
    }

    @Override
    public String toString()
    {
        return "Client: " + client.getName() + ", Address: " + client.getAddress() + ", Match: " + match.toString() + ", Number of tickets: " + numberOfTickets;
    }
}
