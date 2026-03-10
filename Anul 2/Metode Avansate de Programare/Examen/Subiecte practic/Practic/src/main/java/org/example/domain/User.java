package org.example.domain;

public class User extends Entity<Integer>
{
    private String name;
    private Double budget;
    private String password;

    public User(Integer id, String name, Double budget, String password)
    {
        setId(id);
        this.name = name;
        this.budget = budget;
        this.password = password;
    }

    public String getName()
    {
        return name;
    }

    public Double getBudget()
    {
        return budget;
    }

    public String getPassword()
    {
        return password;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setBudget(Double budget)
    {
        this.budget = budget;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    @Override
    public String toString()
    {
        return "Nume: " + this.name + " Buget: " + this.budget;
    }
}
