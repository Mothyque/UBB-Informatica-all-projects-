package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.example.domain.Car;
import org.example.domain.Status;
import org.example.service.CarService;


public class EmployeeController
{
    @FXML
    private ListView<Car> listCars;
    @FXML
    private Button btnInfo;

    private CarService carService;
    private final ObservableList<Car> carsModel = FXCollections.observableArrayList();

    public void setService(CarService carService)
    {
        this.carService = carService;
        loadCars();
    }

    private void loadCars()
    {
        listCars.getItems().clear();
        Iterable<Car> cars = carService.findAll();
        for(Car car : cars)
        {
            carsModel.add(car);
        }
        listCars.setItems(carsModel);
    }

    @FXML
    private void onPushInfo()
    {
        Car selectedCar = listCars.getSelectionModel().getSelectedItem();
        if(selectedCar == null)
        {
            showErrorMessage("No car selected.");
            return;
        }
        else if(selectedCar.getStatus() != Status.NEW && selectedCar.getStatus() != Status.REJECTED)
        {
            showErrorMessage("You can not select this car.");
            return;
        }
        else
        {
            try
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/employee-car-view.fxml"));
                Parent root = loader.load();
                EmployeeCarController employeeCarController = loader.getController();
                employeeCarController.setCarService(carService,selectedCar);
                Stage currentStage = (Stage) btnInfo.getScene().getWindow();
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
