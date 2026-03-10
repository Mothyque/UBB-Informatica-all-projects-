package com.ubb.repository.db;

import com.ubb.domain.duck.*;
import com.ubb.repository.IDuckRepository;
import com.ubb.repository.paging.DBPagingRepository;
import com.ubb.utils.paging.Page;
import com.ubb.utils.paging.Pageable;
import com.ubb.validators.PersistenceException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DuckDBRepository extends DBPagingRepository<Integer, Duck> implements IDuckRepository
{
    public DuckDBRepository(String url, String username, String password)
    {
        super(url, username, password);
    }

    @Override
    protected String getFindAllPagedSQL()
    {
        return "SELECT * FROM ducks LIMIT ? OFFSET ?";
    }

    @Override
    protected String getFindAllSQL()
    {
        return "SELECT * FROM ducks ORDER BY id";
    }

    @Override
    protected String getFindOneSQL()
    {
        return "SELECT * FROM ducks WHERE id = ?";
    }

    @Override
    protected String getSaveSQL()
    {
        return "INSERT INTO ducks (id, username, email, password, type, speed, endurance) VALUES (?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String getDeleteSQL()
    {
        return "DELETE FROM ducks WHERE id = ?";
    }

    @Override
    protected String getUpdateSQL()
    {
        return "UPDATE ducks SET username = ?, email = ?, password = ?, type = ?, speed = ?, endurance = ? WHERE id = ?";
    }

    @Override
    protected String getSizeSQL()
    {
        return "SELECT COUNT(*) AS total FROM ducks";
    }

    @Override
    protected Duck extractEntity(ResultSet rs) throws SQLException
    {
        int id = rs.getInt("id");
        String username = rs.getString("username");
        String email = rs.getString("email");
        String password = rs.getString("password");
        String type = rs.getString("type");
        DuckType duckType = DuckType.valueOf(type);
        double speed = rs.getDouble("speed");
        double endurance = rs.getDouble("endurance");
        return switch (duckType)
        {
            case FLYING -> new FlyingDuck(id, username, email, password, speed, endurance);
            case SWIMMING -> new SwimmingDuck(id, username, email, password, speed, endurance);
            case FLYING_AND_SWIMMING -> new FlyingAndSwimmingDuck(id, username, email, password, speed, endurance);
        };
    }

    @Override
    protected void setSaveParameters(PreparedStatement statement, Duck entity) throws SQLException
    {
        statement.setInt(1, entity.getId());
        statement.setString(2, entity.getUsername());
        statement.setString(3, entity.getEmail());
        statement.setString(4, entity.getPassword());
        statement.setString(5, entity.getType().toString());
        statement.setDouble(6, entity.getSpeed());
        statement.setDouble(7, entity.getEndurance());
    }

    @Override
    protected void setFindOneDeleteParameters(PreparedStatement statement, Integer id) throws SQLException
    {
        statement.setInt(1, id);
    }

    @Override
    protected void setUpdateParameters(PreparedStatement statement, Duck entity) throws SQLException
    {
        statement.setString(1, entity.getUsername());
        statement.setString(2,  entity.getEmail());
        statement.setString(3, entity.getPassword());
        statement.setString(4, entity.getType().toString());
        statement.setDouble(5, entity.getSpeed());
        statement.setDouble(6, entity.getEndurance());
        statement.setInt(7, entity.getId());
    }

    @Override
    public Page<Duck> findAllByType(Pageable pageable, DuckType type)
    {
        List<Duck> ducks = new ArrayList<>();
        int totalFiltered = 0;

        try (Connection connection = getConnection()) {
            String countSql = "SELECT COUNT(*) AS total FROM ducks WHERE type = ?";
            try (PreparedStatement statement = connection.prepareStatement(countSql)) {
                statement.setString(1, type.toString());
                try (ResultSet rs = statement.executeQuery()) {
                    if (rs.next()) {
                        totalFiltered = rs.getInt("total");
                    }
                }
            }
            String selectSql = "SELECT * FROM ducks WHERE type = ? ORDER BY id LIMIT ? OFFSET ?";
            try (PreparedStatement statement = connection.prepareStatement(selectSql)) {
                statement.setString(1, type.toString());
                statement.setInt(2, pageable.getPageSize());
                statement.setInt(3, pageable.getPageNumber() * pageable.getPageSize());

                try (ResultSet rs = statement.executeQuery()) {
                    while (rs.next()) {
                        Duck duck = extractEntity(rs);
                        ducks.add(duck);
                    }
                }
            }
        }
        catch (SQLException e)
        {
            throw new PersistenceException("Eroare la preluarea entitatilor: " + e.getMessage());
        }
        return new Page<>(ducks, totalFiltered);
    }
}
