package org.example.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.domain.User;
import org.example.networking.Services;

public class Main extends Application
{
    private Services server;
    private User currentUser;

    public void setCurrentUser(User user)
    {
        this.currentUser = user;
    }
    
    @Override
    public void start(Stage stage) throws Exception
    {
        server = new ServicesRpcProxy("localhost", 5555);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login-view.fxml"));
        Parent root = loader.load();

        LoginController ctrl = loader.getController();
        ctrl.setServer(server);
        ctrl.setMainApp(this);

        Scene scene = new Scene(root);
        stage.setTitle("Basketball Tickets - Client");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception
    {
        if (server != null)
        {
            try
            {
                if (currentUser != null)
                {
                    server.logout(currentUser, null);
                }
            }
            catch (Exception e)
            {
                System.err.println("Error during logout: " + e.getMessage());
            }
            finally
            {
                if (server instanceof ServicesRpcProxy)
                {
                    ((ServicesRpcProxy) server).closeConnection();
                }
            }
        }
        super.stop();
        System.exit(0);
    }

     public static void main(String[] args)
     {
         Application.launch(args);
     }
}
