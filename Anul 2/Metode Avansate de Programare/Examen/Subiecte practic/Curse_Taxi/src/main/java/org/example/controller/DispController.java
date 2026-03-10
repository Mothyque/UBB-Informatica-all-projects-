package org.example.controller;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import org.example.domain.Driver;
import org.example.domain.Order;
import org.example.service.DriverService;
import org.example.service.OrderService;

import java.util.ArrayList;
import java.util.List;


public class DispController
{
    @FXML
    private TextField lblPlecare;
    @FXML
    private TextField lblDestinatie;
    @FXML
    private TextField lblNume;
    @FXML
    private Button btnAdd;

    private List<Driver> currentDriverList;
    private int currentIndex;
    private boolean orderAccepted = false;
    private String currentPlecare;
    private String currentDestinatie;
    private String currentNume;

    private OrderService orderService;
    private DriverService driverService;

    public void setServices(OrderService orderService, DriverService driverService)
    {
        this.orderService = orderService;
        this.driverService = driverService;
    }

    @FXML
    private void handleAdd(ActionEvent actionEvent)
    {
        currentPlecare = lblPlecare.getText();
        currentDestinatie = lblDestinatie.getText();
        currentNume = lblNume.getText();
        if(currentPlecare.isEmpty() || currentDestinatie.isEmpty() || currentNume.isEmpty())
        {
            showError("Completeaza toate campurile");
            return;
        }
        currentDriverList = findBestDriver();
        currentIndex = 0;
        orderAccepted = false;
        if(currentDriverList.isEmpty())
        {
            showError("Eroare! Nu sunt soferi disponibili");
        }
        askNextDriver();
        lblNume.clear();
        lblPlecare.clear();
        lblDestinatie.clear();
    }

    private void askNextDriver()
    {
        if(orderAccepted)
        {
            return;
        }
        if(currentIndex >= currentDriverList.size())
        {
            showError("Toti soferii au refuzat!");
            return;
        }
        Driver currentDriver = currentDriverList.get(currentIndex);
        orderService.requestOrder(currentDriver.getId(), currentPlecare, currentDestinatie, currentNume);
        PauseTransition delay = new PauseTransition(Duration.seconds(5));
        delay.setOnFinished(event -> {
            if (!orderAccepted) {
                currentIndex++;
                askNextDriver();
            }
        });
        delay.play();
    }

    private List<Driver> findBestDriver()
    {
        List<Driver> allDrivers = driverService.getAll();
        List<Driver> available = new ArrayList<>();
        for(Driver d : allDrivers)
        {
            List<Order> activeOrders = orderService.getAllUnfinished(d.getId());
            if(activeOrders.isEmpty())
            {
                available.add(d);
            }
        }
        return available;
    }

    private void showError(String msg)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(msg);
        alert.show();
    }
}
