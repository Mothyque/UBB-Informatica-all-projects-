package org.example.networking;

import org.example.domain.Client;
import org.example.domain.ClientTicketDTO;
import org.example.domain.Match;
import org.example.domain.User;
import org.example.dto.Request;
import org.example.dto.RequestType;
import org.example.dto.Response;
import org.example.dto.ResponseType;
import org.example.utils.Observer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ClientRpcWorker implements Runnable, Observer
{
    private org.example.networking.Services server;
    private Socket connection;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;

    public ClientRpcWorker(org.example.networking.Services server, Socket connection)
    {
        this.server = server;
        this.connection = connection;
        try
        {
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            connected = true;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void run()
    {
        while(connected)
        {
            try
            {
                Object request = input.readObject();
                Response response = handleRequest((Request) request);
                if (response != null)
                {
                    sendResponse(response);
                }
            }
            catch (IOException | ClassNotFoundException e)
            {
                e.printStackTrace();
                break;
            }
        }
        closeConnection();
    }

    @Override
    public void update()
    {
        Response response = new Response(ResponseType.UPDATE, null);
        try
        {
            sendResponse(response);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void sendResponse(Response response) throws IOException
    {
        synchronized (output)
        {
            output.writeObject(response);
            output.flush();
        }
    }

    private void closeConnection()
    {
        try
        {
            input.close();
            output.close();
            connection.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private Response handleRequest(Request request)
    {
        if(request.getType() == RequestType.LOGIN)
        {
            User user = (User) request.getData();
            try
            {
                server.login(user, this);
                return new Response(ResponseType.OK, null);
            }
            catch (Exception e)
            {
                connected = false;
                return new Response(ResponseType.ERROR, e.getMessage());
            }
        }

        if (request.getType() == RequestType.GET_ALL_MATCHES)
        {
            try
            {
                Match[] matches = server.getAllMatches();
                return new Response(ResponseType.GET_ALL_MATCHES, matches);
            }
            catch(Exception e)
            {
                return new Response(ResponseType.ERROR, e.getMessage());
            }
        }

        if (request.getType() == RequestType.LOGOUT)
        {
            User user = (User) request.getData();
            try
            {
                server.logout(user, this);
                connected = false;
                return new Response(ResponseType.OK, null);
            }
            catch (Exception e)
            {
                return new Response(ResponseType.ERROR, e.getMessage());
            }
        }

        if (request.getType() == RequestType.BUY_TICKETS)
        {
            Object[] data = (Object[]) request.getData();
            Match match = (Match) data[0];
            String clientName = (String) data[1];
            String clientAddress = (String) data[2];
            int quantity = (int) data[3];
            try
            {
                server.buyTickets(match, clientName, clientAddress, quantity);
                return new Response(ResponseType.OK, null);
            }
            catch (Exception e)
            {
                return new Response(ResponseType.ERROR, e.getMessage());
            }
        }

        if (request.getType() == RequestType.FILTER_TICKETS)
        {
            Object[] data = (Object[]) request.getData();
            String name = (String) data[0];
            String address = (String) data[1];

            try
            {
                List<ClientTicketDTO> filtered = server.filterTickets(name, address);
                return new Response(ResponseType.FILTER_TICKETS, filtered);
            }
            catch (Exception e)
            {
                return new Response(ResponseType.ERROR, e.getMessage());
            }
        }

        if (request.getType() == RequestType.UPDATE_TICKETS)
        {
            Object[] data = (Object[]) request.getData();
            Match match = (Match) data[0];
            Client client = (Client) data[1];
            int oldQuantity = (int) data[2];
            int newQuantity = (int) data[3];

            try
            {
                server.updateTickets(match, client, oldQuantity, newQuantity);
                return new Response(ResponseType.OK, null);
            }
            catch (Exception e)
            {
                return new Response(ResponseType.ERROR, e.getMessage());
            }
        }

        return null;
    }


}