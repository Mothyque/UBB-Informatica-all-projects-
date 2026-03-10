package com.ubb.domain.duck;

public class FlyingAndSwimmingDuck extends Duck implements Flyer, Swimmer
{
    public FlyingAndSwimmingDuck(int id, String username, String email, String password, double viteza, double rezistenta)
    {
        super(id, username, email, password, viteza, rezistenta);
    }

    @Override
    public DuckType getType()
    {
        return DuckType.FLYING_AND_SWIMMING;
    }

    @Override
    public void fly()
    {
        System.out.println(getUsername() + " is flying at a speed of " + speed + " with endurance " + endurance + "but could also swim!");
    }

    @Override
    public void swim()
    {
        System.out.println(getUsername() + " is swimming at a speed of " + speed + " with endurance " + endurance + "but could also fly!");
    }

    @Override
    public String toString()
    {
        return "FlyingAndSwimmingDuck{" +
                "id=" + getId() +
                ", username='" + getUsername() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", speed=" + speed +
                ", endurance=" + endurance +
                '}';
    }
}
