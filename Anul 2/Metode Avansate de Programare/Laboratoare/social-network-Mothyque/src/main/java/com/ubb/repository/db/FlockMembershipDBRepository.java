package com.ubb.repository.db;

import com.ubb.domain.flock.FlockMembership;
import com.ubb.repository.paging.DBPagingRepository;
import com.ubb.repository.paging.PagingRepository;
import com.ubb.utils.Tuple;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FlockMembershipDBRepository extends DBPagingRepository<Tuple<Integer, Integer>, FlockMembership> implements PagingRepository<Tuple<Integer, Integer>, FlockMembership>
{
    public FlockMembershipDBRepository(String url, String username, String password)
    {
        super(url, username, password);
    }

    @Override
    protected String getFindAllPagedSQL()
    {
        return "SELECT * FROM flock_memberships LIMIT ? OFFSET ?";
    }

    @Override
    protected String getFindAllSQL()
    {
        return "SELECT * FROM flock_memberships";
    }

    @Override
    protected String getFindOneSQL()
    {
        return "SELECT * FROM flock_memberships WHERE flock_id=? and duck_id=?";
    }

    @Override
    protected String getSaveSQL()
    {
        return "INSERT INTO flock_memberships (flock_id, duck_id) VALUES (?, ?)";
    }

    @Override
    protected String getDeleteSQL()
    {
        return "DELETE FROM flock_memberships WHERE flock_id=? and duck_id=?";
    }

    @Override
    protected String getUpdateSQL()
    {
        return "";
    }

    @Override
    protected String getSizeSQL()
    {
        return "SELECT COUNT(*) AS total FROM flock_memberships";
    }

    @Override
    protected FlockMembership extractEntity(ResultSet rs) throws SQLException
    {
        Integer flockId = rs.getInt("flock_id");
        Integer duckId = rs.getInt("duck_id");
        return new FlockMembership(flockId, duckId);
    }

    @Override
    protected void setSaveParameters(PreparedStatement statement, FlockMembership entity) throws SQLException
    {
        statement.setInt(1, entity.getFlockId());
        statement.setInt(2, entity.getDuckId());

    }

    @Override
    protected void setFindOneDeleteParameters(PreparedStatement statement, Tuple<Integer, Integer> id) throws SQLException
    {
        statement.setInt(1, id.getLeft());
        statement.setInt(2, id.getRight());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement statement, FlockMembership entity) throws SQLException
    {
    }
}

