package com.ubb.controller;

import com.ubb.ApplicationContext;
import com.ubb.domain.Message;
import com.ubb.domain.Person;
import com.ubb.domain.ProfilePage;
import com.ubb.domain.User;
import com.ubb.domain.duck.Duck;
import com.ubb.domain.event.ChangeEventType;
import com.ubb.domain.event.Event;
import com.ubb.domain.event.EntityChangeEvent;
import com.ubb.domain.friendship.Friendship;
import com.ubb.observer.Observer;
import com.ubb.service.CommunityService;
import com.ubb.service.FriendshipService;
import com.ubb.service.MessageService;
import com.ubb.utils.UserSession;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

public class MessageController implements Observer<Event>
{
    @FXML private ListView<User> usersList;
    @FXML private ListView<Message> messagesList;
    @FXML private TextArea txtMessage;
    @FXML private Label lblChatWith;
    @FXML private TextField searchUserField;

    private MessageService messageService;
    private FriendshipService friendshipService;
    private CommunityService communityService;

    private ApplicationContext applicationContext;

    private User currentUser;
    private User selectedUser;

    private final User COMMUNITY_USER = new Person(-1, "📢 Community Chat", "", "", "Group", "Chat", "", "", 0);

    private final ObservableList<User> friendsModel = FXCollections.observableArrayList();
    private final ObservableList<Message> messagesModel = FXCollections.observableArrayList();

    public void setServices(MessageService messageService, FriendshipService friendshipService, CommunityService communityService, ApplicationContext applicationContext, User currentUser)
    {
        this.messageService = messageService;
        this.friendshipService = friendshipService;
        this.communityService = communityService;
        this.applicationContext = applicationContext;
        this.currentUser = currentUser;
        this.friendshipService.addObserver(this);
        this.messageService.addObserver(this);
        loadFriendsList();
    }

