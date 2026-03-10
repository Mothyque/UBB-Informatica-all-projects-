package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.controller.LoginAdminController;
import org.example.controller.LoginEmployeeController;
import org.example.repository.CarRepository;
import org.example.service.CarService;

public class Main extends Application
{
    @Override
    public void start(Stage primaryStage)
    {
        String url = "jdbc:postgresql://localhost:5432/dacia";
        String username = "postgres";
        String password = "mathy";

        CarRepository carRepository = new CarRepository(url, username, password);
        CarService carService = new CarService(carRepository);
        for(int i = 1; i <= 3; i++)
        {
            initLoginWindow(carService, "/login-employee-view.fxml");
            if(i <= 2)
            {
                initLoginWindow(carService, "/login-admin-view.fxml");
            }
        }

    }

    private void initLoginWindow(CarService carService, String file)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(file));
            Parent root = loader.load();
            if(file.contains("admin"))
            {
                LoginAdminController adminController = loader.getController();
                adminController.setService(carService);
                setStage(root);
            }
            else
            {
                LoginEmployeeController employeeController = loader.getController();
                employeeController.setService(carService);
                setStage(root);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private void setStage(Parent root)
    {
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}