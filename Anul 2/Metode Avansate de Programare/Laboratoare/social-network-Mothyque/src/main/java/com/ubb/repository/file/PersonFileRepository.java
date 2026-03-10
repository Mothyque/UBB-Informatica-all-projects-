package com.ubb.repository.file;

import com.ubb.domain.Person;

public class PersonFileRepository extends FileRepository<Integer, Person>
{
    public PersonFileRepository(String fileName)
    {
        super(fileName);
    }

    @Override
    protected Person extractEntity(String[] attributes)
    {
        int id = Integer.parseInt(attributes[0]);
        String username = attributes[1];
        String email = attributes[2];
        String password = attributes[3];
        String nume = attributes[4];
        String prenume = attributes[5];
        String dataNasterii = attributes[6];
        String ocupatie = attributes[7];
        int nivelEmpatie = Integer.parseInt(attributes[8]);
        return new Person(id, username, email, password, nume, prenume, dataNasterii, ocupatie, nivelEmpatie);
    }

    @Override
    protected String createEntityAsString(Person entity)
    {
        return entity.getId() + "," + entity.getUsername() + "," + entity.getEmail() + "," + entity.getPassword() + "," +
               entity.getLastName() + "," + entity.getFirstName() + "," + entity.getBirthDate() + "," +
               entity.getOccupation() + "," + entity.getEmpathyLevel();
    }
}
