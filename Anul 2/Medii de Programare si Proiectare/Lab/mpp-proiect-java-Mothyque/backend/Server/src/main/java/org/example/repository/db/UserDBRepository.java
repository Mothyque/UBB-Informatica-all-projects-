package org.example.repository.db;

import org.example.domain.User;
import org.example.repository.UserRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDBRepository extends DBRepository<Integer, User> implements UserRepository
{
    public UserDBRepository(String url)
    {
        super(url);
    }

    @Override
    protected String getFindAllSQL() {
        return "SELECT * FROM users";
    }

    @Override
    protected String getFindOneSQL() {
        return "SELECT * FROM users WHERE id = ?";
    }

    @Override
    protected String getSaveSQL() {
        return "INSERT INTO users (username, password) VALUES (?, ?)";
    }

    @Override
    protected String getDeleteSQL() {
        return "DELETE FROM users WHERE id = ?";
    }

    @Override
    protected String getUpdateSQL() {
        return "UPDATE users SET username = ?, password = ? WHERE id = ?";
    }

    @Override
    protected String getSizeSQL() {
        return "SELECT COUNT(*) FROM users";
    }

    @Override
    protected User extractEntity(ResultSet rs) throws SQLException
    {
        int id = rs.getInt("id");
        String username = rs.getString("username");
        String password = rs.getString("password");
        User user = new User(username, password);
        user.setId(id);
        return user;
    }

    @Override
    protected void setSaveParameters(PreparedStatement statement, User entity) throws SQLException
    {
        statement.setString(1, entity.getUsername());
        statement.setString(2, entity.getPassword());
    }

    @Override
    protected void setFindOneDeleteParameters(PreparedStatement statement, Integer integer) throws SQLException
    {
        statement.setInt(1, integer);
    }

    @Override
    protected void setUpdateParameters(PreparedStatement statement, User entity) throws SQLException
    {
        statement.setString(1, entity.getUsername());
        statement.setString(2, entity.getPassword());
        statement.setInt(3, entity.getId());
    }

    @Override
    public User findByUsername(String username)
    {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql))
        {
            statement.setString(1, username);
            try (ResultSet rs = statement.executeQuery())
            {
                if (rs.next())
                {
                    return extractEntity(rs);
                }
            }
        }
        catch (SQLException e)
        {
            System.err.println("Error finding user by username: " + e.getMessage());
        }
        return null;
    }
}
