package com.ubb.validators;

import com.ubb.domain.duck.*;

import java.util.List;

public class DuckValidator implements Validator<Duck>
{
    public DuckValidator() {}

    @Override
    public void validate(Duck entity) throws ValidationException
    {
        List<String> errorList = new java.util.ArrayList<>();
        if(entity.getUsername() == null || entity.getUsername().trim().isEmpty())
        {
            errorList.add("Invalid username!");
        }

        String email = entity.getEmail();
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (email == null || !email.matches(emailPattern))
        {
            errorList.add("Invalid email!");
        }

        String password = entity.getPassword();
        if(password == null)
        {
            errorList.add("Invalid password!");
        }

        boolean tipValid = false;
        DuckType type = entity.getType();
        for(DuckType dt : DuckType.values())
        {
            if(dt == type)
            {
                tipValid = true;
                break;
            }
        }
        if(!tipValid)
        {
            errorList.add("Invalid duck type. Valid types are: FLYING, SWIMMING, FLYING_AND_SWIMMING.");
        }

        if(entity.getSpeed() <= 0)
        {
            errorList.add("The speed must be a positive number!");
        }

        if(entity.getEndurance() <= 0)
        {
            errorList.add("The endurance must be a positive number!");
        }

        if(!errorList.isEmpty())
        {
            String errors = String.join("\n", errorList);
            throw new ValidationException(errors);
        }
    }
}
