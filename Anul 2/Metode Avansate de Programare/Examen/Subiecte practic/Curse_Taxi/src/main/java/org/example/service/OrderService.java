package org.example.service;

import org.example.domain.Driver;
import org.example.domain.Order;
import org.example.domain.Status;
import org.example.repository.DriverRepository;
import org.example.repository.OrderRepository;
import org.example.utils.Observer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OrderService
{
    OrderRepository orderRepository;
    private List<Observer> observers = new ArrayList<>();

    public OrderService(OrderRepository orderRepository)
    {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAll()
    {
        return orderRepository.findAll();
    }

    public List<Order> getAllUnfinished(int driverId)
    {
        return orderRepository.findAllUnfinished(driverId);
    }

    public void addObserver(Observer observer)
    {
        observers.add(observer);
    }

    public void notifyObservers(int targetDriverId, String plecare, String destinatie, String nume)
    {
        for(Observer observer : observers)
        {
            observer.update(targetDriverId, plecare, destinatie, nume);
        }
    }

    public void requestOrder (int targetDriverId, String plecare, String destinatie, String nume)
    {
        notifyObservers(targetDriverId, plecare, destinatie, nume);
    }

    public void finishOrder(int id)
    {
        Order order = orderRepository.findOrder(id);
        order.setEndDate(LocalDateTime.now());
        order.setStatus(Status.FINISHED);
        orderRepository.update(order);
    }

    public void addOrder(String adresa_plecare, String adresa_destinatie, String nume_client)
    {
        orderRepository.add(new Order(adresa_plecare, adresa_destinatie, nume_client));
    }

    public void addOrder(Order order)
    {
        orderRepository.add(order);
    }
}