    @FXML
    public void initialize()
    {
        usersList.setItems(friendsModel);
        messagesList.setItems(messagesModel);

        usersList.setCellFactory(param -> new ListCell<>()
        {
            @Override
            protected void updateItem(User user, boolean empty)
            {
                super.updateItem(user, empty);
                if (empty || user == null)
                {
                    setText(null);
                }
                else
                {
                    setText(user.getUsername());
                }
            }
        });

        usersList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) ->
        {
            if(newVal != null)
            {
                selectedUser = newVal;
                if(selectedUser.getId().equals(-1))
                {
                    lblChatWith.setText("Community Chat");
                }
                else
                {
                    lblChatWith.setText("Chat with " + selectedUser.getUsername());
                }
                loadMessages(newVal);
            }
        });

        messagesList.setCellFactory(param -> new ListCell<>()
        {
            @Override
            protected void updateItem(Message message, boolean empty)
            {
                super.updateItem(message, empty);
                if (empty || message == null)
                {
                    setText(null);
                    setGraphic(null);
                }
                else
                {
                    HBox messageBox = new HBox();
                    messageBox.setPadding(new Insets(5, 10, 5, 10));
                    TextFlow bubbleContent = new TextFlow();
                    bubbleContent.setPadding(new Insets(8, 12, 8, 12));
                    bubbleContent.maxWidthProperty().bind(messagesList.widthProperty().multiply(0.7));

                    boolean isMe = message.getFrom().equals(currentUser);
                    boolean isCommunity = selectedUser.getId().equals(-1);
                    if(!isMe && isCommunity)
                    {
                        Text nameText = new Text(message.getFrom().getUsername() + ": ");
                        nameText.setStyle("-fx-font-size: 11px; -fx-fill: #ffd700; -fx-font-weight: bold;");
                        bubbleContent.getChildren().add(nameText);
                    }
                    Text msgText = new Text(message.getMessage());
                    msgText.setStyle("-fx-font-size: 13px; -fx-fill: white;");

                    bubbleContent.getChildren().add(msgText);
                    if(isMe)
                    {
                        messageBox.setAlignment(Pos.CENTER_RIGHT);
                        bubbleContent.setStyle("-fx-background-color: #2362de; -fx-background-radius: 10; -fx-padding: 10;");
                    }
                    else
                    {
                        messageBox.setAlignment(Pos.CENTER_LEFT);
                        bubbleContent.setStyle("-fx-background-color: #808080; -fx-background-radius: 10; -fx-padding: 10; -fx-border-color: #ddd; -fx-border-radius: 10;");
                    }
                    messageBox.getChildren().add(bubbleContent);
                    setGraphic(messageBox);
                }
            }
        });
    }

    @FXML
    public void onOpenFriendRequests(ActionEvent actionEvent)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ubb/views/friend-requests-view.fxml"));
            Parent friendRequestsRoot = loader.load();
            Stage stage = new Stage();
            FriendRequestsController friendRequestsController = loader.getController();
            friendRequestsController.setServices(friendshipService, applicationContext.getPersonService(), applicationContext.getDuckService(), currentUser);
            Scene scene = new Scene(friendRequestsRoot, 400, 500);
            stage.setScene(scene);
            stage.setTitle("Social Network - Friend Requests");
            stage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleSearchUser(ActionEvent actionEvent)
    {
        String username = searchUserField.getText().trim();
        if(username.isEmpty())
        {
             showAlert("Username cannot be empty.");
             return;
        }
        User foundUser = null;
        try
        {
            Iterable<Person> persons = applicationContext.getPersonService().getAll();
            foundUser = StreamSupport.stream(persons.spliterator(), false)
                    .filter(p -> p.getUsername().equalsIgnoreCase(username))
                    .findFirst()
                    .orElse(null);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        if(foundUser == null)
        {
            try
            {
                Iterable<Duck> ducks = applicationContext.getDuckService().getAll();
                foundUser = StreamSupport.stream(ducks.spliterator(), false)
                        .filter(d -> d.getUsername().equalsIgnoreCase(username))
                        .findFirst()
                        .orElse(null);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        if(foundUser == null)
        {
            showAlert("User with username '" + username + "' not found.");
            return;
        }
        openProfilePage(foundUser);
        searchUserField.clear();
    }

    private void openProfilePage(User user)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ubb/views/profile-view.fxml"));
            Parent profileRoot = loader.load();

            ProfileController profileController = loader.getController();

            int friendCount = friendshipService.getFriendsOfUser(user.getId()).size();
            String status = "NOT_FRIEND";
            if(user.getId().equals(currentUser.getId()))
            {
                status = "SELF";
            }
            else
            {
                boolean isFriend = friendshipService.getFriendsOfUser(user.getId()).stream().anyMatch(d -> d.getId().equals(currentUser.getId()));
                if(isFriend)
                {
                    status = "FRIEND";
                }
            }
            ProfilePage page = new ProfilePage(user, status, friendCount);
            profileController.setServices(friendshipService, currentUser, user, applicationContext, page);

            Stage stage = (Stage) searchUserField.getScene().getWindow();

            Scene scene = new Scene(profileRoot, 700, 500);
            stage.setScene(scene);
            stage.setTitle("Social Network - Profile: " + user.getUsername());
            stage.show();

            this.friendshipService.removeObserver(this);
            this.messageService.removeObserver(this);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    public void onNewSession(ActionEvent actionEvent)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ubb/views/login-view.fxml"));
            Parent root = loader.load();
            Stage newStage = new Stage();
            LoginController loginController = loader.getController();
            loginController.setServices(applicationContext.getDuckService(), applicationContext.getPersonService());
            loginController.setLoginData(newStage, applicationContext);
            newStage.setScene(new Scene(root, 400, 425));
            newStage.setTitle("Social Network - Login");
            newStage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void loadFriendsList()
    {
        if(currentUser == null) return;

        javafx.concurrent.Task<List<User>> task = new javafx.concurrent.Task<>()
        {
            @Override
            protected List<User> call() throws Exception
            {
                List<User> result = new ArrayList<>();

                List<User> communityMembers = communityService.getCommunityMembers(currentUser.getId());
                if(communityMembers != null && !communityMembers.isEmpty()) {
                    boolean isAlone = communityMembers.size() == 1 && communityMembers.get(0).getId().equals(currentUser.getId());
                    if(!isAlone) {
                        result.add(COMMUNITY_USER);
                    }
                }

                List<User> friends = new ArrayList<>(friendshipService.getFriendsOfUser(currentUser.getId()));

                friends.sort((u1, u2) ->
                {
                    LocalDateTime date1 = getLastMessageDate(u1);
                    LocalDateTime date2 = getLastMessageDate(u2);
                    return date2.compareTo(date1);
                });

                result.addAll(friends);
                return result;
            }
        };
        task.setOnSucceeded(e ->
        {
            List<User> computedList = task.getValue();

            User currentSelection = usersList.getSelectionModel().getSelectedItem();

            friendsModel.setAll(computedList);
            if(currentSelection != null)
            {
                for(User u : friendsModel)
                {
                    if(u.getId().equals(currentSelection.getId()))
                    {
                        usersList.getSelectionModel().select(u);
                        break;
                    }
                }
            }
        });

        task.setOnFailed(e ->
        {
            task.getException().printStackTrace();
        });
        new Thread(task).start();
    }

    private LocalDateTime getLastMessageDate(User user)
    {
        List<Message> conversation = messageService.getConversation(currentUser, user);
        if(conversation.isEmpty())
        {
            return LocalDateTime.MIN;
        }
        return conversation.get(conversation.size() - 1).getDate();
    }

    private void loadMessages(User chatWith)
    {
        if(currentUser == null || chatWith == null)
        {
            return;
        }
        List<Message> messages;
        if(chatWith.getId().equals(-1))
        {
            messages = messageService.getCommunityMessages();
        }
        else
        {
            messages = messageService.getConversation(currentUser, chatWith);
        }
        messagesModel.setAll(messages);
        Platform.runLater(() -> messagesList.scrollTo(messagesModel.size() - 1));
    }

    @FXML
    public void onSendMessage(ActionEvent actionEvent)
    {
        String messageText = txtMessage.getText().trim();
        if(selectedUser == null || messageText.isEmpty())
        {
            return;
        }
        List<User> recipients = new ArrayList<>();
        if(selectedUser.getId().equals(-1))
        {
            recipients = communityService.getCommunityMembers(currentUser.getId());
            recipients.remove(currentUser);
        }
        else
        {
            recipients.add(selectedUser);
        }
        if(!recipients.isEmpty())
        {
            messageService.sendMessage(currentUser, recipients, messageText);
            loadFriendsList();
        }
        txtMessage.clear();
    }

    @FXML
    public void onLogout(ActionEvent actionEvent)
    {
        try
        {
            UserSession.getInstance().logout();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ubb/views/login-view.fxml"));
            Parent loginRoot = loader.load();
            Stage stage = (Stage) lblChatWith.getScene().getWindow();
            LoginController loginController = loader.getController();
            loginController.setServices(applicationContext.getDuckService(), applicationContext.getPersonService());
            loginController.setLoginData(stage, applicationContext);
            Scene loginScene = new Scene(loginRoot, 400, 425);
            stage.setScene(loginScene);
            stage.sizeToScene();
            stage.setTitle("Social Network - Login");
            stage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error during logout process.");
            alert.show();
        }
    }

    @FXML
    public void onOpenRaceSection(ActionEvent actionEvent)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ubb/views/race-section-view.fxml"));
            Parent raceSectionRoot = loader.load();

            RaceSectionController controller = loader.getController();
            controller.setServices(applicationContext.getRaceService(), applicationContext.getMessageService(), currentUser, applicationContext);

            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            stage.setMinWidth(100);
            stage.setMinHeight(100);
            stage.setScene(new Scene(raceSectionRoot, 700, 500));
            stage.setTitle("Race Center - " + currentUser.getUsername());
            stage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error opening Race Section.");
            alert.show();

        }
    }

    @Override
    public void update(Event event)
    {
        if(event instanceof EntityChangeEvent)
        {
            EntityChangeEvent<?> changeEvent = (EntityChangeEvent<?>) event;
            Platform.runLater(() ->
            {
                User currentSelection = usersList.getSelectionModel().getSelectedItem();
                loadFriendsList();
                if(currentSelection != null)
                {
                    for(User u : friendsModel)
                    {
                        if(u.getId().equals(currentSelection.getId()))
                        {
                            usersList.getSelectionModel().select(u);
                            break;
                        }
                    }
                }
                if(selectedUser != null)
                {
                    loadMessages(selectedUser);
                }
                handleNotification(changeEvent);
            });
        }
    }

    private void handleNotification(EntityChangeEvent<?> event)
    {
        if (event.getType() == ChangeEventType.ADD && event.getEntity() instanceof Friendship) {
            Friendship request = (Friendship) event.getEntity();

            if (request.getId2().equals(currentUser.getId()))
            {
                String senderName = "Unknown";

                try
                {
                    Person senderPerson = applicationContext.getPersonService().findPersonById(request.getId1());
                    if (senderPerson != null)
                    {
                        senderName = senderPerson.getUsername();
                    }
                }
                catch (Exception e)
                {
                    try
                    {
                        Duck senderDuck = applicationContext.getDuckService().findDuckById(request.getId1());
                        if (senderDuck != null)
                        {
                            senderName = senderDuck.getUsername();
                        }
                    }
                    catch (Exception ex)
                    {
                        System.out.println("User ID " + request.getId1() + " not found in either service.");
                    }
                }

                String finalSenderName = senderName;
                Platform.runLater(() -> showNotificationAlert(finalSenderName));
            }
        }
    }

    private void showNotificationAlert(String senderName) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("New Friend Request");
        alert.setHeaderText(null);
        alert.setContentText("User '" + senderName + "' sent you a friend request!");
        alert.show();
    }


    private void showAlert(String msg)
    {
        Alert alert = new Alert(Alert.AlertType.WARNING, msg);
        alert.show();
    }
}
