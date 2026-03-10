package com.ubb.controller;

import com.ubb.ApplicationContext;
import com.ubb.domain.User;
import com.ubb.domain.duck.Duck;
import com.ubb.domain.Person;
import com.ubb.service.DuckService;
import com.ubb.service.PersonService;
import com.ubb.utils.PasswordHasher;
import com.ubb.utils.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LoginController
{
    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private TextField txtPasswordVisible;
    @FXML private CheckBox chkShowPassword;
    @FXML private Label lblError;

    private DuckService duckService;
    private PersonService personService;

    private Stage primaryStage;
    private ApplicationContext context;

    public void setLoginData(Stage primaryStage, ApplicationContext context)
    {
        this.primaryStage = primaryStage;
        this.context = context;
    }

    public void setServices(DuckService duckService, PersonService personService)
    {
        this.duckService = duckService;
        this.personService = personService;
    }

    @FXML
    public void initialize()
    {
        txtPasswordVisible.setVisible(false);
        txtPasswordVisible.textProperty().bindBidirectional(txtPassword.textProperty());
    }

    @FXML void onTogglePassword()
    {
        boolean show = chkShowPassword.isSelected();
        txtPasswordVisible.setVisible(show);
        txtPassword.setVisible(!show);
    }

    @FXML
    public void onLogin()
    {
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();
        if("admin".equals(username) && "admin".equals(password))
        {
            User adminUser = new Person(0, "admin", "admin", "admin", "admin", "admin", "", "admin", 0);
            UserSession.getInstance().setLoggedUser(adminUser);
            openMainScene();
            return;
        }
        String hashedPassword = PasswordHasher.hash(password);
        User user = findUser(username, hashedPassword);
        if(user != null)
        {
            UserSession.getInstance().setLoggedUser(user);
            openChatScene(user);
            primaryStage.close();
        }
        else
        {
            lblError.setText("Invalid username or password.");
        }
    }

    private User findUser(String username, String passwordHash)
    {
        for(Duck d : duckService.getAll())
        {
            if(Objects.equals(d.getUsername(), username))
            {
                if(Objects.equals(d.getPassword(), passwordHash))
                {
                    return d;
                }
            }
        }
        for(Person p : personService.getAll())
        {
            if(Objects.equals(p.getUsername(), username))
            {
                if(Objects.equals(p.getPassword(), passwordHash))
                {
                    return p;
                }
            }
        }
        return null;
    }

    private void openMainScene()
    {
        try
        {
            FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/com/ubb/views/main-view.fxml"));
            Parent mainRoot = mainLoader.load();
            MainController mainController = mainLoader.getController();
            mainController.setServices(personService, duckService, context.getFriendshipService(), context.getCommunityService(), context.getFlockService(), context.getRaceService(), context);
            Scene mainScene = new Scene(mainRoot);
            primaryStage.setScene(mainScene);
            primaryStage.setTitle("Duck Social Network - Admin Panel");
            primaryStage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            lblError.setText("Error loading main application.");
        }
    }

    private void openChatScene(User user)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ubb/views/message-view.fxml"));
            Parent root = loader.load();

            MessageController chatController = loader.getController();

            chatController.setServices(context.getMessageService(), context.getFriendshipService(), context.getCommunityService(), context, user);

            Stage stage = new Stage();
            stage.setTitle("Chat - " + UserSession.getInstance().getLoggedUser().getUsername());
            stage.setScene(new Scene(root));
            stage.setWidth(900);
            stage.setHeight(600);
            stage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
