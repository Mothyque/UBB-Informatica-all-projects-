package org.example.repository;

import org.example.domain.Driver;
import org.example.domain.Order;
import org.example.domain.Status;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository {
    private List<Order> orders;

    private String url;
    private String username;
    private String password;

    public OrderRepository(List<Order> orders, String url, String username, String password) {
        this.orders = orders;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Order findOrder(int id) {
        String sql = "SELECT * FROM orders WHERE id = ?";
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try(ResultSet rs = statement.executeQuery())
            {
                if(rs.next())
                {
                    return extractOrder(rs);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<Order> findAll()
    {
        List<Order> outputOrders = new ArrayList<>();
        String sql = getFindAllSQL();
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql); ResultSet rs = statement.executeQuery())
        {
            while(rs.next())
            {
                Order order = extractOrder(rs);
                outputOrders.add(order);
            }
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        return outputOrders;
    }

    public List<Order> findAllUnfinished(int driverId)
    {
        List<Order> outputOrders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE status = 'IN_PROGRESS' AND driver_id = ?";
        try(Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.setInt(1, driverId);
            try(ResultSet rs = statement.executeQuery())
            {
                while (rs.next())
                {
                    Order order = extractOrder(rs);
                    outputOrders.add(order);
                }
            }
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        return outputOrders;
    }

    public void update(Order order)
    {
        String sql = "UPDATE orders SET end_date = ?, status = ? WHERE id = ?";
        try(Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.setObject(1, order.getEndDate());
            statement.setString(2, String.valueOf(order.getStatus()));
            statement.setInt(3, order.getId());

            statement.executeUpdate();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    public void add(Order order)
    {
        String sql = "INSERT INTO orders (driver_id, status, start_date, end_date, pickup_address, destination_address, client_name) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try(Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.setInt(1, order.getDriverId());
            statement.setObject(2, String.valueOf(order.getStatus()));
            statement.setObject(3, order.getStartDate());
            statement.setObject(4, order.getEndDate());
            statement.setString(5, order.getPickupAddress());
            statement.setString(6, order.getDestinationAddress());
            statement.setString(7, order.getClientName());
            statement.executeUpdate();
        }
        catch(SQLException ex)
            ex.printStackTrace();
        }

    }

    private String getFindAllSQL()
    {
        return "SELECT * FROM orders";
    }

    private Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(url, username, password);
    }

    private Order extractOrder(ResultSet rs) throws SQLException
    {
        int id = rs.getInt("id");
        int driverId = rs.getInt("driver_id");
        Status status = Status.valueOf(rs.getString("status"));
        LocalDateTime startDate = rs.getObject("start_date", LocalDateTime.class);
        LocalDateTime endDate = rs.getObject("end_date", LocalDateTime.class);
        String pickupAddress = rs.getString("pickup_address");
        String destinationAddress = rs.getString("destination_address");
        String clientName = rs.getString("client_name");
        return new Order(id, driverId, status, startDate, endDate, pickupAddress, destinationAddress, clientName);

    }
}
