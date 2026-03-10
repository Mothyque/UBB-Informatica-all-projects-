package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import org.example.domain.Order;
import org.example.domain.OrderStatus;
import org.example.service.OrderService;
import org.example.utils.Observer;

import java.util.ArrayList;
import java.util.List;

public class StaffController implements Observer
{
    @FXML
    private ListView<Order> listOrders;
    private final ObservableList<Order> model = FXCollections.observableArrayList();

    private OrderService orderService;

    public void setService(OrderService orderService)
    {
        this.orderService = orderService;
        orderService.addObserver(this);
        loadOrders();
    }

    private void loadOrders()
    {
        Iterable<Order> orders = orderService.getAll();
        List<Order> placedOrders = new ArrayList<>();
        for(Order order : orders)
        {
            if(order.getStatus() == OrderStatus.PLACED)
            {
                placedOrders.add(order);
            }
        }
        placedOrders.sort((o1, o2) -> o1.getDate().compareTo(o2.getDate()));
        model.setAll(placedOrders);
        listOrders.setItems(model);
    }

    @Override
    public void update()
    {
        loadOrders();
    }
}
