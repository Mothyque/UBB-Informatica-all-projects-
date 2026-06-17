using ProiectMPP.Common.Domain;
using ProiectMPP.Common.dto;
using ProiectMPP.Common.Networking;
using ProiectMPP.Common.Utils;
using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Net.Sockets;
using System.Threading;
using ClientEntity = ProiectMPP.Common.Domain.Client;

namespace ProiectMPP.Client
{
    public class ServicesRpcProxy : IServices
    {
        private string host;
        private int port;
        private IObserver client;
        private NetworkStream stream;
        private NetworkJsonSerializer serializer;
        private TcpClient connection;
        private BlockingCollection<Response> qresponses;
        private volatile bool finished;

        public ServicesRpcProxy(string host, int port)
        {
            this.host = host;
            this.port = port;
            qresponses = new BlockingCollection<Response>();
        }

        public virtual void Login(User user, IObserver client)
        {
            InitializeConnection();
            SendRequest(new Request(RequestType.LOGIN, user));
            Response response = ReadResponse();
            if (response.Type == ResponseType.OK)
            {
                this.client = client;
            }
            else
            {
                throw new Exception(response.Data.ToString());
            }
        }

        public virtual void Logout(User user, IObserver client)
        {
            SendRequest(new Request(RequestType.LOGOUT, user));
            Response response = ReadResponse();
            CloseConnection();
            if (response.Type == ResponseType.ERROR)
            {
                throw new Exception(response.Data.ToString());
            }
        }

        public virtual Match[] GetAllMatches()
        {
            SendRequest(new Request(RequestType.GET_ALL_MATCHES, null));
            Response response = ReadResponse();
            if (response.Type == ResponseType.ERROR)
            {
                throw new Exception(response.Data.ToString());
            }

            if (response.Data is Match[])
            {
                return (Match[])response.Data;
            }
            else if (response.Data is object[] objArray)
            {
                List<Match> matches = new List<Match>();
                foreach (var obj in objArray)
                {
                    if (obj is Match match)
                    {
                        matches.Add(match);
                    }
                }
                return matches.ToArray();
            }

            throw new Exception("Unexpected response data type: " + response.Data?.GetType().Name);
        }

        public virtual void BuyTickets(Match match, string clientName, string clientAddress, int numberOfTickets)
        {
            object[] data = new object[] { match, clientName, clientAddress, numberOfTickets };
            SendRequest(new Request(RequestType.BUY_TICKETS, data));
            Response response = ReadResponse();
            if (response.Type == ResponseType.ERROR)
            {
                throw new Exception(response.Data.ToString());
            }
        }

        public virtual IList<ClientTicketDTO> FilterTickets(string name, string address)
        {
            object[] data = new object[] { name, address };
            SendRequest(new Request(RequestType.FILTER_TICKETS, data));
            Response response = ReadResponse();
            if (response.Type == ResponseType.ERROR)
            {
                throw new Exception(response.Data.ToString());
            }

            if (response.Data is IList<ClientTicketDTO>)
            {
                return (IList<ClientTicketDTO>)response.Data;
            }
            else if (response.Data is object[] objArray)
            {
                    List<ClientTicketDTO> tickets = new List<ClientTicketDTO>();
                foreach (var obj in objArray)
                {
                    if (obj is ClientTicketDTO dto)
                    {
                        tickets.Add(dto);
                    }
                }
                return tickets;
            }

            throw new Exception("Unexpected response data type: " + response.Data?.GetType().Name);
        }

        public virtual void UpdateTickets(Match match, ProiectMPP.Common.Domain.Client client, int oldQuantity, int newQuantity)
        {
            object[] data = new object[] { match, client, oldQuantity, newQuantity };
            SendRequest(new Request(RequestType.UPDATE_TICKETS, data));
            Response response = ReadResponse();
            if (response.Type == ResponseType.ERROR)
            {
                throw new Exception(response.Data.ToString());
            }
        }

        protected virtual void CloseConnection()
        {
            finished = true;
            try
            {
                stream?.Close();
                connection?.Close();
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }

        private void HandleUpdate(Response response)
        {
            if (response.Type == ResponseType.UPDATE)
            {
                client.Update();
            }
        }

        private void SendRequest(Request request)
        {
            try
            {
                serializer.SerializeRequest(request);
            }
            catch (Exception e)
            {
                throw new Exception("Error sending request: " + e.Message);
            }
        }

        private void InitializeConnection()
        {
            try
            {
                connection = new TcpClient(host, port);
                stream = connection.GetStream();
                serializer = new NetworkJsonSerializer(stream);
                finished = false;
                StartReader();
            }
            catch (Exception e)
            {
                throw new Exception("Error initializing connection: " + e.Message);
            }
        }

        private void StartReader()
        {
            Thread tw = new Thread(RunReader);
            tw.Start();
        }

        private void RunReader()
        {
            while (!finished)
            {
                try
                {
                    Response res = serializer.DeserializeResponse();
                    if (IsUpdate(res))
                    {
                        HandleUpdate(res);
                    }
                    else
                    {
                        qresponses.Add(res);
                    }
                }
                catch (Exception e)
                {
                    if (!finished)
                    {
                        finished = true;
                        Response errorRes = new Response(ResponseType.ERROR, e.Message);
                        qresponses.Add(errorRes);
                        CloseConnection();
                    }
                }
            }
        }

        private Response ReadResponse()
        {
            Response response = null;
            try
            {
                if (qresponses.TryTake(out response, TimeSpan.FromSeconds(5)))
                {
                    return response;
                }
                else
                {
                    throw new Exception("Timeout waiting for server response.");
                }
            }
            catch (Exception)
            {
                throw;
            }
        }
        private bool IsUpdate(Response response)
        {
            return response.Type == ResponseType.UPDATE;
        }
    }
}
