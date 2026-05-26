using ProiectMPP.Common.Networking;
using System;
using System.Net.Sockets;
using System.Threading;

namespace ProiectMPP.Server.Networking
{
    public class RpcConcurrentServer : AbstractServer
    {
        private IServices serverInterface;

        public RpcConcurrentServer(int port, IServices serverInterface)
            : base(port)
        {
            this.serverInterface = serverInterface;
        }

        protected override void ProcessRequest(TcpClient client)
        {
            ClientRpcWorker worker = new ClientRpcWorker(serverInterface, client);

            Thread tw = new Thread(new ThreadStart(worker.Run));
            tw.Start();
        }
    }
}