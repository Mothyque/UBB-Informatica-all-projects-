package com.ubb.domain;


import java.util.List;
import java.util.Objects;

public abstract class User extends Entity<Integer>
{
    private String username;
    private String email;
    private String password;

    public User(Integer id, String username, String email, String password)
    {
        super(id);
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername()
    {
        return username;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void login(String username, String password)
    {
        if(this.username.equals(username) && this.password.equals(password))
        {
            System.out.println("Login successful!");
        }
        else
        {
            throw new IllegalArgumentException("Invalid username or password");
        }
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o) return true;
        if(!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getId());
    }

    public void logout()
    {
        System.out.println("Logout successful!");
    }

}