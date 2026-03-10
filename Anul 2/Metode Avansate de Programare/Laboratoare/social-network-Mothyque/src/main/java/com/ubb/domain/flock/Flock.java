package com.ubb.domain.flock;

import com.ubb.domain.Entity;
import com.ubb.domain.duck.Duck;
import com.ubb.domain.duck.DuckType;
import com.ubb.domain.duck.FlyingDuck;
import com.ubb.utils.Tuple;

import java.util.List;

public abstract class Flock<T extends Duck> extends Entity<Integer> {
    private String name;
    private final List<T> members;

    public Flock(int id, String name)
    {
        super(id);
        this.name = name;
        this.members = new java.util.ArrayList<>();
    }

    public String getName()
    {
        return name;
    }

    public List<T> getMembers()
    {
        return members;
    }

    public void addMember(T member)
    {
        members.add(member);
    }

    public void removeMember(T member)
    {
        members.remove(member);
    }

    public void setName(String name)
    {
        this.name = name;
    }

    protected String addMembersToString(List<T> ducks, String string)
    {
        for (int i = 0; i < ducks.size(); i++)
        {
            string += ducks.get(i).toString();
            if (i < ducks.size() - 1)
            {
                string += ", ";
            }
        }
        string += "]}";
        return string;
    }

    public abstract DuckType getType();

    public Tuple<Double, Double> getAveragePerformance()
    {
        double totalSpeed = 0;
        double totalEndurance = 0;
        int count = members.size();

        for (T member : members)
        {
            totalSpeed += member.getSpeed();
            totalEndurance += member.getEndurance();
        }

        if (count == 0)
        {
            return new Tuple<>(0.0, 0.0);
        }

        return new Tuple<>(totalSpeed / count, totalEndurance / count);
    }
}
