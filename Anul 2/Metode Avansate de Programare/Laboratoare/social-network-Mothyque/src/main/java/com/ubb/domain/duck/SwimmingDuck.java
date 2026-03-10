package com.ubb.domain.duck;

public class SwimmingDuck extends Duck implements Swimmer
{
    public SwimmingDuck(int id, String username, String email, String password, double viteza, double rezistenta)
    {
        super(id, username, email, password, viteza, rezistenta);
    }

    @Override
    public DuckType getType()
    {
        return DuckType.SWIMMING;
    }

    @Override
    public void swim()
    {
        System.out.println(getUsername() + " is swimming at a speed of " + speed + " with endurance " + endurance);
    }

    @Override
    public String toString()
    {
        return "SwimmingDuck{" +
                "id=" + getId() +
                ", username='" + getUsername() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", speed=" + speed +
                ", endurance=" + endurance +
                '}';
    }
}
