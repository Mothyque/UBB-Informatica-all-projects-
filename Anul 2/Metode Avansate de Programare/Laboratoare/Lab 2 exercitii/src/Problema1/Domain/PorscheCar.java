package Problema1.Domain;

public class PorscheCar extends Car
{
    private String model;

    public PorscheCar()
    {
        super();model = "";
    }

    public PorscheCar(int year, double price, String model)
    {
        super(year, price);this.model=model;
    }

    @Override
    public String toString()
    {
        return "Year: " + getYear() + "\n" + "Price: " + getPrice() + "\n" + "Model: " + model + '\n';
    }
}
