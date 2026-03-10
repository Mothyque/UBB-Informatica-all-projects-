package com.ubb.repository.file;

import com.ubb.domain.duck.Duck;
import com.ubb.domain.duck.DuckType;
import com.ubb.domain.flock.Flock;
import com.ubb.domain.flock.FlyingAndSwimmingFlock;
import com.ubb.domain.flock.FlyingFlock;
import com.ubb.domain.flock.SwimmingFlock;

public class FlockFileRepository extends FileRepository<Integer, Flock<? extends Duck>>
{
    public FlockFileRepository(String fileName)
    {
        super(fileName);
    }

    @Override
    protected Flock<? extends Duck> extractEntity(String[] attributes)
    {
        Integer id = Integer.parseInt(attributes[0]);
        String name = attributes[1];
        String type = attributes[2];
        DuckType duckType = DuckType.valueOf(type);
        return switch (duckType)
        {
            case SWIMMING -> new SwimmingFlock(id, name);
            case FLYING -> new FlyingFlock(id, name);
            case FLYING_AND_SWIMMING -> new FlyingAndSwimmingFlock(id, name);
        };
    }

    @Override
    protected String createEntityAsString(Flock<? extends Duck> entity)
    {
        return entity.getId() + "," +
               entity.getName() + "," +
               entity.getType();
    }
}
