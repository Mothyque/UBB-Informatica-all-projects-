package com.ubb.validators;

import com.ubb.domain.friendship.Friendship;

import java.util.ArrayList;
import java.util.List;

public class FriendshipValidator implements Validator<Friendship>
{
    @Override
    public void validate(Friendship entity) throws ValidationException
    {
        List<String> errors = new ArrayList<>();
        if(entity.getId1() == null || entity.getId2() == null)
        {
            errors.add("Friendship IDs must not be null.");
        }
        if(entity.getId1() <= 0 || entity.getId2() <= 0)
        {
            errors.add("Friendship IDs must be positive integers.");
        }
        if(entity.getId1().equals(entity.getId2()))
        {
            errors.add("A user cannot be friends with themselves.");
        }
    }
}
