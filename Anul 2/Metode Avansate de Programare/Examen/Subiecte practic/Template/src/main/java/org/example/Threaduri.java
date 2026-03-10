package org.example;

import javafx.application.Platform;
import javafx.scene.control.Alert;

public class Threaduri
{
    // IN SERVICE
    public void updateCar(Car car)
    {
        Thread thread = new Thread(() ->
        {
            try
            {
                Thread.sleep(5000);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
            carRepository.update(car);
            notifyObservers();
        });
        thread.start();
    }

    // IN CONTROLLER
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
