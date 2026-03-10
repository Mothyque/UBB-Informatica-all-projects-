package org.example.domain;

import java.util.Optional;

public class Car extends Entity<Integer>
{
    private String name;
    private String description;
    private Double price;
    private Status status;
    private String comments;
    private String options;
    private String rejectionReason;


    public Car(int id, String name, String description, Double price, Status status, String comments, String options, String rejectionReason)
    {
        setId(id);
        this.name = name;
        this.description = description;
        this.price = price;
        this.status = status;
        this.comments = comments;
        this.options = options;
        this.rejectionReason = rejectionReason;
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public Double getPrice()
    {
        return price;
    }

    public Status getStatus()
    {
        return status;
    }

    public String getComments()
    {
        return comments;
    }

    public String getOptions()
    {
        return options;
    }

    public String getRejectionReason()
    {
        return rejectionReason;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setPrice(Double price)
    {
        this.price = price;
    }

    public void setStatus(Status status)
    {
        this.status = status;
    }

    public void setComments(String comments)
    {
        this.comments = comments;
    }

    public void setOptions(String options)
    {
        this.options = options;
    }

    public void setRejectionReason(String rejectionReason)
    {
        this.rejectionReason = rejectionReason;
    }

    @Override
    public String toString()
    {
        return "Car{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", status=" + status +
                '}';
    }
}
