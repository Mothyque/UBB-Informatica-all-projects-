package org.example.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.service.CarService;


public class LoginEmployeeController
{
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;

    @FXML
    private Button btnLogin;

    private CarService carService;

    public void setService(CarService carService)
    {
        this.carService = carService;
    }

    @FXML
    public void onLogin()
    {
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        if(username.isEmpty() || password.isEmpty())
        {
            showErrorMessage("Completeaza toate campurile");
        }
        else if(!username.equalsIgnoreCase("employee") || !password.equalsIgnoreCase("pass"))
        {
            showErrorMessage("Parola sau username incorect");
        }
        else
        {
            try
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/employee-view.fxml"));
                Parent root = loader.load();
                EmployeeController controller = loader.getController();
                controller.setService(carService);
                Stage currentStage = (Stage) btnLogin.getScene().getWindow();
                currentStage.setScene(new Scene(root));
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private void showErrorMessage(String text)
    {
        Alert message = new Alert(Alert.AlertType.ERROR);
        message.setTitle("Error");
        message.setContentText(text);
        message.showAndWait();
    }

}
