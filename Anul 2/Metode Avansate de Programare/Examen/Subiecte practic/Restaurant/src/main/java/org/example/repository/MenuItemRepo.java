package org.example.repository;

import org.example.domain.MenuItem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MenuItemRepo extends DBRepository<Integer, MenuItem>
{

    public MenuItemRepo(String url, String username, String password)
    {
        super(url, username, password);
    }

    @Override
    protected String getFindAllSQL()
    {
        return "SELECT * FROM menu_items";
    }

    @Override
    protected String getFindOneSQL()
    {
        return "SELECT * FROM menu_items WHERE id = ?";
    }

    @Override
    protected String getSaveSQL()
    {
        return "INSERT INTO menu_items VALUES(?, ?, ?, ?)";
    }

    @Override
    protected String getDeleteSQL()
    {
        return "DELETE FROM menu_items WHERE id = ?";
    }

    @Override
    protected String getUpdateSQL()
    {
        return "UPDATE menu_items SET category = ?, item = ?, price = ? currency = ? WHERE id = ?";
    }

    @Override
    protected String getSizeSQL()
    {
        return "SELECT COUNT(*) AS total FROM menu_items";
    }

    @Override
    protected MenuItem extractEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String category = rs.getString("category");
        String item = rs.getString("item");
        float price = rs.getFloat("price");
        String currency = rs.getString("currency");
        return new MenuItem(id, category, item, price, currency);
    }

    @Override
    protected void setSaveParameters(PreparedStatement statement, MenuItem entity) throws SQLException
    {
        statement.setString(1, entity.getCategory());
        statement.setString(2, entity.getItem());
        statement.setFloat(3, entity.getPrice());
        statement.setString(4, entity.getCurrency());
    }

    @Override
    protected void setFindOneDeleteParameters(PreparedStatement statement, Integer integer) throws SQLException
    {
        statement.setInt(1, integer);
    }

    @Override
    protected void setUpdateParameters(PreparedStatement statement, MenuItem entity) throws SQLException
    {
        statement.setString(1, entity.getCategory());
        statement.setString(2, entity.getItem());
        statement.setFloat(3, entity.getPrice());
        statement.setString(4, entity.getCurrency());
        statement.setInt(5, entity.getId());
    }
}
