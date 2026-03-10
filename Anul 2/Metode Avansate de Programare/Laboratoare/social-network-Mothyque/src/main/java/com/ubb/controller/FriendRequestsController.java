package com.ubb.controller;

import com.ubb.domain.Person;
import com.ubb.domain.duck.Duck;
import com.ubb.domain.event.Event;
import com.ubb.domain.friendship.FriendRequestDTO;
import com.ubb.domain.friendship.Friendship;
import com.ubb.domain.friendship.FriendshipStatus;
import com.ubb.domain.User;
import com.ubb.domain.event.ChangeEventType;
import com.ubb.domain.event.EntityChangeEvent;
import com.ubb.observer.Observer;
import com.ubb.service.FriendshipService;
import com.ubb.service.Service; // Sau UserService-ul tău
import com.ubb.utils.UserSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.Optional;

public class FriendRequestsController implements Observer<Event>
{
    @FXML private TableView<FriendRequestDTO> requestsTable;
    @FXML private TableColumn<FriendRequestDTO, String> colFrom;
    @FXML private TableColumn<FriendRequestDTO, String> colDate;
    @FXML private TableColumn<FriendRequestDTO, String> colStatus;
    @FXML private TextField txtUsernameToAdd;

    private FriendshipService friendshipService;
    private Service<Person> personService;
    private Service<Duck> duckService;

    private User currentUser;

    ObservableList<FriendRequestDTO> requestsList = FXCollections.observableArrayList();

    public void setServices(FriendshipService friendshipService, Service<Person> personService, Service<Duck> duckService, User user)
    {
        this.friendshipService = friendshipService;
        this.personService = personService;
        this.duckService = duckService;
        this.currentUser = user;
        friendshipService.addObserver(this);
        initModel();
    }

    @FXML
    public void initialize()
    {
        colFrom.setCellValueFactory(new PropertyValueFactory<>("senderName"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        requestsTable.setItems(requestsList);
    }

    public void initModel()
    {
        List<Friendship> pendingRequests = friendshipService.getPendingFriendRequests(currentUser.getId());

        List<FriendRequestDTO> dtos = pendingRequests.stream().map(request -> {
            Integer senderId = request.getId1();
            String senderName = "Unknown ID: " + senderId;
            Optional<User> senderOpt = findUserById(senderId);
            if(senderOpt.isPresent())
            {
               senderName = senderOpt.get().getUsername();
            }
            return new FriendRequestDTO(request, senderName);
        }).toList();
        requestsList.setAll(dtos);
    }

    @FXML
    public void onAccept()
    {
        FriendRequestDTO selected = requestsTable.getSelectionModel().getSelectedItem();
        if(selected == null)
        {
            showError("No request selected");
            return;
        }
        try
        {
            friendshipService.respondToFriendRequest(selected.getEntity(), FriendshipStatus.APPROVED);
        }
        catch (Exception e)
        {
            showError(e.getMessage());
        }
    }

    @FXML
    public void onReject()
    {
        FriendRequestDTO selected = requestsTable.getSelectionModel().getSelectedItem();
        if(selected == null)
        {
            showError("No request selected");
            return;
        }
        try
        {
            friendshipService.respondToFriendRequest(selected.getEntity(), FriendshipStatus.REJECTED);
        }
        catch (Exception e)
        {
            showError(e.getMessage());
        }
    }

    @FXML
    public void onSendRequest()
    {
        String username = txtUsernameToAdd.getText().trim();
        if(username.isEmpty())
        {
            showError("Username cannot be empty");
            return;
        }
        try
        {
            Optional<User> receiverOpt = findUserByUsername(username);
            if(receiverOpt.isEmpty())
            {
                showError("User not found");
                return;
            }
            User receiver = receiverOpt.get();
            friendshipService.sendFriendRequest(currentUser.getId(), receiver.getId());
            txtUsernameToAdd.clear();
        }
        catch (Exception e)
        {
            showError(e.getMessage());
        }
    }

    @Override
    public void update(Event event)
    {
        initModel();
    }

    private Optional<User> findUserById(int id)
    {
        for(Duck d : duckService.getAll())
        {
            if(d.getId() == id)
            {
                return Optional.of(d);
            }
        }
        for(Person p : personService.getAll())
        {
            if(p.getId() == id)
            {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

    private Optional<User> findUserByUsername(String username)
    {
        for(Duck d : duckService.getAll())
        {
            if(d.getUsername().equals(username))
            {
                return Optional.of(d);
            }
        }
        for(Person p : personService.getAll())
        {
            if(p.getUsername().equals(username))
            {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

    private void showError(String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }
}
