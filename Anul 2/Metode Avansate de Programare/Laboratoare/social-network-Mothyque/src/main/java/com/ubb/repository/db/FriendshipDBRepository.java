package com.ubb.repository.db;

import com.ubb.domain.friendship.Friendship;
import com.ubb.domain.friendship.FriendshipStatus;
import com.ubb.repository.paging.DBPagingRepository;
import com.ubb.repository.paging.PagingRepository;
import com.ubb.utils.Tuple;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class FriendshipDBRepository extends DBPagingRepository<Tuple<Integer, Integer>, Friendship> implements PagingRepository<Tuple<Integer, Integer>, Friendship>
{
    public FriendshipDBRepository(String url, String username, String password)
    {
        super(url, username, password);
    }

    @Override
    protected String getFindAllPagedSQL()
    {
        return "SELECT * FROM friendships LIMIT ? OFFSET ?";
    }

    @Override
    protected String getFindAllSQL()
    {
        return "SELECT * FROM friendships";
    }

    @Override
    protected String getFindOneSQL()
    {
        return "SELECT * FROM friendships WHERE user_id_1=? and user_id_2=?";
    }

    @Override
    protected String getSaveSQL()
    {
        return "INSERT INTO friendships (user_id_1, user_id_2, date, status) VALUES (?, ?, ?, ?)";
    }

    @Override
    protected String getDeleteSQL()
    {
        return "DELETE FROM friendships WHERE user_id_1=? and user_id_2=?";
    }

    @Override
    protected String getUpdateSQL()
    {
        return "UPDATE friendships SET status = ? WHERE user_id_1 = ? AND user_id_2 = ?";
    }

    @Override
    protected String getSizeSQL()
    {
        return "SELECT COUNT(*) AS total FROM friendships";
    }

    @Override
    protected Friendship extractEntity(ResultSet rs) throws SQLException
    {
        Integer id1 = rs.getInt("user_id_1");
        Integer id2 = rs.getInt("user_id_2");
        Timestamp timestamp = rs.getTimestamp("date");
        var date = (timestamp != null) ? timestamp.toLocalDateTime() : null;
        String statusStr = rs.getString("status");
        FriendshipStatus status = FriendshipStatus.valueOf(statusStr);
        return new Friendship(id1, id2, date, status);
    }

    @Override
    protected void setSaveParameters(PreparedStatement statement, Friendship entity) throws SQLException
    {
        statement.setInt(1, entity.getId1());
        statement.setInt(2, entity.getId2());
        statement.setTimestamp(3, Timestamp.valueOf(entity.getDate()));
        statement.setString(4, entity.getStatus().toString());
    }

    @Override
    protected void setFindOneDeleteParameters(PreparedStatement statement, Tuple<Integer, Integer> id) throws SQLException
    {
        statement.setInt(1, id.getLeft());
        statement.setInt(2, id.getRight());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement statement, Friendship entity) throws SQLException
    {
        statement.setString(1, entity.getStatus().toString());
        statement.setInt(2, entity.getId1());
        statement.setInt(3, entity.getId2());
    }
}
