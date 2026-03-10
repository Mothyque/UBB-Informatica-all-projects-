package org.example.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Order
{
    private int id;
    private int driverId;
    private Status status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String pickupAddress;
    private String destinationAddress;
    private String clientName;

    public Order(int id, int driverId, Status status, LocalDateTime startDate, LocalDateTime endDate, String pickupAddress, String destinationAddress, String clientName)
    {
        this.id = id;
        this.driverId = driverId;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.pickupAddress = pickupAddress;
        this.destinationAddress = destinationAddress;
        this.clientName = clientName;
    }

    public Order(String pickupAddress, String destinationAddress, String clientName)
    {
        this.id = 0;
        this.driverId = 0;
        this.status = Status.PENDING;
        this.startDate = LocalDateTime.now();
        this.endDate = null;
        this.pickupAddress = pickupAddress;
        this.destinationAddress = destinationAddress;
        this.clientName = clientName;
    }

    public int getId()
    {
        return id;
    }

    public int getDriverId()
    {
        return driverId;
    }

    public Status getStatus()
    {
        return status;
    }

    public LocalDateTime getStartDate()
    {
        return startDate;
    }

    public LocalDateTime getEndDate()
    {
        return endDate;
    }

    public String getDestinationAddress()
    {
        return destinationAddress;
    }

    public String getPickupAddress()
    {
        return pickupAddress;
    }

    public String getClientName()
    {
        return clientName;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setDriverId(int driverId)
    {
        this.driverId = driverId;
    }

    public void setStatus(Status status)
    {
        this.status = status;
    }

    public void setStartDate(LocalDateTime startDate)
    {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate)
    {
        this.endDate = endDate;
    }

    public void setPickupAddress(String pickupAddress)
    {
        this.pickupAddress = pickupAddress;
    }

    public void setDestinationAddress(String destinationAddress)
    {
        this.destinationAddress = destinationAddress;
    }

    public void setClientName(String clientName)
    {
        this.clientName = clientName;
    }

    @Override
    public String toString()
    {
        return "Client: " + clientName + "\n" + "Destinatie: " + destinationAddress;
    }
}
