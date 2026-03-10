package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.domain.Car;
import org.example.domain.Status;
import org.example.service.CarService;

public class AdminCarController
{
    @FXML
    private Label lblDetails;

    @FXML
    private TextField txtReason;

    @FXML
    private Button btnAccept;

    @FXML
    private Button btnDeny;

    private CarService carService;
    private Car car;

    public void setCarService(CarService carService, Car car)
    {
        this.carService = carService;
        this.car = car;
        this.lblDetails.setText("Description: " + car.getDescription() + " Comments: " + car.getComments() + " Options: " + car.getOptions());
    }

    @FXML
    private void onAccept()
    {
        car.setStatus(Status.APPROVED);
        car.setRejectionReason("");
        carService.updateCar(car);
    }

    @FXML
    private void onDeny()
    {
        String reason = txtReason.getText().trim();
        car.setStatus(Status.REJECTED);
        car.setRejectionReason(reason);
        carService.updateCar(car);
        txtReason.clear();
    }
}
