package com.ubb.controller;
import com.ubb.ApplicationContext;
import com.ubb.domain.User;
import com.ubb.domain.duck.SwimmingDuck;
import com.ubb.domain.event.ChangeEventType;
import com.ubb.domain.event.EntityChangeEvent;
import com.ubb.domain.event.Event;
import com.ubb.domain.event.RaceEvent;
import com.ubb.observer.Observer;
import com.ubb.service.MessageService;
import com.ubb.service.RaceService;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.concurrent.CompletableFuture;

import java.util.List;

public class RaceSectionController implements Observer<Event>
{
    @FXML
    private ListView<String> systemMessagesList;

    private RaceService raceService;
    private MessageService messageService;
    private User currentUser;
    private ApplicationContext applicationContext;

    public void setServices(RaceService raceService, MessageService messageService, User currentUser, ApplicationContext applicationContext)
    {
        this.raceService = raceService;
        this.messageService = messageService;
        this.currentUser = currentUser;
        this.applicationContext = applicationContext;
        raceService.addObserver(this);
        messageService.addObserver(this);
        initView();
        loadSystemMessages();
    }

    private void initView()
    {
        systemMessagesList.setCellFactory(param -> new ListCell<>()
        {
            @Override
            protected void updateItem(String msg, boolean empty)
            {
                super.updateItem(msg, empty);
                if (empty || msg == null)
                {
                    setText(null);
                    setGraphic(null);
                    setStyle("-fx-background-color: transparent;");
                }
                else
                {
                    VBox container = new VBox();
                    container.setStyle("-fx-background-color: #34495e; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 3, 0, 0, 1);");
                    container.setPadding(new Insets(10));

                    Label textLabel = new Label(msg);
                    textLabel.setStyle("-fx-text-fill: #ecf0f1; -fx-font-size: 14px; -fx-font-weight: bold;");
                    textLabel.setWrapText(true);

                    textLabel.prefWidthProperty().bind(systemMessagesList.widthProperty().subtract(50));

                    container.getChildren().add(textLabel);

                    setGraphic(container);
                    setStyle("-fx-background-color: transparent; -fx-padding: 5px;");
                }
            }
        });
    }

    @FXML
    public void handleJoinRaceClick(ActionEvent event)
    {
        if (currentUser instanceof SwimmingDuck)
        {
            openActiveRacesWindow();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Only swimming ducks can join races.");
            alert.show();
        }
    }

    @FXML
    public void handleBackToMainMenu(ActionEvent event)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ubb/views/message-view.fxml"));
            Parent root = loader.load();

            MessageController controller = loader.getController();
            controller.setServices(
                    applicationContext.getMessageService(),
                    applicationContext.getFriendshipService(),
                    applicationContext.getCommunityService(),
                    applicationContext,
                    currentUser
            );

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 900, 600));
            stage.setTitle("Chat - " + currentUser.getUsername());
            stage.show();

            raceService.removeObserver(this);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void openActiveRacesWindow()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ubb/views/active-races-view.fxml"));
            Parent root = loader.load();
            ActiveRacesController controller = loader.getController();
            controller.setServices(raceService, (SwimmingDuck) currentUser);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root, 600, 400));
            stage.setTitle("Select a Race to Join");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadSystemMessages()
    {
        if(applicationContext == null || messageService == null)
        {
            return;
        }

        CompletableFuture.supplyAsync(() ->
                {
                    return messageService.getSystemMessages();
                }, applicationContext.getExecutorService())

                .thenAccept(messages ->
                {
                    Platform.runLater(() ->
                    {
                        systemMessagesList.getItems().setAll(messages);

                        if (!messages.isEmpty()) {
                            systemMessagesList.scrollTo(messages.size() - 1);
                        }
                    });
                })
                .exceptionally(ex ->
                {
                    ex.printStackTrace();
                    return null;
                });
    }

    @Override
    public void update(Event event)
    {
        if (event instanceof EntityChangeEvent)
        {
            EntityChangeEvent<?> changeEvent = (EntityChangeEvent<?>) event;
            if (changeEvent.getType() == ChangeEventType.UPDATE && changeEvent.getEntity() instanceof RaceEvent)
            {
                RaceEvent race = (RaceEvent) changeEvent.getEntity();

                if (race.getWinnerId() != 0)
                {
                    String winnerName = race.getParticipants().stream()
                            .filter(duck -> duck.getId().equals(race.getWinnerId()))
                            .findFirst()
                            .map(duck -> duck.getUsername())
                            .orElse("Unknown Duck");
                    String text = "🏁 RACE FINISHED! The winner is " + winnerName + "!";
                    messageService.sendGlobalMessage("SYSTEM", text);
                }
            }
            if (changeEvent.getEntity() instanceof com.ubb.domain.Message)
            {
                Platform.runLater(this::loadSystemMessages);
            }
        }
    }
}

