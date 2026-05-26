using Grpc.Core;
using Grpc.Net.Client;
using ProiectMPP.Common.Domain;
using ProiectMPP.Common.Networking;
using ProiectMPP.Common.Utils;
using Proto;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Windows;

namespace ProiectMPP.Client;

public class GrpcServicesProxy : IServices
{
    private readonly ProjectService.ProjectServiceClient _client;
    private readonly GrpcChannel _channel;
    private IObserver? _observer;

    public GrpcServicesProxy(string host, int port)
    {
        _channel = GrpcChannel.ForAddress($"http://{host}:{port}");
        _client = new ProjectService.ProjectServiceClient(_channel);
    }

    public void Login(ProiectMPP.Common.Domain.User user, IObserver client)
    {
        var request = new Proto.User { Name = user.Username, Password = user.Password };
        try
        {
            var identity = _client.Login(request);

            user.Id = identity.Id;
            _observer = client;

            Task.Run(() => StartHandlingUpdates(user));
        }
        catch (Exception e)
        {
            throw new Exception("Login failed: " + e.Message);
        }
    }

    public void Logout(ProiectMPP.Common.Domain.User user, IObserver client)
    {
        try 
        {
            _client.Logout(new Proto.User { Name = user.Username });
            _observer = null;
        } 
        catch (Exception e) 
        {
            throw new Exception("Logout error: " + e.Message);
        }
    }

    public ProiectMPP.Common.Domain.Match[] GetAllMatches()
    {
        try 
        {
            var response = _client.GetAllMatches(new Empty());
            return response.Matches.Select(m =>
                new ProiectMPP.Common.Domain.Match(m.TeamA, m.TeamB, m.MatchType, m.TicketPrice, m.TotalSeats, m.AvailableSeats)
                    { Id = m.Id }).ToArray();
        } 
        catch (Exception e) 
        {
            throw new Exception("Error fetching matches: " + e.Message);
        }
    }

    public void BuyTickets(ProiectMPP.Common.Domain.Match match, string clientName, string clientAddress, int numberOfTickets)
    {
        var request = new BuyTicketRequest {
            Match = new Proto.Match { Id = match.Id },
            ClientName = clientName,
            ClientAddress = clientAddress,
            NumberOfTickets = numberOfTickets
        };
        try 
        {
            _client.BuyTicket(request);
        } 
        catch (Exception e) 
        {
            throw new Exception("Purchase failed: " + e.Message);
        }
    }

    public IList<ProiectMPP.Common.Domain.ClientTicketDTO> FilterTickets(string name, string address)
    {
        try 
        {
            var request = new FilterRequest { Name = name, Address = address };
            var response = _client.FilterTickets(request);
            
            return response.Tickets.Select(t => new ProiectMPP.Common.Domain.ClientTicketDTO {
                Client = new ProiectMPP.Common.Domain.Client(t.Client.Name, t.Client.Address) { Id = t.Client.Id },
                Match = new ProiectMPP.Common.Domain.Match(t.Match.TeamA, t.Match.TeamB, t.Match.MatchType, t.Match.TicketPrice, t.Match.TotalSeats, t.Match.AvailableSeats) { Id = t.Match.Id },
                NumberOfTickets = t.NumberOfTickets
            }).ToList();
        } 
        catch (Exception e) 
        {
            throw new Exception("Filtering failed: " + e.Message);
        }
    }

    public void UpdateTickets(ProiectMPP.Common.Domain.Match match, ProiectMPP.Common.Domain.Client client, int oldQuantity, int newQuantity)
    {
        var request = new UpdateTicketRequest
        {
            Match = new Proto.Match { Id = match?.Id ?? 0 },
            Client = new Proto.Client
            {
                Id = client?.Id ?? 0,
                Name = client?.Name ?? "",
                Address = client?.Address ?? ""
            },
            OldQuantity = oldQuantity,
            NewQuantity = newQuantity
        };

        try
        {
            _client.UpdateTicket(request);
        }
        catch (RpcException e)
        {
            throw new Exception(e.Status.Detail);
        }
    }

    private async Task StartHandlingUpdates(ProiectMPP.Common.Domain.User user)
    {
        var request = new Proto.User { Id = user.Id, Name = user.Username };

        try
        {
            using var call = _client.ListenForUpdates(request);
            while (await call.ResponseStream.MoveNext(System.Threading.CancellationToken.None))
            {
                Application.Current.Dispatcher.Invoke(() =>
                {
                    _observer?.Update();
                });
            }
        }
        catch (Exception e)
        {
            Console.WriteLine("Stream closed: " + e.Message);
        }
    }
}