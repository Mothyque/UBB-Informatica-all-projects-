package org.example.controller;

import javafx.application.Platform;
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
import org.example.service.CarService;
import org.example.utils.Observer;


public class AdminController implements Observer
{

    @FXML
    private ListView<Car> listCars;

    @FXML
    private Button btnInfo;

    private CarService carService;
    private final ObservableList<Car> carsModel = javafx.collections.FXCollections.observableArrayList();


    public void setService(CarService carService)
    {
        this.carService = carService;
        carService.addObserver(this);
        loadCars();
    }

    private void loadCars()
    {
        carsModel.setAll(carService.findAllNeedsApproval());
        listCars.setItems(carsModel);
    }

    @FXML
    private void onPressInfo()
    {
        Car selectedCar = listCars.getSelectionModel().getSelectedItem();
        if (selectedCar == null)
        {
            showErrorMessage("No car selected.");
            return;
        }
        else
        {
            try
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin-car-view.fxml"));
                Parent root = loader.load();
                AdminCarController adminCarController = loader.getController();
                adminCarController.setCarService(carService, selectedCar);
                Stage currentStage = (Stage) btnInfo.getScene().getWindow();
                currentStage.setScene(new Scene(root));
            }
            catch (Exception e)
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

    @Override
    public void update()
    {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Update");
            alert.setContentText("Car approval request.");
            alert.show();
            loadCars();
        });

    }
}
