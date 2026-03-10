package Problema1;

import Problema1.Domain.Car;

import java.util.Vector;

public class CarRepository
{
    private Vector<Car> cars;

    private CarRepository()
    {
        this.cars = new Vector();
    }
}
