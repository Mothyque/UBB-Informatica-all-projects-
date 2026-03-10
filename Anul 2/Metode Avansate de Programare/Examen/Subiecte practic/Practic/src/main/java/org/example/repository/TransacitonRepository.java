package org.example.repository;

import org.example.domain.Transaction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class TransacitonRepository extends DBRepository<Integer, Transaction>
{

    public TransacitonRepository(String url, String username, String password)
    {
        super(url, username, password);
    }

    @Override
    protected String getFindAllSQL() {
        return "SELECT * FROM transactions ORDER BY timestamp DESC";
    }

    @Override
    protected String getFindOneSQL() {
        return "SELECT * FROM transactions WHERE id = ?";
    }

    @Override
    protected String getSaveSQL() {
        return "INSERT INTO transactions (user_id, abbreviate, type, price, timestamp) VALUES (?, ?, ?, ?, ?)";
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
        return "";
    }

    @Override
    protected Transaction extractEntity(ResultSet rs) throws SQLException
    {
        int id = rs.getInt("id");
        int userId = rs.getInt("id_user");
        String abbreviate = rs.getString("abbreviation");
        String type = rs.getString("type");
        Double price = rs.getDouble("price");
        LocalDateTime timestamp = rs.getObject("timestamp", LocalDateTime.class);
        return new Transaction(id, userId, abbreviate, type, price, timestamp);
    }

    @Override
    protected void setSaveParameters(PreparedStatement statement, Transaction entity) throws SQLException
    {
        statement.setInt(1, entity.getUserId());
        statement.setString(2, entity.getAbbreviate());
        statement.setString(3, entity.getType());
        statement.setDouble(4, entity.getPrice());
        statement.setObject(5, entity.getTimestamp());
    }

    @Override
    protected void setFindOneDeleteParameters(PreparedStatement statement, Integer integer) throws SQLException
    {
        statement.setInt(1, integer);
    }

    @Override
    protected void setUpdateParameters(PreparedStatement statement, Transaction entity) throws SQLException {

    }
}
