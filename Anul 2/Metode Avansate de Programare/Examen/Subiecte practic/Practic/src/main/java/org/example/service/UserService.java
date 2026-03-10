package org.example.service;

import org.example.domain.User;
import org.example.repository.UserRepository;

import java.util.Objects;

public class UserService
{
    private UserRepository userRepository;

    public UserService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    public Iterable<User> findAll()
    {
        return userRepository.findAll();
    }

    public User getUserByName(String name)
    {
        for(User user : userRepository.findAll())
        {
            if(Objects.equals(user.getName().toLowerCase(), name))
            {
                return user;
            }
        }
        return null;
    }

    public int size()
    {
        return userRepository.size();
    }
}
