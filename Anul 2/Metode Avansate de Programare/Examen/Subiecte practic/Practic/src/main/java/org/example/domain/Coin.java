package org.example.domain;

public class Coin extends Entity<Integer>
{
    private String abbreviation;
    private String name;
    private Double price;

    public Coin(Integer id, String abbreviation, String name, Double price)
    {
        setId(id);
        this.abbreviation = abbreviation;
        this.name = name;
        this.price = price;
    }


    public String getAbbreviation()
    {
        return abbreviation;
    }

    public String getName()
    {
        return name;
    }

    public Double getPrice()
    {
        return price;
    }

    public void setAbbreviation(String abbreviation)
    {
        this.abbreviation = abbreviation;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setPrice(Double price)
    {
        this.price = price;
    }

    @Override
    public String toString()
    {
        return this.abbreviation + " "  + this.name + " " + this.price;
    }
}
