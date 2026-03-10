package org.example.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.example.domain.MenuItem;
import org.example.service.MenuItemService;
import org.example.service.OrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TableController
{
    @FXML
    private Label lblTable;

    @FXML
    private VBox vboxMenu;

    @FXML
    private Button btnOrder;

    private int tableId;

    private MenuItemService menuItemService;
    private OrderService orderService;

    private final List<TableView<MenuItem>> generatedTables = new ArrayList<>();

    public void setServices(MenuItemService menuItemService, OrderService orderService, Integer tableId)
    {
        this.menuItemService = menuItemService;
        this.orderService = orderService;
        this.tableId = tableId;
        this.lblTable.setText("Table: " + this.tableId);
        loadMenu();
    }

    private void loadMenu()
    {
        Map<String, List<MenuItem>> menu = menuItemService.getMenu();
        vboxMenu.getChildren().clear();
        generatedTables.clear();
        vboxMenu.setSpacing(30);
        vboxMenu.setStyle("-fx-padding: 15;");
        for(String key : menu.keySet())
        {
            Label lblCategory = new Label(key);
            lblCategory.setStyle("-fx-font-weight: bold");

            TableView<MenuItem> table = new TableView<>();
            table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

            TableColumn<MenuItem, String> colName = new TableColumn<>("Produs");
            colName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getItem()));

            TableColumn<MenuItem, String> colPrice = new TableColumn<>("Pret");
            colPrice.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPrice() + " " + data.getValue().getCurrency()));

            table.getColumns().add(colName);
            table.getColumns().add(colPrice);

            List<MenuItem> itemsInCategory = menu.get(key);
            table.setItems(FXCollections.observableArrayList(itemsInCategory));

            table.setFixedCellSize(30);
            int tableHeight = (itemsInCategory.size() * 30) + 28 + 2;

            table.setPrefHeight(tableHeight);
            table.setMinHeight(tableHeight);
            table.setMaxHeight(tableHeight);

            vboxMenu.getChildren().addAll(lblCategory, table);
            generatedTables.add(table);
        }
    }

    @FXML
    public void onPlaceOrder()
    {
        List<MenuItem> selectedItems = new ArrayList<>();
        for(TableView<MenuItem> table : generatedTables)
        {
            selectedItems.addAll(table.getSelectionModel().getSelectedItems());
        }

        if(selectedItems.isEmpty())
        {
            return;
        }

        orderService.add(tableId, selectedItems);

        for(TableView<MenuItem> table : generatedTables) table.getSelectionModel().clearSelection();
    }
}
