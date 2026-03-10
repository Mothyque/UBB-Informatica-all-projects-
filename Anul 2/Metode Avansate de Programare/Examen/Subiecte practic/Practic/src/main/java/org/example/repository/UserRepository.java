package org.example.repository;

import org.example.domain.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository extends DBRepository<Integer, User>
{

    public UserRepository(String url, String username, String password)
    {
        super(url, username, password);
    }

    @Override
    protected String getFindAllSQL()
    {
        return "SELECT * FROM users";
    }

    @Override
    protected String getFindOneSQL()
    {
        return "SELECT * FROM users WHERE id = ?";
    }

    @Override
    protected String getSaveSQL() {
        return "";
    }

    @Override
    protected String getDeleteSQL() {
        return "";
    }

    @Override
    protected String getUpdateSQL() {
        return "";
    }

    @Override
    protected String getSizeSQL() {
        return "SELECT COUNT(*) AS total FROM users";
    }

    @Override
    protected User extractEntity(ResultSet rs) throws SQLException
    {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        Double budget = rs.getDouble("budget");
        String password = rs.getString("password");
        return new User(id, name, budget, password);
    }

    @Override
    protected void setSaveParameters(PreparedStatement statement, User entity) throws SQLException
    {
    }

    @Override
    protected void setFindOneDeleteParameters(PreparedStatement statement, Integer integer) throws SQLException
    {
        statement.setInt(1, integer);
    }

    @Override
    protected void setUpdateParameters(PreparedStatement statement, User entity) throws SQLException {

    }
}
