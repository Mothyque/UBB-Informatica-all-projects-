using ProiectMPP.Common.Domain; 
using ProiectMPP.Common.dto;
using ProiectMPP.Common.Networking;
using ProiectMPP.Common.Utils;
using System;
using System.Collections.Generic;
using System.Net.Sockets;
using System.Threading;

namespace ProiectMPP.Server.Networking
{
    public class ClientRpcWorker : IObserver
    {
        private IServices server;
        private TcpClient connection;
        private NetworkStream stream;
        private NetworkJsonSerializer serializer;
        private volatile bool connected;

        public ClientRpcWorker(IServices server, TcpClient connection)
        {
            this.server = server;
            this.connection = connection;
            try
            {
                stream = connection.GetStream();
                serializer = new NetworkJsonSerializer(stream);
                connected = true;
            }
            catch (Exception e)
            {
            }
        }

        public virtual void Run()
        {
            while (connected)
            {
                try
                {
                    Request request = serializer.DeserializeRequest();
                    Response response = HandleRequest(request);
                    if (response != null)
                    {
                        SendResponse(response);
                    }
                }
                catch (Exception e)
                {
                    break;
                }
            }
            CloseConnection();
        }

        public void Update()
        {
            Response response = new Response(ResponseType.UPDATE, null);
            try
            {
                SendResponse(response);
            }
            catch (Exception e)
            {
            }
        }

        private void SendResponse(Response response)
        {
            try
            {
                lock (stream)
                {
                    serializer.SerializeResponse(response);
                }
            }
            catch (Exception e)
            {
                Response errorResponse = new Response(ResponseType.ERROR, e.Message);
                try
                {
                    lock (stream)
                    {
                        serializer.SerializeResponse(errorResponse);
                    }
                }
                catch (Exception ex)
                {
                }
            }
        }

        private void CloseConnection()
        {
            try
            {
                connected = false;
                stream.Close();
                connection.Close();
            }
            catch (Exception e)
            {
            }
        }

        private Response HandleRequest(Request request)
        {
            if (request.Type == RequestType.LOGIN)
            {
                User user = (User)request.Data;
                try
                {
                    server.Login(user, this);
                    return new Response(ResponseType.OK, null);
                }
                catch (Exception e)
                {
                    connected = false;
                    return new Response(ResponseType.ERROR, e.Message);
                }
            }

            if (request.Type == RequestType.GET_ALL_MATCHES)
            {
                try
                {
                    Match[] matches = server.GetAllMatches();
                    return new Response(ResponseType.GET_ALL_MATCHES, matches);
                }
                catch (Exception e)
                {
                    return new Response(ResponseType.ERROR, e.Message);
                }
            }

            if (request.Type == RequestType.LOGOUT)
            {
                User user = (User)request.Data;
                try
                {
                    server.Logout(user, this);
                    connected = false;
                    return new Response(ResponseType.OK, null);
                }
                catch (Exception e)
                {
                    return new Response(ResponseType.ERROR, e.Message);
                }
            }

            if (request.Type == RequestType.BUY_TICKETS)
            {
                object[] data = (object[])request.Data;
                Match match = null;
                string clientName = null;
                string clientAddress = null;
                int quantity = 0;

                try
                {
                    if (data[0] is Match m)
                        match = m;
                    else if (data[0] is Dictionary<string, object> matchDict)
                        match = ConvertToMatch(matchDict);
                    else
                        throw new Exception($"Expected Match for data[0], got {data[0]?.GetType().Name}");

                    if (data[1] is string name)
                        clientName = name;
                    else
                        throw new Exception($"Expected string for clientName, got {data[1]?.GetType().Name}");

                    if (data[2] is string address)
                        clientAddress = address;
                    else
                        throw new Exception($"Expected string for clientAddress, got {data[2]?.GetType().Name}");

                    if (data[3] is int qty)
                        quantity = qty;
                    else
                        throw new Exception($"Expected int for quantity, got {data[3]?.GetType().Name}");

                    server.BuyTickets(match, clientName, clientAddress, quantity);
                    return new Response(ResponseType.OK, null);
                }
                catch (Exception e)
                {
                    return new Response(ResponseType.ERROR, e.Message);
                }
            }

            if (request.Type == RequestType.FILTER_TICKETS)
            {
                object[] data = (object[])request.Data;
                string name = (string)data[0];
                string address = (string)data[1];

                try
                {
                    IList<ClientTicketDTO> filtered = server.FilterTickets(name, address);
                    return new Response(ResponseType.FILTER_TICKETS, filtered);
                }
                catch (Exception e)
                {
                    return new Response(ResponseType.ERROR, e.Message);
                }
            }

            if (request.Type == RequestType.UPDATE_TICKETS)
            {
                object[] data = (object[])request.Data;

                Match match = null;
                Client client = null;
                int oldQuantity = 0;
                int newQuantity = 0;

                try
                {
                    if (data[0] is Match m)
                        match = m;
                    else if (data[0] is Dictionary<string, object> matchDict)
                        match = ConvertToMatch(matchDict);
                    else
                        throw new Exception($"Expected Match for data[0], got {data[0]?.GetType().Name}");

                    if (data[1] is Client c)
                        client = c;
                    else if (data[1] is Dictionary<string, object> clientDict)
                        client = ConvertToClient(clientDict);
                    else
                        throw new Exception($"Expected Client for data[1], got {data[1]?.GetType().Name}");

                    if (data[2] is int oq)
                        oldQuantity = oq;
                    else
                        throw new Exception($"Expected int for oldQuantity, got {data[2]?.GetType().Name}");

                    if (data[3] is int nq)
                        newQuantity = nq;
                    else
                        throw new Exception($"Expected int for newQuantity, got {data[3]?.GetType().Name}");

                    server.UpdateTickets(match, client, oldQuantity, newQuantity);
                    return new Response(ResponseType.OK, null);
                }
                catch (Exception e)
                {
                    return new Response(ResponseType.ERROR, e.Message);
                }
            }

            return null;
        }

        private Match ConvertToMatch(Dictionary<string, object> dict)
        {
            var match = new Match();
            if (dict.TryGetValue("id", out var id) && id is int idInt)
                match.Id = idInt;
            if (dict.TryGetValue("teamA", out var teamA))
                match.TeamA = teamA?.ToString();
            if (dict.TryGetValue("teamB", out var teamB))
                match.TeamB = teamB?.ToString();
            if (dict.TryGetValue("matchType", out var matchType))
                match.MatchType = matchType?.ToString();
            if (dict.TryGetValue("ticketPrice", out var ticketPrice) && double.TryParse(ticketPrice?.ToString(), out var price))
                match.TicketPrice = price;
            if (dict.TryGetValue("totalSeats", out var totalSeats) && int.TryParse(totalSeats?.ToString(), out var total))
                match.TotalSeats = total;
            if (dict.TryGetValue("availableSeats", out var availableSeats) && int.TryParse(availableSeats?.ToString(), out var available))
                match.AvailableSeats = available;
            return match;
        }

        private Client ConvertToClient(Dictionary<string, object> dict)
        {
            var client = new Client();
            if (dict.TryGetValue("id", out var id) && id is int idInt)
                client.Id = idInt;
            if (dict.TryGetValue("name", out var name))
                client.Name = name?.ToString();
            if (dict.TryGetValue("address", out var address))
                client.Address = address?.ToString();
            return client;
        }
    }
}