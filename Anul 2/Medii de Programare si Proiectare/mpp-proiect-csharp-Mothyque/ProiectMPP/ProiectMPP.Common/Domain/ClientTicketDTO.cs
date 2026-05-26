using System;

namespace ProiectMPP.Common.Domain
{
    [Serializable]
    public class ClientTicketDTO : Entity<int>
    {
        public Client Client { get; set; }
        public Match Match { get; set; }
        public int NumberOfTickets { get; set; }

        public ClientTicketDTO() { }

        public ClientTicketDTO(Client client, Match match, int numberOfTickets)
        {
            Client = client;
            Match = match;
            NumberOfTickets = numberOfTickets;
        }

        public override string ToString()
        {
            return $"Client: {Client.Name}, Address: {Client.Address}, Match: {Match.ToString()}, Number of tickets: {NumberOfTickets}";
        }
    }
}
