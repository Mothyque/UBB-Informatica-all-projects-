package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.domain.Car;
import org.example.domain.Status;
import org.example.service.CarService;

public class EmployeeCarController
{
    @FXML
    private Label lblDetails;

    @FXML
    private TextField txtComments;

    @FXML
    private TextField txtOptions;

    @FXML
    private Button btnSend;

    private CarService carService;
    private Car car;


    public void setCarService(CarService carService, Car car)
    {
        this.carService = carService;
        this.car = car;
        lblDetails.setText(car.getDescription());
    }

    @FXML
    public void onSend()
    {
        String comments = txtComments.getText();
        String options = txtOptions.getText();

        car.setComments(comments);
        car.setOptions(options);
        car.setStatus(Status.NEEDS_APPROVAL);

        carService.updateCar(car);

        txtComments.clear();
        txtOptions.clear();
    }
}
