package com.ubb.repository.file;

import com.ubb.domain.flock.FlockMembership;
import com.ubb.utils.Tuple;

public class FlockMembershipFileRepository extends FileRepository <Tuple<Integer, Integer>, FlockMembership>
{
    public FlockMembershipFileRepository(String fileName)
    {
        super(fileName);
    }

    @Override
    protected FlockMembership extractEntity(String[] attributes)
    {
        int flockId = Integer.parseInt(attributes[0]);
        int duckId = Integer.parseInt(attributes[1]);
        return new FlockMembership(flockId, duckId);
    }

    @Override
    protected String createEntityAsString(FlockMembership entity)
    {
        return entity.getFlockId() + "," + entity.getDuckId();
    }
}
