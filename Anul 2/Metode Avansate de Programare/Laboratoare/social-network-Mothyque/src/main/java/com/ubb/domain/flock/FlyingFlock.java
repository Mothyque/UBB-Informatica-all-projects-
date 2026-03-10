package com.ubb.domain.flock;

import com.ubb.domain.duck.Duck;
import com.ubb.domain.duck.DuckType;
import com.ubb.domain.duck.FlyingDuck;

public class FlyingFlock  extends Flock<FlyingDuck>
{
    public FlyingFlock(Integer id, String name)
    {
        super(id, name);
    }

    @Override
    public String toString()
    {
        String result = "FlyingFlock{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", members=[";
        return addMembersToString((java.util.List<FlyingDuck>) getMembers(), result);
    }

    @Override
    public DuckType getType() {
        return DuckType.FLYING;
    }
}
