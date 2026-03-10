package org.example.repository;

import org.example.domain.Driver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DriverRepository
{
    private List<Driver> drivers;

    private String url;
    private String username;
    private String password;

    public DriverRepository(List<Driver> drivers, String url, String username, String password)
    {
        this.drivers = drivers;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public List<Driver> findAll()
    {
        List<Driver> outputDrivers = new ArrayList<>();
        String sql = getFindAllSQL();
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql); ResultSet rs = statement.executeQuery())
        {
            while(rs.next())
            {
                Driver driver = extractDriver(rs);
                outputDrivers.add(driver);
            }
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        return outputDrivers;
    }

    private String getFindAllSQL()
    {
        return "SELECT * FROM drivers";
    }

    private Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(url, username, password);
    }

    private Driver extractDriver(ResultSet rs) throws SQLException
    {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        return new Driver(id, name);
    }
}
