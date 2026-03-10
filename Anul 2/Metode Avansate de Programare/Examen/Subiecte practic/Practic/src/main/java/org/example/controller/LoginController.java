package org.example.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.domain.User;
import org.example.service.CoinService;
import org.example.service.TransactionService;
import org.example.service.UserService;

import java.util.Objects;


public class LoginController extends AbstractController
{
    private UserService userService;
    private CoinService coinService;
    private TransactionService transactionService;

    @FXML
    private TextField txtName;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnLogin;

    public void setServices(UserService userService, CoinService coinService, TransactionService transactionService)
    {
        this.userService = userService;
        this.coinService = coinService;
        this.transactionService = transactionService;
    }

    @FXML
    public void onLogin()
    {
        String username = txtName.getText();
        String password = txtPassword.getText();

        if(username.isEmpty() || password.isEmpty())
        {
            showErrorMessage("Completeaza toate campurile");
        }
        else if(Objects.equals(username.toLowerCase(), "admin") && Objects.equals(password.toLowerCase(), "pass"))
        {
            try
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin-view.fxml"));
                Parent root = loader.load();
                AdminController adminController = loader.getController();
                adminController.setService(userService, transactionService);
                Stage currentStage = (Stage) btnLogin.getScene().getWindow();
                currentStage.setScene(new Scene(root));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            User searchedUser = userService.getUserByName(username.toLowerCase());
            if(searchedUser != null)
            {
                if(Objects.equals(searchedUser.getPassword(), password))
                {
                    try
                    {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/user-view.fxml"));
                        Parent root = loader.load();
                        UserController userController = loader.getController();
                        userController.setService(coinService, searchedUser);
                        Stage currentStage = (Stage) btnLogin.getScene().getWindow();
                        currentStage.setScene(new Scene(root));
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    showErrorMessage("Incorrect password");
                }
            }
            else
            {
                showErrorMessage("User does not exist");
            }
        }
    }
}
