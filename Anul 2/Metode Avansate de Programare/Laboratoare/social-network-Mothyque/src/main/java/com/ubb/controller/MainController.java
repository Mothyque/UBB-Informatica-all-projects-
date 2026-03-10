package com.ubb.controller;

import com.ubb.domain.User;
import com.ubb.ApplicationContext;
import com.ubb.service.*;
import com.ubb.utils.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
public class MainController
{
    @FXML private BorderPane mainLayout;
    @FXML private Label lblWelcome;

    private Node welcomeView;



    private PersonService personService;
    private DuckService duckService;
    private FriendshipService friendshipService;
    private CommunityService communityService;
    private FlockService flockService;
    private RaceService raceService;

    private ApplicationContext applicationContext;

    public void setServices(PersonService personService, DuckService duckService, FriendshipService friendshipService, CommunityService communityService, FlockService flockService, RaceService raceService, ApplicationContext applicationContext)
    {
        this.personService = personService;
        this.duckService = duckService;
        this.friendshipService = friendshipService;
        this.communityService = communityService;
        this.flockService = flockService;
        this.raceService = raceService;
        this.applicationContext = applicationContext;

        User loggedUser = UserSession.getInstance().getLoggedUser();
        if (loggedUser != null)
        {
            lblWelcome.setText("Welcome, " + loggedUser.getUsername() + "!");
        }
    }

    @FXML
    public void initialize()
    {
        this.welcomeView = mainLayout.getCenter();
    }

    @FXML
    public void handleShowDucks(ActionEvent actionEvent)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ubb/views/duck-view.fxml"));
            Node duckView = loader.load();
            DuckController duckController = loader.getController();
            duckController.setService(duckService);
            setupBackAction(duckController);
            mainLayout.setCenter(duckView);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleShowPersons(ActionEvent actionEvent)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ubb/views/person-view.fxml"));
            Node personView = loader.load();
            PersonController personController = loader.getController();
            personController.setService(personService);
            setupBackAction(personController);
            mainLayout.setCenter(personView);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleShowCommunities(ActionEvent actionEvent)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ubb/views/communities-view.fxml"));
            Node communityView = loader.load();
            CommunityController communityController = loader.getController();
            communityController.setServices(communityService, friendshipService);
            communityController.setOnBackAction(() -> mainLayout.setCenter(welcomeView));
            mainLayout.setCenter(communityView);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleShowFlocks(ActionEvent actionEvent)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ubb/views/flock-view.fxml"));
            Node flockView = loader.load();
            FlockController flockController = loader.getController();
            flockController.setService(flockService);
            setupBackAction(flockController);
            mainLayout.setCenter(flockView);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleShowEvents(ActionEvent actionEvent)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ubb/views/event-view.fxml"));
            Node eventView = loader.load();

            EventController eventController = loader.getController();
            eventController.setService(raceService);
            setupBackAction(eventController);

            mainLayout.setCenter(eventView);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    public void onLogout(ActionEvent actionEvent)
    {
        try {
            UserSession.getInstance().logout();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ubb/views/login-view.fxml"));
            Parent loginRoot = loader.load();
            Stage stage = (Stage) mainLayout.getScene().getWindow();
            LoginController loginController = loader.getController();
            loginController.setLoginData(stage, applicationContext);
            loginController.setServices(duckService, personService);

            Scene loginScene = new Scene(loginRoot, 400, 400);
            stage.setScene(loginScene);
            stage.setTitle("Duck Social Network - Login");
            stage.show();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    private void setupBackAction(PagingController<?> controller)
    {
        controller.setOnBackAction(() -> mainLayout.setCenter(welcomeView));
    }
}
