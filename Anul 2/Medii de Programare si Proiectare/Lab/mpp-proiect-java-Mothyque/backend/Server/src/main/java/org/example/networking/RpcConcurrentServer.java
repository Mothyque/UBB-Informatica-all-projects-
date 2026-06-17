package org.example.networking;

import java.net.Socket;

public class RpcConcurrentServer extends AbstractServer
{
    private org.example.networking.Services serverInterface;

    public RpcConcurrentServer(int port, org.example.networking.Services serverInterface)
    {
        super(port);
        this.serverInterface = serverInterface;
        System.out.println("RpcConcurrentServer created on port" + port);
    }

    @Override
    protected void processRequest(Socket client)
    {
        ClientRpcWorker worker = new ClientRpcWorker(serverInterface, client);
        Thread tw = new Thread(worker);
        tw.start();
    }
}