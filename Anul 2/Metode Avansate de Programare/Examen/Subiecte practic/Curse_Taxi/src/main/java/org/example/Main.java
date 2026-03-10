package org.example;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.controller.DispController;
import org.example.controller.DriverController;
import org.example.domain.Driver;
import org.example.repository.DriverRepository;
import org.example.repository.OrderRepository;
import org.example.service.DriverService;
import org.example.service.OrderService;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application
{
    @Override
    public void start(Stage primaryStage)
    {
        String url = "jdbc:postgresql://localhost:5432/curse_taxi";
        String username = "postgres";
        String password = "mathy";
        OrderRepository orderRepository = new OrderRepository(new ArrayList<>(), url, username, password);
        DriverRepository driverRepository = new DriverRepository(new ArrayList<>(), url, username, password);

        OrderService orderService = new OrderService(orderRepository);
        DriverService driverService = new DriverService(driverRepository);

        List<Driver> drivers = driverService.getAll();
        for(Driver driver: drivers)
        {
            initDriverWindow(driver, orderService);
        }
        initDispWindow(orderService, driverService);
    }

    private void initDriverWindow(Driver driver, OrderService orderService)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/driver-view.fxml"));
            Parent root = loader.load();
            DriverController driverController = loader.getController();
            driverController.setService(orderService, driver.getId(), driver.getName());
            orderService.addObserver(driverController);
            Stage stage = new Stage();
            stage.setTitle("Driver: " + driver.getName());
            stage.setScene(new Scene(root, 800, 600));
            stage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void initDispWindow(OrderService orderService, DriverService driverService)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/disp-view.fxml"));
            Parent root = loader.load();
            DispController dispController = loader.getController();
            dispController.setServices(orderService, driverService);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void main(String[] args)
    {
        launch(args);
    }
}