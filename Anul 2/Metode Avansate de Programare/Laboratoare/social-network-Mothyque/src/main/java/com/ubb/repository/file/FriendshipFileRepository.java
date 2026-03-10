package com.ubb.repository.file;

import com.ubb.domain.friendship.Friendship;
import com.ubb.utils.Tuple;

public class FriendshipFileRepository extends FileRepository<Tuple<Integer, Integer>, Friendship>
{
    public FriendshipFileRepository(String fileName)
    {
        super(fileName);
    }

    @Override
    protected Friendship extractEntity(String[] attributes)
    {
        int id1 = Integer.parseInt(attributes[0]);
        int id2 = Integer.parseInt(attributes[1]);
        return new Friendship(id1, id2);
    }

    @Override
    protected String createEntityAsString(Friendship entity)
    {
        return entity.getId1() + "," + entity.getId2();
    }
}
