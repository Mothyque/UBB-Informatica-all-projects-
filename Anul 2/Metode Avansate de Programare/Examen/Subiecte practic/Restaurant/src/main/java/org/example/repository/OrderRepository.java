package org.example.repository;

import org.example.domain.MenuItem;
import org.example.domain.Order;
import org.example.domain.OrderStatus;
import org.example.domain.Table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderRepository extends DBRepository<Integer, Order>
{
    public OrderRepository(String url, String username, String password)
    {
        super(url, username, password);
    }

    @Override
    public Iterable<Order> findAll()
    {
        Iterable<Order> orders = super.findAll();
        for (Order order : orders)
        {
            loadOrderItems(order);
        }
        return orders;
    }

    @Override
    public Optional<Order> save(Order entity)
    {
        String sql = "INSERT INTO orders (table_id, date, status) VALUES (?, ?, ?) RETURNING id";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.setInt(1, entity.getTable().getId());
            statement.setObject(2, entity.getDate());
            statement.setString(3, entity.getStatus().name());

            try (ResultSet rs = statement.executeQuery())
            {
                if (rs.next())
                {
                    int generatedId = rs.getInt("id");
                    entity.setId(generatedId);

                    if (entity.getMenuItems() != null && !entity.getMenuItems().isEmpty())
                    {
                        String sqlItems = "INSERT INTO order_items (order_id, item_id) VALUES (?, ?)";
                        try (PreparedStatement itemStmt = connection.prepareStatement(sqlItems))
                        {
                            for (MenuItem item : entity.getMenuItems())
                            {
                                itemStmt.setInt(1, generatedId);
                                itemStmt.setInt(2, item.getId());
                                itemStmt.addBatch();
                            }
                            itemStmt.executeBatch();
                        }
                    }
                    return Optional.of(entity);
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private void loadOrderItems(Order order)
    {
        String sql = "SELECT m.id, m.category, m.item, m.price, m.currency " +
                "FROM menu_items m " +
                "INNER JOIN order_items oi ON m.id = oi.item_id " +
                "WHERE oi.order_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.setInt(1, order.getId());
            try (ResultSet rs = statement.executeQuery())
            {
                List<MenuItem> items = new ArrayList<>();
                while (rs.next())
                {
                    MenuItem item = new MenuItem(
                            rs.getInt("id"),
                            rs.getString("category"),
                            rs.getString("item"),
                            rs.getFloat("price"),
                            rs.getString("currency")
                    );
                    items.add(item);
                }
                order.setMenuItems(items);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected String getFindAllSQL()
    {
        return "SELECT * FROM orders";
    }

    @Override
    protected String getFindOneSQL()
    {
        return "SELECT * FROM orders WHERE id=?";
    }

    @Override
    protected String getSaveSQL()
    {
        return "INSERT INTO orders (table_id, date, status) VALUES (?, ?, ?)";
    }

    @Override
    protected String getDeleteSQL()
    {
        return "DELETE FROM orders WHERE id=?";
    }

    @Override
    protected String getUpdateSQL()
    {
        return "UPDATE orders SET status=? WHERE id=?";
    }

    @Override
    protected String getSizeSQL()
    {
        return "SELECT COUNT(*) FROM orders";
    }

    @Override
    protected Order extractEntity(ResultSet rs) throws SQLException
    {
        int id = rs.getInt("id");
        int tableId = rs.getInt("table_id");
        LocalDateTime date = rs.getObject("date", LocalDateTime.class);
        String statusStr = rs.getString("status");
        Table table = new Table(tableId);
        Order order = new Order(table, date, OrderStatus.valueOf(statusStr));
        order.setId(id);

        return order;
    }

    @Override
    protected void setSaveParameters(PreparedStatement statement, Order entity) throws SQLException
    {
        statement.setInt(1, entity.getTable().getId());
        statement.setObject(2, entity.getDate());
        statement.setString(3, entity.getStatus().name());
    }

    @Override
    protected void setFindOneDeleteParameters(PreparedStatement statement, Integer id) throws SQLException
    {
        statement.setInt(1, id);
    }

    @Override
    protected void setUpdateParameters(PreparedStatement statement, Order entity) throws SQLException
    {
        statement.setString(1, entity.getStatus().name());
        statement.setInt(2, entity.getId());
    }
}