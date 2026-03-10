package com.ubb;

import atlantafx.base.theme.Dracula;
import com.ubb.controller.LoginController;
import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GuiMain extends Application
{
    private static ApplicationContext applicationContext;
    public static void setContext(ApplicationContext context)
    {
        applicationContext = context;
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Application.setUserAgentStylesheet(new Dracula().getUserAgentStylesheet());
        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/com/ubb/views/login-view.fxml"));
        Scene loginScene = new Scene(loginLoader.load(), 400, 425);
        LoginController loginController = loginLoader.getController();
        loginController.setServices(applicationContext.getDuckService(), applicationContext.getPersonService());
        loginController.setLoginData(primaryStage, applicationContext);
        primaryStage.setTitle("Social Network - Login");
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }
}