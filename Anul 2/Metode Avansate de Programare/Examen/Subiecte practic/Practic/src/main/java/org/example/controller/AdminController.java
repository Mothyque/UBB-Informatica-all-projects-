package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import org.example.domain.Transaction;
import org.example.domain.User;
import org.example.service.TransactionService;
import org.example.service.UserService;
import org.example.utils.Observer;


public class AdminController extends AbstractController implements Observer
{
    private UserService userService;
    private TransactionService transactionService;
    @FXML
    private ListView<User> listTrades;

    @FXML
    private ListView<Transaction> listTransactions;

    private final ObservableList<User> userModel = FXCollections.observableArrayList();
    private final ObservableList<Transaction> transactionModel = FXCollections.observableArrayList();


    public void setService(UserService userService, TransactionService transactionService)
    {
        this.userService = userService;
        this.transactionService = transactionService;
        loadUsers();
        loadTransactions();
    }

    private void loadUsers()
    {
        listTrades.getItems().clear();
        Iterable<User> users = userService.findAll();
        for(User user : users)
        {
            userModel.add(user);
        }
        listTrades.setItems(userModel);
    }

    private void loadTransactions()
    {
        listTransactions.getItems().clear();
        Iterable<Transaction> transactions = transactionService.findAll();
        for(Transaction trans : transactions)
        {
            transactionModel.add(trans);
        }
        listTransactions.setItems(transactionModel);
    }

    @Override
    public void update()
    {
        loadTransactions();
    }
}

