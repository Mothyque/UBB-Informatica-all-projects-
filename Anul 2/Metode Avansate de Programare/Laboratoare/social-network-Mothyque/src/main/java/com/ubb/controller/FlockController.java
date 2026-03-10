package com.ubb.controller;
import com.ubb.domain.duck.Duck;
import com.ubb.domain.duck.DuckType;
import com.ubb.domain.flock.Flock;
import com.ubb.service.FlockService;
import com.ubb.utils.paging.Page;
import com.ubb.utils.paging.Pageable;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
public class FlockController extends PagingController<Flock<? extends Duck>>
{
    @FXML private TableColumn<Flock<? extends Duck>, Integer> colId;
    @FXML private TableColumn<Flock<? extends Duck>, String> colName;
    @FXML private TableColumn<Flock<? extends Duck>, DuckType> colType;

    @FXML private TableColumn <Flock<? extends Duck>, String> colMembers;

    @FXML private TextField txtId;
    @FXML private TextField txtName;
    @FXML private ComboBox<DuckType> comboType;

    @FXML private TextField txtDuckId;

    @Override
    protected void handleAddAction()
    {
        if(service instanceof FlockService flockService)
        {
            try
            {
                int id = Integer.parseInt(txtId.getText());
                String name = txtName.getText();
                DuckType type = comboType.getValue();
                flockService.addFlock(id, name, type);
                txtId.clear();
                txtName.clear();
                comboType.setValue(null);
                showInfo("Flock added successfully.");
            }
            catch(Exception ex)
            {
                showAlert("Error: " + ex.getMessage());
            }
        }
    }

    @Override
    protected void handleDeleteAction(Flock<? extends Duck> itemToDelete)
    {
        if(service instanceof FlockService flockService)
        {
            try
            {
                flockService.deleteFlock(itemToDelete.getId());
                showInfo("Flock deleted successfully.");
            }
            catch(Exception ex)
            {
                showAlert("Error: + " + ex.getMessage());
            }
        }

    }

    @FXML
    public void onAddMember(ActionEvent event)
    {
        Flock<? extends Duck> selectedFlock = tableView.getSelectionModel().getSelectedItem();
        if(selectedFlock == null)
        {
            showAlert("Please select a flock to add a member to.");
            return;
        }
        if(service instanceof FlockService flockService)
        {
            try
            {
                int duckId = Integer.parseInt(txtDuckId.getText());
                flockService.addMemberToFlock(selectedFlock.getId(), duckId);
                txtDuckId.clear();
                showInfo("Member added successfully.");
            }
            catch(Exception ex)
            {
                showAlert("Error: + " + ex.getMessage());
            }
        }
    }

    @FXML
    public void onRemoveMember(ActionEvent event)
    {
        Flock<? extends Duck> selectedFlock = tableView.getSelectionModel().getSelectedItem();
        if(selectedFlock == null)
        {
            showAlert("Please select a flock to remove a member from.");
            return;
        }
        if(service instanceof FlockService flockService)
        {
            try
            {
                int duckId = Integer.parseInt(txtDuckId.getText());
                flockService.removeMemberFromFlock(selectedFlock.getId(), duckId);
                txtDuckId.clear();
                showInfo("Member removed successfully.");
            }
            catch(Exception ex)
            {
                showAlert("Error: + " + ex.getMessage());
            }
        }
    }

    @Override
    protected void initModel()
    {
        Pageable pageable = new Pageable(currentPage, pageSize);
        Page<Flock<? extends Duck>> flockPage = service.findAllOnPage(pageable);
        totalNumberOfElements = flockPage.getTotalElements();
        List<Flock<? extends Duck>> list = StreamSupport.stream(flockPage.getElementsOnPage().spliterator(), false).toList();
        model.setAll(list);
        updatePageInfo();
    }

    @Override
    protected void initializeTableColumns()
    {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colMembers.setCellValueFactory(cellData -> {
            Flock<? extends Duck> flock = cellData.getValue();
            List<? extends Duck> members = flock.getMembers();
            if(members == null || members.isEmpty())
            {
                return new SimpleStringProperty("No members");
            }
            String membersStr = members.stream()
                    .map(duck -> String.valueOf(duck.getId()))
                    .collect(Collectors.joining(", ", "[", "]"));
            return new SimpleStringProperty(membersStr);
        });
    }

    @Override
    protected void initializeViewData()
    {
        comboType.getItems().addAll(DuckType.values());
    }
}
