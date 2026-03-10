package com.ubb.controller;

import com.ubb.domain.duck.Duck;
import com.ubb.domain.duck.DuckType;
import com.ubb.service.DuckService;
import com.ubb.utils.paging.Page;
import com.ubb.utils.paging.Pageable;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.event.ActionEvent;
import java.util.List;
import java.util.stream.StreamSupport;


public class DuckController extends PagingController<Duck>
{

    @FXML private TableColumn<Duck, Integer> idColumn;
    @FXML private TableColumn<Duck, String> nameColumn;
    @FXML private TableColumn<Duck, String> emailColumn;
    @FXML private TableColumn<Duck, String> typeColumn;
    @FXML private TableColumn<Duck, Integer> speedColumn;
    @FXML private TableColumn<Duck, Integer> enduranceColumn;

    @FXML private TextField txtId;
    @FXML private TextField txtUsername;
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;
    @FXML private TextField txtSpeed;
    @FXML private TextField txtEndurance;
    @FXML private ComboBox<DuckType> comboType;

    @FXML private ComboBox<DuckType> filterComboBox;

    @Override
    protected void handleAddAction() {
        if (service instanceof DuckService duckService)
        {
            try
            {
                int id = Integer.parseInt(txtId.getText());
                String username = txtUsername.getText();
                String email = txtEmail.getText();
                String password = txtPassword.getText();
                DuckType type = comboType.getValue();
                double speed = Double.parseDouble(txtSpeed.getText());
                double endurance = Double.parseDouble(txtEndurance.getText());
                String typeStr = switch (type)
                {
                    case FLYING -> "F";
                    case SWIMMING -> "S";
                    case FLYING_AND_SWIMMING -> "FS";
                };
                duckService.addDuck(id, username, email, password, typeStr, speed, endurance);
                clearInputFields();
                showInfo("Duck added successfully.");
            }
            catch (Exception e)
            {
                showAlert("Error adding duck: " + e.getMessage());
            }
        }
    }

    private void clearInputFields()
    {
        txtId.clear();
        txtUsername.clear();
        txtEmail.clear();
        txtPassword.clear();
        txtSpeed.clear();
        txtEndurance.clear();
        comboType.getSelectionModel().clearSelection();
    }

    @Override
    protected void handleDeleteAction(Duck itemToDelete)
    {
        if(service instanceof DuckService duckService)
        {
            try
            {
                duckService.deleteDuck(itemToDelete.getId());
                showInfo("Duck deleted successfully.");
            }
            catch (Exception e)
            {
                showAlert("Error deleting duck: " + e.getMessage());
            }
        }
    }

    @Override
    protected void initModel()
    {
        Pageable pageable = new Pageable(currentPage, pageSize);
        DuckType filterType = filterComboBox.getValue();
        if(service instanceof DuckService duckService)
        {
            Page<Duck> duckPage = duckService.filterDucksByType(pageable, filterType);
            this.totalNumberOfElements = duckPage.getTotalElements();
            List<Duck> ducks = StreamSupport.stream(duckPage.getElementsOnPage().spliterator(), false).toList();
            model.setAll(ducks);
            updatePageInfo();
        }
    }

    @Override
    protected void initializeViewData()
    {
        filterComboBox.getItems().clear();
        filterComboBox.getItems().add(null);
        filterComboBox.getItems().addAll(DuckType.values());
        filterComboBox.setButtonCell(new ListCell<DuckType>()
        {
            @Override
            protected void updateItem(DuckType item, boolean empty)
            {
                super.updateItem(item, empty);
                if (empty || item == null)
                {
                    setText("All Types");
                }
                else
                {
                    setText(item.toString());
                }
            }
        });
        filterComboBox.setCellFactory(lv -> new ListCell<DuckType>()
        {
            @Override
            protected void updateItem(DuckType item, boolean empty)
            {
                super.updateItem(item, empty);
                if (empty || item == null)
                {
                    setText("All Types");
                }
                else
                {
                    setText(item.toString());
                }
            }
        });
        filterComboBox.getSelectionModel().selectFirst();
        comboType.getItems().addAll(DuckType.values());
    }

    @Override
    protected void initializeTableColumns()
    {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        speedColumn.setCellValueFactory(new PropertyValueFactory<>("speed"));
        enduranceColumn.setCellValueFactory(new PropertyValueFactory<>("endurance"));
    }

    @FXML
    public void onFilterChanged(ActionEvent actionEvent)
    {
        currentPage = 0;
        initModel();
    }
}
