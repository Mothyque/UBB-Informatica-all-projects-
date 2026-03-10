package org.example.controller;

import javafx.scene.control.Alert;

public class AbstractController
{
    protected void showErrorMessage(String text)
    {
        Alert message = new Alert(Alert.AlertType.ERROR);
        message.setTitle("Eroare");
        message.setContentText(text);
        message.showAndWait();
    }

    protected void showInfoMessage(String text)
    {
        Alert message = new Alert(Alert.AlertType.INFORMATION);
        message.setTitle("Info");
        message.setContentText(text);
        message.showAndWait();
    }
}
