package org.example.service;

import org.example.domain.User;
import org.example.repository.Repository;
import org.example.repository.UserRepository;

import java.security.MessageDigest;

public class UserService extends Service<Integer, User>
{
    public UserService(Repository<Integer, User> repository)
    {
        super(repository);
    }

    public User findByUsername(String username)
    {
        return ((UserRepository) repository).findByUsername(username);
    }

    public User authenticate(String username, String password)
    {
        User user = findByUsername(username);
        if (user != null && user.getPassword().equals(hashPassword(password)))
        {
            return user;
        }
        return null;
    }

    public String hashPassword(String password)
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest)
            {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        }
        catch (Exception e)
        {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}
