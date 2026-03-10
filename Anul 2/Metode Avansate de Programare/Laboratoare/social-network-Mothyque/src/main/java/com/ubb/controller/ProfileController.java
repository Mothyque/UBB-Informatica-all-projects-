package com.ubb.controller;

import com.ubb.ApplicationContext;
import com.ubb.domain.ProfilePage;
import com.ubb.domain.User;
import com.ubb.domain.friendship.Friendship;
import com.ubb.service.FriendshipService;
import com.ubb.utils.Tuple;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ProfileController
{
    @FXML private Label avatarLabel;
    @FXML private Label usernameLabel;
    @FXML private Label typeLabel;
    @FXML private Label friendsCountLabel;
    @FXML private Button addFriendButton;

    private FriendshipService friendshipService;
    private User currentUser;
    private User profileUser;
    private ApplicationContext applicationContext;
    private ProfilePage profilePage;

    public void setServices(FriendshipService friendshipService, User currentUser, User profileUser, ApplicationContext applicationContext, ProfilePage profilePage)
    {
        this.friendshipService = friendshipService;
        this.currentUser = currentUser;
        this.profileUser = profileUser;
        this.applicationContext = applicationContext;
        this.profilePage = profilePage;
        loadProfile();
    }

    private void loadProfile()
    {
        usernameLabel.setText(profileUser.getUsername());
        typeLabel.setText(profilePage.getType());
        friendsCountLabel.setText("Friends: " + profilePage.getFriendsCount());
        avatarLabel.setText(profilePage.getAvatarInitial());
        typeLabel.setText(profilePage.getType());

        configureActionButton(profilePage.getStatus());
    }

    private void configureActionButton(String status)
    {
        switch (status)
        {
            case "SELF":
                addFriendButton.setText("It's You!");
                addFriendButton.setDisable(true);
                addFriendButton.setStyle("-fx-background-color: #6272a4; -fx-text-fill: white; -fx-background-radius: 8;");
                break;
            case "FRIEND":
                addFriendButton.setText("✔ Friends");
                addFriendButton.setDisable(true);
                addFriendButton.setStyle("-fx-background-color: #50fa7b; -fx-text-fill: #282a36; -fx-background-radius: 8; -fx-font-weight: bold;");
                break;
            default:
                addFriendButton.setText("➕ Add Friend");
                addFriendButton.setDisable(false);
                addFriendButton.setStyle("-fx-background-color: #bd93f9; -fx-text-fill: #282a36; -fx-background-radius: 8; -fx-font-weight: bold;");
                break;
        }
    }

    @FXML
    public void onAddFriend(ActionEvent event)
    {
        try
        {
            friendshipService.sendFriendRequest(currentUser.getId(), profileUser.getId());
            addFriendButton.setText("Request Sent");
            addFriendButton.setDisable(true);
            addFriendButton.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white; -fx-background-radius: 8;");
        }
        catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @FXML
    public void onBackToMenu(ActionEvent event)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ubb/views/message-view.fxml"));
            Parent root = loader.load();
            MessageController messageController = loader.getController();

            messageController.setServices(
                    applicationContext.getMessageService(),
                    applicationContext.getFriendshipService(),
                    applicationContext.getCommunityService(),
                    applicationContext,
                    currentUser
                    );

            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 900, 600));
            stage.setTitle("Chat - " + currentUser.getUsername());
            stage.show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
