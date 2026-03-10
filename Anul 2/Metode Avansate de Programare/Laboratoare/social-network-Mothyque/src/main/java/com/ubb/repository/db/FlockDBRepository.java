package com.ubb.repository.db;

import com.ubb.domain.duck.Duck;
import com.ubb.domain.duck.DuckType;
import com.ubb.domain.flock.Flock;
import com.ubb.domain.flock.FlyingAndSwimmingFlock;
import com.ubb.domain.flock.FlyingFlock;
import com.ubb.domain.flock.SwimmingFlock;
import com.ubb.repository.paging.DBPagingRepository;
import com.ubb.repository.paging.PagingRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FlockDBRepository extends DBPagingRepository<Integer, Flock<? extends Duck>> implements PagingRepository<Integer, Flock<? extends Duck>>
{

    public FlockDBRepository(String url, String username, String password) {
        super(url, username, password);
    }

    @Override
    protected String getFindAllPagedSQL()
    {
        return "SELECT * FROM flocks LIMIT ? OFFSET ?";
    }

    @Override
    protected String getFindAllSQL()
    {
        return "SELECT * FROM flocks";
    }

    @Override
    protected String getFindOneSQL()
    {
        return "SELECT * FROM flocks WHERE id = ?";
    }

    @Override
    protected String getSaveSQL()
    {
        return "INSERT INTO flocks (id, name, type) VALUES (?, ?, ?)";
    }

    @Override
    protected String getDeleteSQL()
    {
        return "DELETE FROM flocks WHERE id = ?";
    }

    @Override
    protected String getUpdateSQL()
    {
        return "UPDATE flocks SET name = ?, type = ? WHERE id = ?";
    }

    @Override
    protected String getSizeSQL()
    {
        return "SELECT COUNT(*) AS total FROM flocks";
    }

    @Override
    protected Flock<? extends Duck> extractEntity(ResultSet rs) throws SQLException
    {
        Integer id = rs.getInt("id");
        String name = rs.getString("name");
        String type = rs.getString("type");
        DuckType flockType = DuckType.valueOf(type);
        return switch(flockType)
        {
            case FLYING -> new FlyingFlock(id, name);
            case SWIMMING -> new SwimmingFlock(id, name);
            case FLYING_AND_SWIMMING -> new FlyingAndSwimmingFlock(id, name);
        };
    }

    @Override
    protected void setSaveParameters(PreparedStatement statement, Flock<? extends Duck> entity) throws SQLException
    {
        statement.setInt(1, entity.getId());
        statement.setString(2, entity.getName());
        statement.setString(3, entity.getType().toString());
    }

    @Override
    protected void setFindOneDeleteParameters(PreparedStatement statement, Integer integer) throws SQLException
    {
        statement.setInt(1, integer);
    }

    @Override
    protected void setUpdateParameters(PreparedStatement statement, Flock<? extends Duck> entity) throws SQLException
    {
        statement.setString(1, entity.getName());
        statement.setString(2, entity.getType().toString());
        statement.setInt(3, entity.getId());
    }
}

