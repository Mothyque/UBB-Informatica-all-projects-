package org.example.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.domain.Client;
import org.example.domain.ClientTicketDTO;
import org.example.domain.Match;
import org.example.domain.User;
import org.example.networking.Services;
import org.example.utils.Observer;

import java.util.Arrays;
import java.util.List;

public class MainController extends MessageController implements Observer
{
    private Services server;
    private User currentUser;
    private Main mainApp;
    private final ObservableList<Match> matchesModel = FXCollections.observableArrayList();
    private final ObservableList<ClientTicketDTO> ticketsModel = FXCollections.observableArrayList();

    @FXML
    private TableView<Match> tblMatches;

    @FXML
    private TableView<ClientTicketDTO> tblTickets;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtTicket;

    public void setServices(Services server, User user, Main mainApp)
    {
        this.server = server;
        this.currentUser = user;
        this.mainApp = mainApp;
        initData();
    }

    public void initData()
    {
        try
        {
            Match[] allMatches = server.getAllMatches();
            matchesModel.clear();
            matchesModel.addAll(Arrays.asList(allMatches));
            tblMatches.setItems(matchesModel);
            txtTicket.setText("1");
        }
        catch (Exception e)
        {
            showErrorMessage("Error loading matches: " + e.getMessage());
        }
    }

    @FXML
    public void initialize()
    {
        TableColumn<Match, String> matchCol = new TableColumn<>("Match");
        matchCol.setCellValueFactory(cellData -> {
            Match match = cellData.getValue();
            String text = match.toString();
            if (match.getAvailableSeats() == 0) {
                text += " SOLD OUT";
            }
            return new ReadOnlyObjectWrapper<>(text);
        });
        matchCol.setCellFactory(column -> new TableCell<Match, String>() {
            @Override
            protected void updateItem(String item, boolean empty)
            {
                super.updateItem(item, empty);
                if (empty || item == null)
                {
                    setText(null);
                    setStyle("");
                }
                else
                {
                    setText(item);
                    if (item.contains("SOLD OUT"))
                    {
                        setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                    }
                    else
                    {
                        setStyle("-fx-text-fill: -fx-text-background-color; -fx-font-weight: normal;");
                    }
                }
            }
        });
        tblMatches.getColumns().add(matchCol);
        tblMatches.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<ClientTicketDTO, String> ticketCol = new TableColumn<>("Client Tickets");
        ticketCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().toString()));
        tblTickets.getColumns().add(ticketCol);
        tblTickets.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    @FXML
    public void onSearch()
    {
        String name = txtName.getText();
        String address = txtAddress.getText();

        if (name.isEmpty() && address.isEmpty())
        {
            ticketsModel.clear();
            return;
        }

        try
        {
                List<ClientTicketDTO> clientTickets = server.filterTickets(name, address);
            ticketsModel.clear();
            ticketsModel.addAll(clientTickets);
            tblTickets.setItems(ticketsModel);
        }
        catch (Exception e)
        {
            showErrorMessage("Error filtering tickets: " + e.getMessage());
        }
    }

    @FXML
    public void onUpdate()
    {
        ClientTicketDTO selected = tblTickets.getSelectionModel().getSelectedItem();
        if (selected == null)
        {
            showErrorMessage("No ticket selected for update.");
            return;
        }
        try
        {
            int newNumber = Integer.parseInt(txtTicket.getText());
            if (newNumber <= 0)
            {
                showErrorMessage("Number of tickets must be positive.");
                return;
            }

            int oldNumber = selected.getNumberOfTickets();
            if (newNumber == oldNumber)
            {
                showInfoMessage("No change in number of tickets.");
                return;
            }

            server.updateTickets(selected.getMatch(), selected.getClient(), oldNumber, newNumber);
            showInfoMessage("Ticket update request sent successfully.");
            txtTicket.setText("1");
        }
        catch (NumberFormatException e)
        {
            showErrorMessage("Invalid number of tickets: " + txtTicket.getText());
        }
        catch (Exception e)
        {
            showErrorMessage("Error updating tickets: " + e.getMessage());
        }
    }

    @FXML
    public void onBuy()
    {
        String name = txtName.getText();
        String address = txtAddress.getText();
        Match selectedMatch = tblMatches.getSelectionModel().getSelectedItem();
        if (selectedMatch == null)
        {
            showErrorMessage("No match selected.");
            return;
        }
        if (name.isEmpty() || address.isEmpty())
        {
            showErrorMessage("Name and address cannot be empty.");
            return;
        }
        try
        {
            int numberOfTickets = Integer.parseInt(txtTicket.getText());
            if (numberOfTickets <= 0)
            {
                showErrorMessage("Number of tickets must be positive.");
                return;
            }
            if (selectedMatch.getAvailableSeats() < numberOfTickets)
            {
                showErrorMessage("Not enough available seats for the selected match.");
                return;
            }
            server.buyTickets(selectedMatch, name, address, numberOfTickets);
            showInfoMessage("Purchase request sent successfully.");
            txtTicket.setText("1");
        }
        catch (NumberFormatException e)
        {
            showErrorMessage("Invalid number of tickets: " + txtTicket.getText());
        }
        catch (Exception e)
        {
            showErrorMessage("Error purchasing tickets: " + e.getMessage());
        }
    }

    @FXML
    public void onLogout()
    {
        try
        {
            server.logout(currentUser, this);
            showLoginWindow();
        }
        catch (Exception e)
        {
            showErrorMessage("Error during logout: " + e.getMessage());
        }
    }

    private void showLoginWindow()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login-view.fxml"));
            Parent root = loader.load();
            LoginController ctrl = loader.getController();
            ctrl.setServer(server);
            ctrl.setMainApp(mainApp);
            Stage stage = (Stage) tblMatches.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Login");
            stage.show();
        }
        catch (Exception e)
        {
            showErrorMessage("Error loading login window: " + e.getMessage());
        }
    }

    @Override
    public void update()
    {
        javafx.application.Platform.runLater(() -> {
            initData();
            if (!txtName.getText().isEmpty() || !txtAddress.getText().isEmpty())
            {
                onSearch();
            }
            else
            {
                ticketsModel.clear();
            }
        });

    }
}
