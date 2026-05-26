using System;
using System.Net;
using System.Net.Sockets;

namespace ProiectMPP.Server.Networking
{
    public abstract class AbstractServer
    {
        private int port;
        private TcpListener server = null;

        public AbstractServer(int port)
        {
            this.port = port;
        }

        public void Start()
        {
            try
            {
                server = new TcpListener(IPAddress.Any, port);
                server.Start();

                while (true)
                {
                    Console.WriteLine("Waiting for clients...");
                    TcpClient client = server.AcceptTcpClient();
                    Console.WriteLine("Client connected...");
                    ProcessRequest(client);
                }
            }
            catch (SocketException e)
            {
                throw new Exception("Starting server error: " + e.Message);
            }
            finally
            {
                Stop();
            }
        }

        protected abstract void ProcessRequest(TcpClient client);

        public void Stop()
        {
            try
            {
                server?.Stop();
            }
            catch (SocketException e)
            {
                throw new Exception("Closing server error: " + e.Message);
            }
        }
    }
}