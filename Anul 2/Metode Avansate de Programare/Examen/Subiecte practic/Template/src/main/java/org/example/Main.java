package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.controller.AbstractController;
import org.example.domain.Entity;
import org.example.service.Service;

import java.io.IOException;

public class Main extends Application
{
    @Override
    public void start(Stage primaryStage)
    {
        String url = "jdbc:postgresql://localhost:5432/db_name";
        String username = "postgres";
        String password = "mathy";
    }

    private <ID, E extends Entity<ID>> void initWindow(Service<ID, E> service, String fxmlFile, String title) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();
        AbstractController<ID, E> controller = loader.getController();
        controller.setService(service);
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}