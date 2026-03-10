package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.controller.StaffController;
import org.example.controller.TableController;
import org.example.domain.MenuItem;
import org.example.domain.Order;
import org.example.repository.MenuItemRepo;
import org.example.repository.OrderRepository;
import org.example.repository.TableRepository;
import org.example.service.MenuItemService;
import org.example.service.OrderService;
import org.example.service.TableService;

import java.awt.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main extends Application
{
    @Override
    public void start(Stage primaryStage)
    {
        String url = "jdbc:postgresql://localhost:5432/restaurant";
        String username = "postgres";
        String password = "mathy";

        MenuItemRepo menuItemRepo = new MenuItemRepo(url, username, password);
        OrderRepository orderRepository = new OrderRepository(url, username, password);
        TableRepository tableRepository = new TableRepository(url, username, password);

        MenuItemService menuItemService = new MenuItemService(menuItemRepo);
        OrderService orderService = new OrderService(orderRepository);

        TableService tableService = new TableService(tableRepository);

        initStaffWindow(orderService);
        initTableWindows(menuItemService, tableService, orderService);
    }

    private void initStaffWindow(OrderService orderService)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/staff-view.fxml"));
            Parent root = loader.load();
            StaffController controller = loader.getController();
            controller.setService(orderService);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private void initTableWindows(MenuItemService menuItemService, TableService tableService, OrderService orderService)
    {
        int tableNumber = tableService.getTables();
        for(int i = 1; i <= tableNumber; i++)
        {
            try
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/table-view.fxml"));
                Parent root = loader.load();
                TableController tableController = loader.getController();
                tableController.setServices(menuItemService, orderService, i);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args)
    {
        launch(args);
    }


}