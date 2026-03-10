package org.example.service;

import org.example.domain.Car;
import org.example.domain.Status;
import org.example.repository.CarRepository;
import org.example.utils.Observable;
import org.example.utils.Observer;

import java.util.ArrayList;
import java.util.List;

public class CarService extends Observable
{
    private final CarRepository carRepository;

    private List<Observer> observers = new ArrayList<>();

    public CarService(CarRepository carRepository)
    {
        this.carRepository = carRepository;
    }

    public List<Car> findAll()
    {
        List<Car> cars = new ArrayList<>();
        for(Car car : carRepository.findAll())
        {
            cars.add(car);
        }
        return cars;
    }

    public List<Car> findAllNeedsApproval()
    {
        List<Car> cars = new ArrayList<>();
        for(Car car : carRepository.findAll())
        {
            if(car.getStatus().equals(Status.NEEDS_APPROVAL))
            {
                cars.add(car);
            }
        }
        return cars;
    }

    public void updateCar(Car car)
    {
        Thread thread = new Thread(() ->
        {
            try
            {
                Thread.sleep(5000);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
            carRepository.update(car);
            notifyObservers();
        });
        thread.start();
    }
}
