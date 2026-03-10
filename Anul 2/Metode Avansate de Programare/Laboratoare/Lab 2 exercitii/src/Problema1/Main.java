package Problema1;

import Problema1.Domain.AudiCar;
import Problema1.Domain.Car;
import Problema1.Domain.PorscheCar;

public class Main
{
    public static void main(String[] args)
    {
        Car car  = new Car(2020, 30000);
        AudiCar audiCar = new AudiCar(2021, 40000, "Germany");
        PorscheCar porscheCar = new PorscheCar(2022, 80000, "911");
        Car anotherPorscheCar = new PorscheCar(2023, 90000, "Cayenne");

        System.out.println(car.toString());
        System.out.println(audiCar.toString());
        System.out.println(porscheCar.toString());
        System.out.println(anotherPorscheCar.toString());

        anotherPorscheCar = audiCar;
        System.out.println(anotherPorscheCar.toString());
    }
}
