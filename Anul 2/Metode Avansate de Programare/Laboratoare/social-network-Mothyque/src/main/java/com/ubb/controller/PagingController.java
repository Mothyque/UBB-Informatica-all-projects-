package com.ubb.controller;

import com.ubb.domain.Entity;
import com.ubb.domain.event.Event;
import com.ubb.observer.Observer;
import com.ubb.service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public abstract class PagingController<E extends Entity<?>> implements Observer<Event>
{
    protected Service<E> service;
    protected ObservableList<E> model = FXCollections.observableArrayList();

    @FXML
    protected TableView<E> tableView;

    @FXML
    protected Button previousButton;

    @FXML
    protected Button nextButton;

    @FXML
    protected Label pageLabel;

    @FXML
    protected ComboBox<Integer> pageSizeComboBox;

    @FXML protected Button addButton;
    @FXML protected Button deleteButton;

    private Runnable onBackAction;

    protected int currentPage = 0;
    protected int pageSize = 5;
    protected int totalNumberOfElements = 0;

    protected abstract void handleAddAction();
    protected abstract void handleDeleteAction(E itemToDelete);

    public void setService(Service<E> service)
    {
        this.service = service;
        this.service.addObserver(this);
        initModel();
    }

    public void setOnBackAction(Runnable onBackAction)
    {
        this.onBackAction = onBackAction;
    }

    @FXML
    public void initialize()
    {
        tableView.setItems(model);

        initializeTableColumns();
        initializeViewData();
        initializePagingControls();
    }

    @FXML
    public void onBack(ActionEvent actionEvent)
    {
        if(onBackAction != null)
            onBackAction.run();
    }

    @FXML
    public void onAdd(ActionEvent actionEvent)
    {
        handleAddAction();
    }

    @FXML
    public void onDelete(ActionEvent actionEvent)
    {
        E selectedEntity = tableView.getSelectionModel().getSelectedItem();
        if(selectedEntity != null)
        {
            handleDeleteAction(selectedEntity);
        }
    }

    protected abstract void initModel();

    protected void initializeViewData() {}

    protected abstract void initializeTableColumns();

    protected void updatePageInfo()
    {
        int totalPages = (int) Math.ceil((double) totalNumberOfElements / pageSize);
        if(totalPages == 0)
            totalPages = 1;
        previousButton.setDisable(currentPage == 0);
        nextButton.setDisable(currentPage >= totalPages - 1);
        pageLabel.setText("Page " + (currentPage + 1) + " of " + totalPages);
    }

    public void onNextPage(ActionEvent actionEvent)
    {
        currentPage++;
        initModel();
    }

    public void onPreviousPage(ActionEvent actionEvent)
    {
        currentPage--;
        initModel();
    }

    protected void initializePagingControls()
    {
        if(pageSizeComboBox != null)
        {
            pageSizeComboBox.getItems().addAll(2, 5, 10, 20);
            pageSizeComboBox.setValue(pageSize);
            pageSizeComboBox.setOnAction(event ->
            {
                pageSize = pageSizeComboBox.getValue();
                currentPage = 0;
                initModel();
            });
        }
    }

    protected void showAlert(String msg)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg);
        alert.show();
    }

    protected void showInfo(String msg)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg);
        alert.show();
    }

    @Override
    public void update(Event event)
    {
        currentPage = 0;
        initModel();
    }


}
