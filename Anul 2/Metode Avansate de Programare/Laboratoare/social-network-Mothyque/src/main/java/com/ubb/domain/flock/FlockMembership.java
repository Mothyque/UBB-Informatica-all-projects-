package com.ubb.domain.flock;

import com.ubb.domain.Entity;
import com.ubb.utils.Tuple;

public class FlockMembership extends Entity<Tuple<Integer, Integer>> {

    public FlockMembership(Integer flockId, Integer duckId)
    {
        Tuple<Integer, Integer> id = new Tuple<>(flockId, duckId);
        setId(id);
    }

    public Integer getFlockId()
    {
        return getId().getLeft();
    }

    public Integer getDuckId()
    {
        return getId().getRight();
    }
}
