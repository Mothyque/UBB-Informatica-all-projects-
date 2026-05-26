package org.example.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import org.example.domain.User;

public class LoginController extends MessageController
{
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnLogin;

    private org.example.networking.Services server;
    private Main mainApp;

    public void setServer(org.example.networking.Services server)
    {
        this.server = server;
    }

    public void setMainApp(Main mainApp)
    {
        this.mainApp = mainApp;
    }

    @FXML
    public void onLogin()
    {
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        User user = new User(username, password);
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main-view.fxml"));
            Parent root  = loader.load();
            MainController mainCtrl = loader.getController();

            server.login(user, mainCtrl);
            mainCtrl.setServices(server, user, mainApp);
            mainApp.setCurrentUser(user);

            Stage stage = (Stage) btnLogin.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Basketball Tickets - Main");
            stage.show();
        }
        catch (Exception e)
        {
            showErrorMessage("Login failed: " + e.getMessage());
        }
    }

}
