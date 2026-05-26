package org.example.repository.db;

import org.example.domain.Client;
import org.example.repository.ClientRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientDBRepository extends DBRepository<Integer, Client> implements ClientRepository
{

    public ClientDBRepository(String url)
    {
        super(url);
    }

    @Override
    protected String getFindAllSQL() {
        return "SELECT * FROM clients";
    }

    @Override
    protected String getFindOneSQL() {
        return "SELECT * FROM clients WHERE id = ?";
    }

    @Override
    protected String getSaveSQL() {
        return "INSERT INTO clients (name, address) VALUES (?, ?)";
    }

    @Override
    protected String getDeleteSQL() {
        return "DELETE FROM clients WHERE id = ?";
    }

    @Override
    protected String getUpdateSQL() {
        return "UPDATE clients SET name = ?, address = ? WHERE id = ?";
    }

    @Override
    protected String getSizeSQL() {
        return "SELECT COUNT(*) AS count FROM clients";
    }

    @Override
    protected Client extractEntity(ResultSet rs) throws SQLException
    {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String address = rs.getString("address");
        Client client = new Client(name, address);
        client.setId(id);
        return client;
    }

    @Override
    protected void setSaveParameters(PreparedStatement statement, Client entity) throws SQLException
    {
        statement.setString(1, entity.getName());
        statement.setString(2, entity.getAddress());
    }

    @Override
    protected void setFindOneDeleteParameters(PreparedStatement statement, Integer integer) throws SQLException
    {
        statement.setInt(1, integer);
    }

    @Override
    protected void setUpdateParameters(PreparedStatement statement, Client entity) throws SQLException
    {
        statement.setString(1, entity.getName());
        statement.setString(2, entity.getAddress());
        statement.setInt(3, entity.getId());
    }

    @Override
    public List<Client> findClientsByNameAndAddress(String name, String address)
    {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM clients WHERE name = ? AND address = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql))
        {
            statement.setString(1, name);
            statement.setString(2, address);
            try (ResultSet rs = statement.executeQuery())
            {
                while (rs.next())
                {
                    clients.add(extractEntity(rs));
                }
            }
        }
        catch (SQLException e)
        {
            System.err.println("Error finding clients by name and address: " + e.getMessage());
        }
        return clients;
    }

    @Override
    public Client findClientByNameAndAddress(String name, String address)
    {
        String sql = "SELECT * FROM clients WHERE name = ? AND address = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql))
        {
            statement.setString(1, name);
            statement.setString(2, address);
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
            System.err.println("Error finding client by name and address: " + e.getMessage());
        }
        return null;
    }
}

