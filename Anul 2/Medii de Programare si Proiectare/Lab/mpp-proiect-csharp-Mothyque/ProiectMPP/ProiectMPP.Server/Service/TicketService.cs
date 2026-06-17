using ProiectMPP.Common.Domain;
using ProiectMPP.Server.Repository;
using System;
using System.Collections.Generic;

namespace ProiectMPP.Server.Service
{
    public class TicketService : Service<int, Ticket>
    {
        private readonly ITicketRepository _ticketRepository;
        private readonly IMatchRepository _matchRepository;

        public TicketService(ITicketRepository ticketRepository, IMatchRepository matchRepository)
            : base(ticketRepository)
        {
            _ticketRepository = ticketRepository;
            _matchRepository = matchRepository;
        }

        public Ticket GetTicket(int clientId, int matchId)
        {
            int ticketId = _ticketRepository.GetTicketId(clientId, matchId);
            if (ticketId != -1)
            {
                return _ticketRepository.FindOne(ticketId);
            }
            return null;
        }

        public List<ClientTicketDTO> GetTicketByNameAndAddress(string clientName, string clientAddress)
        {
            return (List<ClientTicketDTO>)_ticketRepository.GetClientTicketsByCriteria(clientName, clientAddress);
        }

        public void RemoveTickets(int clientId, int matchId, int number)
        {
            for (int i = 0; i < number; i++)
            {
                int ticketId = _ticketRepository.GetTicketId(clientId, matchId);
                if (ticketId != -1)
                {
                    _ticketRepository.Delete(ticketId);
                }
            }
            UpdateObservers(); 
        }

        public void UpdateClientTickets(Client client, Match match, int diff)
        {
            if (diff > 0)
            {
                int currentAvailable = match.AvailableSeats;
                if (currentAvailable < diff)
                {
                    throw new ArgumentException("Not enough available seats for the match.");
                }

                for (int i = 0; i < diff; i++)
                {
                    int seatNumber = match.TotalSeats - currentAvailable + i + 1;
                    _ticketRepository.Save(new Ticket(client.Id, match.Id, "Seat " + seatNumber));
                }
            }
            else
            {
                RemoveTickets(client.Id, match.Id, Math.Abs(diff));
            }

            match.AvailableSeats -= diff;
            _matchRepository.Update(match);
            UpdateObservers();
        }

        public List<ClientTicketDTO> GetFilteredTickets(string name, string address)
        {
            return (List<ClientTicketDTO>)_ticketRepository.GetClientTicketsByCriteria(name, address);
        }
    }
}