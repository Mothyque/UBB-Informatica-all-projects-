package com.ubb.utils;

import com.ubb.domain.User;

public class UserSession
{
    private static UserSession instance;
    private User loggedUser;

    private UserSession() { }

    public static UserSession getInstance()
    {
        if (instance == null)
        {
            instance = new UserSession();
        }
        return instance;
    }

    public void setLoggedUser(User user)
    {
        getInstance().loggedUser = user;
    }

    public User getLoggedUser()
    {
        return getInstance().loggedUser;
    }

    public void logout()
    {
        getInstance().loggedUser = null;
    }
}
