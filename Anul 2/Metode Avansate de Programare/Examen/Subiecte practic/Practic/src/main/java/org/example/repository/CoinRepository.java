package org.example.repository;

import org.example.domain.Coin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CoinRepository extends DBRepository<Integer, Coin>
{

    public CoinRepository(String url, String username, String password)
    {
        super(url, username, password);
    }

    @Override
    protected String getFindAllSQL()
    {
        return "SELECT * FROM coins";
    }

    @Override
    protected String getFindOneSQL() {
        return "SELECT * FROM coins WHERE id = ?";
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
        return "";
    }

    @Override
    protected Coin extractEntity(ResultSet rs) throws SQLException
    {
        int id = rs.getInt("id");
        String abbreviation = rs.getString("abbreviation");
        String name = rs.getString("name");
        Double price = rs.getDouble("price");
        return new Coin(id, abbreviation, name, price);
    }

    @Override
    protected void setSaveParameters(PreparedStatement statement, Coin entity) throws SQLException {

    }

    @Override
    protected void setFindOneDeleteParameters(PreparedStatement statement, Integer integer) throws SQLException
    {
        statement.setInt(1, integer);
    }

    @Override
    protected void setUpdateParameters(PreparedStatement statement, Coin entity) throws SQLException {

    }
}
