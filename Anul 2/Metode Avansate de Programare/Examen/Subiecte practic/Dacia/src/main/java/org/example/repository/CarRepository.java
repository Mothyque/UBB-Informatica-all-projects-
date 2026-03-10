package org.example.repository;

import org.example.domain.Car;
import org.example.domain.Status;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CarRepository extends DBRepository<Integer, Car>
{
    public CarRepository(String url, String username, String password)
    {
        super(url, username, password);
    }

    @Override
    protected String getFindAllSQL() {
        return "SELECT * FROM cars";
    }

    @Override
    protected String getFindOneSQL() {
        return "SELECT * FROM cars WHERE id = ?";
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
        return "UPDATE cars SET name = ?, description = ?, price = ?, status = ?, comments = ?, options = ?, rejection_reason = ? WHERE id = ?";
    }

    @Override
    protected String getSizeSQL() {
        return "";
    }

    @Override
    protected Car extractEntity(ResultSet rs) throws SQLException
    {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        Status status = Status.valueOf(rs.getString("status"));
        Double price = rs.getDouble("price");
        String comments = rs.getString("comments");
        String options = rs.getString("options");
        String rejectedReason = rs.getString("rejection_reason");
        return new Car(id, name, description, price, status, comments, options, rejectedReason);
    }

    @Override
    protected void setSaveParameters(PreparedStatement statement, Car entity) throws SQLException {

    }

    @Override
    protected void setFindOneDeleteParameters(PreparedStatement statement, Integer integer) throws SQLException {

    }

    @Override
    protected void setUpdateParameters(PreparedStatement statement, Car entity) throws SQLException
    {
        statement.setString(1, entity.getName());
        statement.setString(2, entity.getDescription());
        statement.setDouble(3, entity.getPrice());
        statement.setString(4, String.valueOf(entity.getStatus()));
        statement.setString(5, entity.getComments());
        statement.setString(6, entity.getOptions());
        statement.setString(7, entity.getRejectionReason());
        statement.setInt(8, entity.getId());
    }

}
