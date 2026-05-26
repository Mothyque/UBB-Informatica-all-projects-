package org.example.controller;

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
import java.net.ConnectException;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class ServicesRpcProxy implements org.example.networking.Services
{
    private String host;
    private int port;
    private Observer client;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;
    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;

    public ServicesRpcProxy(String host, int port)
    {
        this.host = host;
        this.port = port;
        qresponses = new LinkedBlockingDeque<>();
    }

    @Override
    public void login(User user, Observer client) throws Exception
    {
        initializeConnection();
        sendRequest(new Request(RequestType.LOGIN, user));
        Response response = readResponse();
        if (response.getType() == ResponseType.OK)
        {
            this.client = client;
        }
        else
        {
            throw new Exception((response.getData().toString()));
        }
    }

    @Override
    public void logout(User user, Observer client) throws Exception
    {
        sendRequest(new Request(RequestType.LOGOUT, user));
        Response response = readResponse();
        closeConnection();
        if (response.getType() == ResponseType.ERROR)
        {
            throw new Exception(response.getData().toString());
        }
    }

    @Override
    public Match[] getAllMatches() throws Exception
    {
        sendRequest(new Request(RequestType.GET_ALL_MATCHES, null));
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR)
        {
            throw new Exception(response.getData().toString());
        }
        return (Match[]) response.getData();
    }

    @Override
    public void buyTickets(Match match, String clientName, String clientAddress, int numberOfTickets) throws Exception
    {
        Object[] data = new Object[]{match, clientName, clientAddress, numberOfTickets};
        sendRequest(new Request(RequestType.BUY_TICKETS, data));
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR)
        {
            throw new Exception(response.getData().toString());
        }

    }

    @Override
    public List<ClientTicketDTO> filterTickets(String name, String address) throws Exception
    {
        Object[] data = new Object[]{name, address};
        sendRequest(new Request(RequestType.FILTER_TICKETS, data));
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR)
        {
            throw new Exception(response.getData().toString());
        }
        return (List<ClientTicketDTO>) response.getData();
    }

    @Override
    public void updateTickets(Match match, Client client, int oldQuantity, int newQuantity) throws Exception {
        Object[] data = new Object[]{match, client, oldQuantity, newQuantity};
        sendRequest(new Request(RequestType.UPDATE_TICKETS, data));
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR)
        {
            throw new Exception(response.getData().toString());
        }
    }

    @Override
    public void addObserver(Integer userId, Observer client) {}

    void closeConnection()
    {
        try
        {
            if (input != null) input.close();
            if (output != null) output.close();
            if (connection != null) connection.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void handleUpdate(Response response)
    {
        if (response.getType() == ResponseType.UPDATE)
        {
            client.update();
        }
    }

    private void sendRequest(Request request) throws Exception
    {
        try
        {
            output.writeObject(request);
            output.flush();
        }
        catch (Exception e)
        {
            throw new Exception("Error sending object " + e);
        }
    }

    private Response readResponse() throws Exception
    {
        Response response = null;
        try
        {
            response = qresponses.take();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        return response;
    }

    private void initializeConnection() throws Exception
    {
        try
        {
            connection = new Socket(host, port);
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            finished = false;
            startReader();
        }
        catch (ConnectException e)
        {
            throw new Exception("Cannot connect to server: " + e.getMessage());
        }
        catch (IOException e)
        {
            throw new Exception("Error initializing connection: " + e.getMessage());
        }
    }

    private void startReader()
    {
        Thread tw = new Thread(new Reader());
        tw.start();
    }

    private class Reader implements Runnable
    {
        public void run()
        {
            while (!finished)
            {
                try
                {
                    Object response = input.readObject();
                    if (isUpdate((Response) response))
                    {
                        handleUpdate((Response) response);
                    }
                    else
                    {
                        try
                        {
                            qresponses.put((Response) response);
                        }
                        catch (InterruptedException e)
                        {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                            finished = true;
                        }
                    }
                }
                catch (Exception e)
                {
                    System.out.println("Reading error " + e);
                    finished = true;
                }
            }
        }
    }

    private boolean isUpdate(Response response)
    {
        return response.getType() == ResponseType.UPDATE;
    }

}