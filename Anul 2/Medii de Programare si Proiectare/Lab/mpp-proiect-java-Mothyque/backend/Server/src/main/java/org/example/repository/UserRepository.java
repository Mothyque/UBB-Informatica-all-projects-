package org.example.repository;

import org.example.domain.User;

public interface UserRepository extends Repository<Integer, User>
{
    public User findByUsername(String username);
}
