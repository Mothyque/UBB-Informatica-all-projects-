package com.ubb.controller;

import com.ubb.domain.duck.SwimmingDuck;
import com.ubb.domain.event.Event;
import com.ubb.domain.event.RaceEvent;
import com.ubb.service.RaceService;
import com.ubb.utils.paging.Page;
import com.ubb.utils.paging.Pageable;
import com.ubb.validators.InputException;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.event.ActionEvent;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class EventController extends PagingController<Event>
{

    @FXML private TableColumn<Event, Integer> colId;
    @FXML private TableColumn<Event, String> colType;
    @FXML private TableColumn<Event, String> colDate;
    @FXML private TableColumn<Event, String> colParticipants;
    @FXML private TableColumn<Event, String> colWinner;

    @FXML private TextField txtParticipants;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Override
    protected void handleAddAction()
    {
        if(service instanceof RaceService raceService)
        {
            try
            {
                String input = txtParticipants.getText();
                if(input == null || input.isBlank())
                {
                    throw new InputException("Participants field cannot be empty.");
                }
                List<Integer> duckIds = new ArrayList<>();
                String[] parts = input.split("\\s+");
                for(String part : parts)
                {
                    duckIds.add(Integer.parseInt(part));
                }
                raceService.addRace(duckIds);
                txtParticipants.clear();
                showInfo("Race added successfully.");
            }
            catch(InputException ex)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage());
                alert.show();
            }
            catch(NumberFormatException ex)
            {
                showAlert("Error: Invalid format for duck IDs. Please enter integers separated by spaces.");
            }
        }

    }

    @Override
    protected void handleDeleteAction(Event itemToDelete)
    {
        if(service instanceof RaceService raceService)
        {
            try
            {
                raceService.deleteRace(itemToDelete.getId());
                showInfo("Race deleted successfully.");
            }
            catch (InputException ex)
            {
                showAlert("Error: " + ex.getMessage());
            }
        }
    }

    @Override
    protected void initModel()
    {
        Pageable pageable = new Pageable(currentPage, pageSize);
        Page<Event> eventPage = service.findAllOnPage(pageable);
        totalNumberOfElements = eventPage.getTotalElements();
        List<Event> list = StreamSupport.stream(eventPage.getElementsOnPage().spliterator(), false)
                .toList();

        model.setAll(list);
        updatePageInfo();
    }

    @Override
    protected void initializeTableColumns()
    {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colType.setCellValueFactory(cellData ->
        {
            Event event = cellData.getValue();
            if (event instanceof RaceEvent)
            {
                return new SimpleStringProperty("RACE");
            }
            else
            {
                return new SimpleStringProperty("UNKNOWN");
            }
        });

        colDate.setCellValueFactory(cellData ->
        {
            Event event = cellData.getValue();
            if (event.getDate() != null)
            {
                return new SimpleStringProperty(event.getDate().format(dateFormatter));
            }
            else
            {
                return new SimpleStringProperty("N/A");
            }
        });

        colParticipants.setCellValueFactory(cellData ->
        {
            Event event = cellData.getValue();
            if (event instanceof RaceEvent raceEvent)
            {
                List<SwimmingDuck> participants = raceEvent.getParticipants();
                if (participants == null || participants.isEmpty())
                {
                    return new SimpleStringProperty("No participants");
                }
                String ids = participants.stream()
                        .map(duck -> String.valueOf(duck.getId()))
                        .collect(Collectors.joining(", ", "[", "]"));
                return new SimpleStringProperty(ids);
            }
            return new SimpleStringProperty("N/A");
        });

        colWinner.setCellValueFactory(cellData ->
        {
            Event event = cellData.getValue();
            if(event instanceof RaceEvent raceEvent)
            {
                Integer winnerId = raceEvent.getWinnerId();
                return new SimpleStringProperty(String.valueOf(winnerId));
            }
            return new SimpleStringProperty("N/A");
        });
    }

    @FXML
    public void onRunRace(ActionEvent actionEvent)
    {
        Event selectedEvent = tableView.getSelectionModel().getSelectedItem();
        if (selectedEvent == null)
        {
            showAlert("Please select a race to run.");
            return;
        }
        if(selectedEvent instanceof RaceEvent raceEvent && service instanceof RaceService raceService)
        {
            try
            {
                raceService.runRace(raceEvent.getId());
                showInfo("Race finished successfully. Winner updated.");
            }
            catch(Exception ex)
            {
                showAlert("Error running race: " + ex.getMessage());
            }
        }
        else
        {
            showAlert("Selected event is not a race.");
        }
    }
}
