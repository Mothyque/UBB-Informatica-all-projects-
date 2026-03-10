package com.ubb.validators;

import com.ubb.domain.Person;

import java.util.List;

public class PersonValidator implements Validator<Person>
{
    public PersonValidator() {
    }

    @Override
    public void validate(Person entity) throws ValidationException
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
        if (entity.getLastName() == null || entity.getLastName().trim().isEmpty())
        {
            errorList.add("Invalid last name!");
        }
        if (entity.getFirstName() == null || entity.getFirstName().trim().isEmpty())
        {
            errorList.add("Invalid first name!");
        }
        if (entity.getBirthDate() == null || entity.getBirthDate().trim().isEmpty())
        {
            errorList.add("Invalid birth date!");
        }
        String dataPattern = "\\d{2}.\\d{2}.\\d{4}";
        if (!entity.getBirthDate().matches(dataPattern))
        {
            errorList.add("Invalid birth date format! Required format: dd.mm.yyyy");
        }
        int zi = Integer.parseInt(entity.getBirthDate().substring(0, 2));
        int luna = Integer.parseInt(entity.getBirthDate().substring(3, 5));
        int an = Integer.parseInt(entity.getBirthDate().substring(6));
        if (luna < 1 || luna > 12)
        {
            errorList.add("Invalid month in birth date!");
        }
        if ((luna == 1 || luna == 3 || luna == 5 || luna == 7 || luna == 8 || luna == 10 || luna == 12) && (zi < 1 || zi > 31))
        {
            errorList.add("Invalid day in birth date!");
        }
        if ((luna == 4 || luna == 6 || luna == 9 || luna == 11) && (zi < 1 || zi > 30))
        {
            errorList.add("Invalid day in birth date!");
        }
        if(luna == 2)
        {
            boolean esteAnBisect = (an % 4 == 0 && an % 100 != 0) || (an % 400 == 0);
            if (esteAnBisect)
            {
                if (zi < 1 || zi > 29)
                {
                    errorList.add("Invalid day in birth date!");
                }
            } else {
                if (zi < 1 || zi > 28)
                {
                    errorList.add("Invalid day in birth date!");
                }
            }
        }
        if(entity.getOccupation() == null || entity.getOccupation().trim().isEmpty())
        {
            errorList.add("Invalid occupation!");
        }

        if (entity.getEmpathyLevel() < 1 || entity.getEmpathyLevel() > 10)
        {
            errorList.add("Invalid empathy level! It must be between 1 and 10.");
        }

        if (!errorList.isEmpty())
        {
            String errors = String.join("\n", errorList);
            throw new ValidationException(errors);
        }
    }
}
