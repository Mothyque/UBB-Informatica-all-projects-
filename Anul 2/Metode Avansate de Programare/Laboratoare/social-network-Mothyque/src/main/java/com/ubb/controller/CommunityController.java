package com.ubb.controller;

import com.ubb.domain.friendship.Friendship;
import com.ubb.domain.event.Event;
import com.ubb.service.CommunityService;
import com.ubb.service.FriendshipService;
import com.ubb.utils.paging.Page;
import com.ubb.utils.paging.Pageable;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.stream.StreamSupport;

public class CommunityController extends PagingController<Friendship>
{
    @FXML private Label lblNumberOfCommunities;
    @FXML private Label lblMostSociableCommunity;

    @FXML private TextField txtId1;
    @FXML private TextField txtId2;

    @FXML private TableColumn<Friendship, Integer> colId1;
    @FXML private TableColumn<Friendship, Integer> colId2;

    private Runnable onBackAction;
    private CommunityService communityService;

    private FriendshipService getFriendshipService()
    {
        return (FriendshipService) this.service;
    }

    public void setServices(CommunityService communityService, FriendshipService friendshipService)
    {
        this.communityService = communityService;
        super.setService(friendshipService);
        loadStatistics();
    }

    @Override
    protected void handleAddAction() {}

    @Override
    protected void handleDeleteAction(Friendship itemToDelete) {}

    public void setOnBackAction(Runnable onBackAction)
    {
        this.onBackAction = onBackAction;
    }

    @FXML
    public void onBack(ActionEvent actionEvent)
    {
        if(onBackAction != null)
            onBackAction.run();
    }

    @Override
    protected void initModel()
    {
        Pageable pageable = new Pageable(currentPage, pageSize);
        Page<Friendship> page = getFriendshipService().findAllOnPage(pageable);
        totalNumberOfElements = page.getTotalElements();
        List<Friendship> list = StreamSupport.stream(page.getElementsOnPage().spliterator(), false).toList();
        model.setAll(list);
        updatePageInfo();
        loadStatistics();
    }

    private void loadStatistics()
    {
        int numberOfCommunities = communityService.calculateNumberOfCommunities();
        lblNumberOfCommunities.setText("Numarul de comunitati: " + String.valueOf(numberOfCommunities));

        int mostSociableCommunity = communityService.findLargestCommunitySize();
        lblMostSociableCommunity.setText("Cea mai sociabila comunitate: " + String.valueOf(mostSociableCommunity));
    }

    @FXML
    public void onAddFriendship(ActionEvent actionEvent)
    {
        try
        {
            int id1 = Integer.parseInt(txtId1.getText());
            int id2 = Integer.parseInt(txtId2.getText());
            getFriendshipService().addFriendship(id1, id2);
            txtId1.clear();
            txtId2.clear();
            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setContentText("Prietenie adaugata cu succes.");
            info.show();
        }
        catch(Exception ex)
        {
            showAlert("Eroare: " + ex.getMessage());
        }
    }

    @FXML
    public void onRemoveFriendship(ActionEvent actionEvent)
    {
        try
        {
            int id1 = Integer.parseInt(txtId1.getText());
            int id2 = Integer.parseInt(txtId2.getText());

            getFriendshipService().deleteFriendship(id1, id2);

            txtId1.clear();
            txtId2.clear();
            showInfo("Prietenie stearsa cu succes.");
        }
        catch(Exception ex)
        {
            showAlert("Eroare: " + ex.getMessage());
        }
    }

    @Override
    protected void initializeTableColumns()
    {
        colId1.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId1()));
        colId2.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId2()));
    }

    @Override
    public void update(Event event)
    {
        super.update(event);
    }
}
