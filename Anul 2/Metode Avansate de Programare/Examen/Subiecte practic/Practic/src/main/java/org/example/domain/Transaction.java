package org.example.domain;

import java.time.LocalDateTime;

public class Transaction extends Entity<Integer>
{
    private int userId;
    private String abbreviate;
    private String type;
    private Double price;
    private LocalDateTime timestamp;

    public Transaction(int id, int userId, String abbreviate, String type, Double price, LocalDateTime timestamp)
    {
        setId(id);
        this.userId = userId;
        this.abbreviate = abbreviate;
        this.type = type;
        this.price = price;
        this.timestamp = timestamp;
    }

    public int getUserId() {
        return userId;
    }

    public String getAbbreviate() {
        return abbreviate;
    }

    public String getType() {
        return type;
    }

    public Double getPrice() {
        return price;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setAbbreviate(String abbreviate) {
        this.abbreviate = abbreviate;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString()
    {
        return "User ID: " + userId + "Coin Symbol: " + abbreviate + "Type: " + type + "Price: " + price + "Time: " + timestamp;
    }
}

