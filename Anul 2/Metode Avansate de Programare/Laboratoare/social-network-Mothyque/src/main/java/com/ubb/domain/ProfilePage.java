package com.ubb.domain;

public class ProfilePage
{
    private String username;
    private String status;
    private int friendsCount;
    private String avatarInitial;
    private String type;

    private User targetUser;

    public ProfilePage(User targetUser, String status, int friendsCount)
    {
        this.targetUser = targetUser;
        this.username = targetUser.getUsername();
        this.status = status;
        this.friendsCount = friendsCount;
        this.avatarInitial = username.isEmpty() ? "?" : username.substring(0, 1).toUpperCase();
        this.type = targetUser.getClass().getSimpleName();
    }

    public String getUsername()
    {
        return username;
    }

    public String getStatus()
    {
        return status;
    }

    public int getFriendsCount()
    {
        return friendsCount;
    }

    public String getAvatarInitial()
    {
        return avatarInitial;
    }

    public User getTargetUser()
    {
        return targetUser;
    }

    public String getType()
    {
        return type;
    }
}
