package com.ubb.controller;

import com.ubb.domain.duck.SwimmingDuck;
import com.ubb.domain.event.ChangeEventType;
import com.ubb.domain.event.EntityChangeEvent;
import com.ubb.domain.event.Event;
import com.ubb.domain.event.RaceEvent;
import com.ubb.observer.Observer;
import com.ubb.service.RaceService;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class ActiveRacesController implements Observer<Event> {

    @FXML private TableView<Event> tblActiveRaces;
    @FXML private TableColumn<Event, Integer> colId;
    @FXML private TableColumn<Event, String> colDate;
    @FXML private TableColumn<Event, String> colParticipantsCount;
    @FXML private TableColumn<Event, String> colStatus;

    private RaceService raceService;
    private SwimmingDuck currentDuck;

    private final ObservableList<Event> model = FXCollections.observableArrayList();
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public void setServices(RaceService raceService, SwimmingDuck duck)
    {
        this.raceService = raceService;
        this.currentDuck = duck;

        this.raceService.addObserver(this);

        initializeTable();
        loadActiveRaces();
    }

    private void initializeTable() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        colDate.setCellValueFactory(cellData ->
        {
            if (cellData.getValue().getDate() != null)
                return new SimpleStringProperty(cellData.getValue().getDate().format(dateFormatter));
            return new SimpleStringProperty("N/A");
        });

        colParticipantsCount.setCellValueFactory(cellData ->
        {
            if (cellData.getValue() instanceof RaceEvent race)
            {
                return new SimpleStringProperty(race.getParticipants().size() + " Ducks");
            }
            return new SimpleStringProperty("0");
        });

        colStatus.setCellValueFactory(cellData ->
        {
            if (cellData.getValue() instanceof RaceEvent race)
            {
                boolean joined = race.getParticipants().stream()
                        .anyMatch(d -> d.getId().equals(currentDuck.getId()));
                return new SimpleStringProperty(joined ? "✅ JOINED" : "OPEN");
            }
            return new SimpleStringProperty("-");
        });

        tblActiveRaces.setItems(model);
    }

    private void loadActiveRaces()
    {
        List<Event> activeRaces = raceService.getAllActiveRaces();
        model.setAll(activeRaces);
    }

    @FXML
    public void onJoinSelected(ActionEvent actionEvent) {
        Event selected = tblActiveRaces.getSelectionModel().getSelectedItem();

        if (selected == null)
        {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please select a race first!");
            return;
        }

        try
        {
            raceService.addParticipant(selected.getId(), currentDuck.getId());

            showAlert(Alert.AlertType.INFORMATION, "Success", "You have successfully joined the race!");
            loadActiveRaces();

        } catch (Exception e)
        {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @Override
    public void update(Event event)
    {
        if (event instanceof EntityChangeEvent)
        {
            EntityChangeEvent<?> changeEvent = (EntityChangeEvent<?>) event;
            Platform.runLater(this::loadActiveRaces);
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content)
    {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.show();
    }
}