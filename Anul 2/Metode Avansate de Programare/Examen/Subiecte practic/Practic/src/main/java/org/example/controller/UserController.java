package org.example.controller;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.domain.Coin;
import org.example.domain.User;
import org.example.service.CoinService;



public class UserController extends AbstractController
{
    private CoinService coinService;
    private User currentUser;

    private final ObservableList<Coin> coinModel = FXCollections.observableArrayList();

    @FXML
    private Label lblUser;

    @FXML
    private Label lblBalance;

    @FXML
    private TableView<Coin> tableCoins;

    @FXML
    private Button btnBuy;

    @FXML
    private Button btnSell;

    public void setService(CoinService coinService, User user)
    {
        this.coinService = coinService;
        this.currentUser = user;
        this.lblUser.setText("User: " + user.getName());
        this.lblBalance.setText("Balance: " + user.getBudget() + " EUR");
        loadCoins();
    }

    private void loadCoins()
    {
        tableCoins.getItems().clear();

        TableColumn<Coin, String> colName = new TableColumn<>("Nume");
        colName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));

        TableColumn<Coin, String> colPrice = new TableColumn<>("Pret");
        colPrice.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getPrice())));

        tableCoins.getColumns().add(colName);
        tableCoins.getColumns().add(colPrice);

        for(Coin coin : coinService.findAll())
        {
            coinModel.add(coin);
        }
        tableCoins.setItems(coinModel);
    }

    @FXML
    public void onBuy()
    {

    }

    @FXML
    public void onSell()
    {

    }
}
