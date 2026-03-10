package com.ubb.domain.flock;

import com.ubb.domain.duck.FlyingAndSwimmingDuck;
import com.ubb.domain.duck.DuckType;

public class FlyingAndSwimmingFlock extends Flock<FlyingAndSwimmingDuck>
{
    public FlyingAndSwimmingFlock(Integer id, String name)
    {
        super(id, name);
    }

    @Override
    public DuckType getType()
    {
        return DuckType.FLYING_AND_SWIMMING;
    }

    @Override
    public String toString()
    {
        String result = "FlyingAndSwimmingFlock{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", members=[";
        return addMembersToString((java.util.List<FlyingAndSwimmingDuck>) getMembers(), result);
    }
}
