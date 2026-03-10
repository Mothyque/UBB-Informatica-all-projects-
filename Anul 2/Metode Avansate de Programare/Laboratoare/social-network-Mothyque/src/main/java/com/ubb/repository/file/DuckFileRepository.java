package com.ubb.repository.file;

import com.ubb.domain.duck.*;

public class DuckFileRepository extends FileRepository<Integer, Duck>
{
    public DuckFileRepository(String filename)
    {
        super(filename);
    }

    @Override
    protected Duck extractEntity(String[] attributes)
    {
        int id = Integer.parseInt(attributes[0]);
        String username = attributes[1];
        String email = attributes[2];
        String password = attributes[3];
        String typeString = attributes[4];
        DuckType duckType = DuckType.valueOf(typeString);
        double viteza = Double.parseDouble(attributes[5]);
        double rezistenta = Double.parseDouble(attributes[6]);
        switch (duckType)
        {
            case FLYING->
            {
                return new FlyingDuck(id, username, email, password, viteza, rezistenta);
            }
            case SWIMMING ->
            {
                return new SwimmingDuck(id, username, email, password, viteza, rezistenta);
            }
            case FLYING_AND_SWIMMING ->
            {
                return new FlyingAndSwimmingDuck(id, username, email, password, viteza, rezistenta);
            }
        }
        return null;
    }

    @Override
    protected String createEntityAsString(Duck entity)
    {
        return entity.getId() + "," +
               entity.getUsername() + "," +
               entity.getEmail() + "," +
               entity.getPassword() + "," +
               entity.getType() + "," +
               entity.getSpeed() + "," +
               entity.getEndurance();
    }
}
