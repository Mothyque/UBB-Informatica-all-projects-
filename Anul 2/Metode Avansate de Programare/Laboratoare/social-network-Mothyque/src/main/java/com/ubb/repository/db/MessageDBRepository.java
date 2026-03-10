package com.ubb.repository.db;

import com.ubb.domain.Message;
import com.ubb.domain.ReplyMessage;
import com.ubb.domain.User;
import com.ubb.domain.duck.Duck;
import com.ubb.domain.Person;
import com.ubb.repository.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
public class MessageDBRepository implements Repository<Integer, Message>
{
    private final String url;
    private final String username;
    private final String password;

    private final Repository<Integer, Duck> duckRepository;
    private final Repository<Integer, Person> personRepository;

    public MessageDBRepository(String url, String username, String password, Repository<Integer, Duck> duckRepository, Repository<Integer, Person> personRepository)
    {
        this.url = url;
        this.username = username;
        this.password = password;
        this.duckRepository = duckRepository;
        this.personRepository = personRepository;
    }

    @Override
    public Optional<Message> findOne(Integer id)
    {
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM messages WHERE id = ?"))
        {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if(rs.next())
            {
                Message message = extractMessage(rs, connection);
                return Optional.of(message);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Message> findAll()
    {
        List<Message> messages = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM messages ORDER BY date");
            ResultSet rs = statement.executeQuery())
        {
            while(rs.next())
            {
                Message message = extractMessage(rs, connection);
                messages.add(message);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return messages;
    }

    @Override
    public Optional<Message> save(Message entity)
    {
        String insertMessageSQL = "INSERT INTO messages (from_user_id, from_user_type, message_text, date, reply_to_message_id) VALUES (?, ?, ?, ?, ?)";
        try(Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement ps = connection.prepareStatement(insertMessageSQL, Statement.RETURN_GENERATED_KEYS))
        {
            ps.setInt(1, entity.getFrom().getId());
            ps.setString(2, getUserType(entity.getFrom()));
            ps.setString(3, entity.getMessage());
            ps.setTimestamp(4, Timestamp.valueOf(entity.getDate()));
            if(entity instanceof ReplyMessage)
            {
                ps.setInt(5, ((ReplyMessage) entity).getRepliedMessage().getId());
            }
            else
            {
                ps.setNull(5, Types.INTEGER);
            }

            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if(generatedKeys.next())
            {
                int messageId = generatedKeys.getInt(1);
                entity.setId(messageId);
                saveRecipients(connection, messageId, entity.getTo());
            }
            return Optional.empty();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return Optional.of(entity);
        }
    }

    @Override
    public Optional<Message> delete(Integer id)
    {
        String sql = "DELETE FROM messages WHERE id = ?";
        try(Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Message> update(Message entity)
    {
        return Optional.empty();
    }

    @Override
    public int size()
    {
        String sql = "SELECT COUNT(*) AS total FROM messages";
        try(Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery())
        {
            if(rs.next())
            {
                return rs.getInt("total");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    private Message extractMessage(ResultSet rs, Connection connection) throws SQLException
    {
        int id = rs.getInt("id");
        int fromId = rs.getInt("from_user_id");
        String fromType = rs.getString("from_user_type");
        String text = rs.getString("message_text");
        LocalDateTime date = rs.getTimestamp("date").toLocalDateTime();

        User fromUser = findUserByIdAndType(fromId, fromType);

        List<User> recipients = getRecipientsForMessage(connection, id);

        int replyId = rs.getInt("reply_to_message_id");
        if(!rs.wasNull() && replyId != 0)
        {
            Optional<Message> messageOpt = findOne(replyId);
            if(messageOpt.isPresent())
            {
                return new ReplyMessage(id, fromUser, recipients, text, date, messageOpt.get());
            }
        }
        return new Message(id, fromUser, recipients, text, date);
    }

    private List<User> getRecipientsForMessage(Connection conn, int messageId) throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT recipient_user_id, recipient_user_type FROM message_recipients WHERE message_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setInt(1, messageId);
            try (ResultSet rs = ps.executeQuery())
            {
                while (rs.next())
                {
                    int recipientId = rs.getInt("recipient_user_id");
                    String recipientType = rs.getString("recipient_user_type");
                    User user = findUserByIdAndType(recipientId, recipientType);
                    if (user != null)
                    {
                        users.add(user);
                    }
                }
            }
        }
        return users;
    }

    private void saveRecipients(Connection conn, int messageId, List<User> recipients) throws SQLException
    {
        String sql = "INSERT INTO message_recipients (message_id, recipient_user_id, recipient_user_type) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql))
        {
            for (User recipient : recipients)
            {
                ps.setInt(1, messageId);
                ps.setInt(2, recipient.getId());
                ps.setString(3, getUserType(recipient));
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private User findUserByIdAndType(int id, String type)
    {
        if ("DUCK".equalsIgnoreCase(type))
        {
            return duckRepository.findOne(id).orElse(null);
        }
        else if ("PERSON".equalsIgnoreCase(type))
        {
            return personRepository.findOne(id).orElse(null);
        }
        return null;
    }

    private String getUserType(User user)
    {
        if (user instanceof Duck)
        {
            return "DUCK";
        }
        else if (user instanceof Person)
        {
            return "PERSON";
        }
        return "UNKNOWN";
    }
}
