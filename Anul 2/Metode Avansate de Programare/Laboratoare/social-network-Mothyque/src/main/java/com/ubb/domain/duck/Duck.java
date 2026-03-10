package com.ubb.domain.duck;

import com.ubb.domain.User;

public abstract class Duck extends User
{
    public double speed;
    public double endurance;

    public Duck(int id, String username, String email, String password, double speed, double endurance)
    {
        super(id, username, email, password);
        this.speed = speed;
        this.endurance = endurance;
    }

    public abstract DuckType getType();

    public double getSpeed()
    {
        return speed;
    }

    public double getEndurance()
    {
        return endurance;
    }

    public void setSpeed(double viteza)
    {
        this.speed = viteza;
    }

    public void setEndurance(double rezistenta)
    {
        this.endurance = rezistenta;
    }

    @Override
    public String toString()
    {
        return "Duck{" +
                "id=" + getId() +
                ", username='" + getUsername() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", viteza=" + speed +
                ", rezistenta=" + endurance +
                '}';
    }
}
