package org.example.service;

import org.example.domain.MenuItem;
import org.example.domain.Order;
import org.example.domain.OrderStatus;
import org.example.domain.Table;
import org.example.repository.OrderRepository;
import org.example.repository.Repository;
import org.example.utils.Observable;

import java.time.LocalDateTime;
import java.util.List;

public class OrderService extends Observable
{
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository)
    {
        this.orderRepository = orderRepository;
    }

    public void add(int tableId, List<MenuItem> menuItemList)
    {
        Table table = new Table(tableId);
        Order order = new Order(table, LocalDateTime.now(), OrderStatus.PLACED);
        order.setMenuItems(menuItemList);
        orderRepository.save(order);
        notifyObservers();
    }

    public void delete(int id)
    {
        orderRepository.delete(id);
    }

    public Iterable<Order> getAll()
    {
        return orderRepository.findAll();
    }


}
