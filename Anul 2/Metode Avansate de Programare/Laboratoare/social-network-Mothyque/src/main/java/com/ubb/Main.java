package com.ubb;

import com.ubb.ui.Ui;
import javafx.application.Application;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        ApplicationContext applicationContext = new ApplicationContext();
        runGui(applicationContext, args);
//        runConsole(applicationContext);
    }

    private static void runGui(ApplicationContext applicationContext, String[] args)
    {
        GuiMain.setContext(applicationContext);
        Application.launch(GuiMain.class, args);
    }

    private static void runConsole(ApplicationContext applicationContext)
    {
        Ui ui = new Ui(
                applicationContext.getPersonService(),
                applicationContext.getDuckService(),
                applicationContext.getCommunityService(),
                applicationContext.getFlockService(),
                applicationContext.getRaceService(),
                applicationContext.getFriendshipService()
        );
        ui.run();
    }
}

