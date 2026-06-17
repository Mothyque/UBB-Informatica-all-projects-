using ProiectMPP.Common.Domain;
using ProiectMPP.Common.dto;
using ProiectMPP.Common.Networking;
using ProiectMPP.Common.Utils;
using ProiectMPP.Server.Repository;
using ProiectMPP.Server.Service;
using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Linq;

namespace ProiectMPP.Server
{
    public class ServicesImpl : IServices
    {
        private UserService userService;
        private MatchService matchService;
        private ClientService clientService;
        private TicketService ticketService;
        private IDictionary<int, IObserver> loggedClients;

        public ServicesImpl(UserService userService, MatchService matchService,
                            ClientService clientService, TicketService ticketService)
        {
            this.userService = userService;
            this.matchService = matchService;
            this.clientService = clientService;
            this.ticketService = ticketService;
            loggedClients = new ConcurrentDictionary<int, IObserver>();
        }

        public void Login(User user, IObserver client)
        {

            lock (this)
            {
                if (userService == null)
                {
                    throw new Exception("Server internal error: userService null.");
                }

                User userFound = userService.FindByUsername(user.Username);

                if (userFound != null)
                {
                    if (loggedClients.ContainsKey(userFound.Id))
                    {
                        throw new Exception("User already logged in.");
                    }

                    User loggedUser = userService.Authenticate(user.Username, user.Password);

                    if (loggedUser != null)
                    {
                        loggedClients[loggedUser.Id] = client;
                    }
                    else
                    {
                        throw new Exception("Invalid username or password.");
                    }
                }
                else
                {
                    throw new Exception("User not found.");
                }
            }
        }

        public void Logout(User user, IObserver client)
        {
            lock (this)
            {
                if (SharedConnectionManager.HasActiveTransaction())
                {
                    try
                    {
                        SharedConnectionManager.RollbackTransaction();
                    }
                    catch (Exception ex)
                    {
                    }
                }

                User userFound = null;
                try
                {
                    userFound = userService.FindByUsername(user.Username);
                }
                catch (Exception ex)
                {
                }

                if (userFound != null && loggedClients.Remove(userFound.Id))
                {
                    return;
                }

                throw new Exception("User not logged in.");
            }
        }

        public Match[] GetAllMatches()
        {
            try
            {
                IEnumerable<Match> matches = matchService.FindAll();
                return matches.ToArray();
            }
            catch (Exception e)
            {
                throw new Exception("Error retrieving matches: " + e.Message);
            }
        }

        public void BuyTickets(Match match, string clientName, string clientAddress, int numberOfTickets)
        {
            lock (this)
            {
                try
                {
                    ticketService.BeginTransaction();

                    Match selectedMatch = matchService.FindOne(match.Id) ??
                                         throw new Exception("Match not found.");

                    if (selectedMatch.AvailableSeats < numberOfTickets)
                    {
                        throw new Exception("Not enough available seats for the selected match.");
                    }

                    Client client = clientService.FindByNameAndAddress(clientName, clientAddress);
                    if (client == null)
                    {
                        client = new Client(clientName, clientAddress);
                        clientService.Add(client);
                        client = clientService.FindByNameAndAddress(clientName, clientAddress);
                    }

                    int currentAvailable = selectedMatch.AvailableSeats;
                    for (int i = 0; i < numberOfTickets; i++)
                    {
                        int seatNumber = selectedMatch.TotalSeats - currentAvailable + i + 1;
                        Ticket newTicket = new Ticket(client.Id, selectedMatch.Id, "Seat " + seatNumber);
                        ticketService.Add(newTicket);
                    }

                    selectedMatch.AvailableSeats = currentAvailable - numberOfTickets;
                    matchService.Update(selectedMatch);

                    ticketService.CommitTransaction();
                }
                catch (Exception ex)
                {
                    ticketService.RollbackTransaction();
                    throw;
                }
            }
            NotifyAllClients();
        }

        public IList<ClientTicketDTO> FilterTickets(string name, string address)
        {
            try
            {
                return ticketService.GetTicketByNameAndAddress(name, address).ToList();
            }
            catch (Exception e)
            {
                throw new Exception("Error filtering tickets: " + e.Message);
            }
        }

        public void UpdateTickets(Match match, Client client, int oldQuantity, int newQuantity)
        {
            lock (this)
            {
                try
                {
                    ticketService.BeginTransaction();

                    Match matchDb = matchService.FindOne(match.Id);
                    if (matchDb == null)
                    {
                        throw new Exception("Match not found.");
                    }

                    int diff = newQuantity - oldQuantity;

                    if (diff > 0 && matchDb.AvailableSeats < diff)
                    {
                        throw new Exception("Not enough available seats for the match.");
                    }

                    try
                    {
                        ticketService.UpdateClientTickets(client, matchDb, diff);
                    }
                    catch (Exception ex)
                    {
                        throw;
                    }

                    ticketService.CommitTransaction();
                }
                catch (Exception ex)
                {
                    ticketService.RollbackTransaction();
                    throw;
                }
            }
            NotifyAllClients();
        }

        private void NotifyAllClients()
        {
            foreach (var client in loggedClients.Values)
            {
                try
                {
                    client.Update();
                }
                catch (Exception e)
                {
                    Console.Error.WriteLine("Error notifying client: " + e.Message);
                }
            }
        }
    }
}