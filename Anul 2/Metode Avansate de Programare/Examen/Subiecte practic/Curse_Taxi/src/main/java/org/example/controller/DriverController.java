package org.example.controller;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.util.Duration;
import org.example.domain.Order;
import org.example.domain.Status;
import org.example.service.OrderService;

import javafx.event.ActionEvent;
import org.example.utils.Observer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public class DriverController implements Observer
{
    @FXML
    private Label labelDriverName;

    @FXML
    private ListView<Order> listOrders;

    private OrderService orderService;
    private int driverId;
    private ObservableList<Order> model = FXCollections.observableArrayList();

    public void setService(OrderService orderService, int driverId, String driverName)
    {
        this.orderService = orderService;
        this.driverId = driverId;
        this.labelDriverName.setText("Driver: " + driverName);
        loadOrders();
    }

    private void loadOrders()
    {
        List<Order> orders = orderService.getAllUnfinished(driverId);
        model.setAll(orders);
        listOrders.setItems(model);
    }

    @FXML
    public void handleFinished(ActionEvent actionEvent)
    {
        Order selectedOrder = listOrders.getSelectionModel().getSelectedItem();

        if(selectedOrder != null)
        {
            orderService.finishOrder(selectedOrder.getId());
            loadOrders();
        }
        else
        {
            showError("Selectati o comanda pentru a finaliza");
        }
    }

    private void showError(String msg)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(msg);
        alert.show();
    }

    @Override
    public void update(int targetDriverId, String plecare, String destinatie, String nume)
    {
        if(this.driverId == targetDriverId)
        {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Oferta Cursa");
                alert.setHeaderText("Dispecerul ti-a propus o cursa!");
                alert.setContentText("De la: " + plecare + "\nPan la: " + destinatie);

                PauseTransition delay = new PauseTransition(Duration.seconds(5));
                delay.setOnFinished(event -> alert.close());
                delay.play();

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK)
                {
                    Order order = new Order(0, driverId, Status.IN_PROGRESS, LocalDateTime.now(), null, plecare, destinatie, nume);
                    orderService.addOrder(order);
                    loadOrders();
                }
            });
        }
    }
}
