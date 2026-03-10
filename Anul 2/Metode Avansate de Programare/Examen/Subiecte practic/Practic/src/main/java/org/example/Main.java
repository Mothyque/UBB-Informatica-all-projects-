package org.example;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.controller.LoginController;
import org.example.domain.Coin;
import org.example.repository.CoinRepository;
import org.example.repository.TransacitonRepository;
import org.example.repository.UserRepository;
import org.example.service.CoinService;
import org.example.service.TransactionService;
import org.example.service.UserService;
public class Main extends Application
{
    @Override
    public void start(Stage primaryStage)
    {
        String url = "jdbc:postgresql://localhost:5432/crypto";
        String username = "postgres";
        String password = "mathy";

        UserRepository userRepository = new UserRepository(url, username, password);
        UserService userService = new UserService(userRepository);

        CoinRepository coinRepository = new CoinRepository(url, username, password);
        CoinService coinService = new CoinService(coinRepository);

        TransacitonRepository transacitonRepository = new TransacitonRepository(url, username, password);
        TransactionService transactionService = new TransactionService(transacitonRepository);

        for(int i = 0; i <= userService.size(); i++)
        {
            initLogin(userService, coinService, transactionService);
        }
    }

    private void initLogin(UserService userService, CoinService coinService, TransactionService transactionService)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login-view.fxml"));
            Parent root = loader.load();
            LoginController loginController = loader.getController();
            loginController.setServices(userService, coinService, transactionService);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}