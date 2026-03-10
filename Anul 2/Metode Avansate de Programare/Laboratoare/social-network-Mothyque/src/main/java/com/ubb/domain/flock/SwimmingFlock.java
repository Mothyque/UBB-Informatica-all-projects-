package com.ubb.domain.flock;

import com.ubb.domain.duck.Duck;
import com.ubb.domain.duck.DuckType;
import com.ubb.domain.duck.SwimmingDuck;

import java.util.List;

public class SwimmingFlock extends Flock<SwimmingDuck>
{
    public SwimmingFlock(Integer id, String name)
    {
        super(id, name);
    }

    @Override
    public DuckType getType()
    {
        return DuckType.SWIMMING;
    }

    @Override
    public String toString()
    {
        String result = "SwimmingFlock{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", members=[";
        return addMembersToString((List<SwimmingDuck>) getMembers(), result);
    }
}
