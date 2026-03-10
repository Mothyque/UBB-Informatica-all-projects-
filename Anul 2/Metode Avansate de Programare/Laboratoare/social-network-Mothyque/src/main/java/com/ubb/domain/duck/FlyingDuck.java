package com.ubb.domain.duck;

public class FlyingDuck extends Duck implements Flyer
{
    public FlyingDuck(int id, String username, String email, String password, double viteza, double rezistenta)
    {
        super(id, username, email, password, viteza, rezistenta);
    }

    @Override
    public DuckType getType()
    {
        return DuckType.FLYING;
    }

    @Override
    public void fly()
    {
        System.out.println(getUsername() + " is flying at a speed of " + speed + " with endurance " + endurance);
    }

    @Override
    public String toString()
    {
        return "FlyingDuck{" +
                "id=" + getId() +
                ", username='" + getUsername() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", speed=" + speed +
                ", endurance=" + endurance +
                '}';
    }
}
