package org.example.repository.db;

import org.example.domain.Client;
import org.example.domain.ClientTicketDTO;
import org.example.domain.Match;
import org.example.domain.Ticket;
import org.example.repository.TicketRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketDBRepository extends DBRepository<Integer, Ticket> implements TicketRepository
{
    public TicketDBRepository(String url) {
        super(url);
    }

    @Override
    protected String getFindAllSQL() {
        return "SELECT * FROM tickets";
    }

    @Override
    protected String getFindOneSQL() {
        return "SELECT * FROM tickets WHERE id = ?";
    }

    @Override
    protected String getSaveSQL() {
        return "INSERT INTO tickets (clientId, matchId , seatLocation) VALUES (?, ?, ?)";
    }

    @Override
    protected String getDeleteSQL() {
        return "DELETE FROM tickets WHERE id = ?";
    }

    @Override
    protected String getUpdateSQL() {
        return "UPDATE tickets SET clientId = ?, matchId = ?, seatLocation = ? WHERE id = ?";
    }

    @Override
    protected String getSizeSQL() {
        return "SELECT COUNT(*) FROM tickets";
    }

    @Override
    protected Ticket extractEntity(ResultSet rs) throws SQLException
    {
        int clientId = rs.getInt("clientId");
        int matchId = rs.getInt("matchId");
        String seatLocation = rs.getString("seatLocation");
        Ticket ticket = new Ticket(clientId, matchId, seatLocation);
        ticket.setId(rs.getInt("id"));
        return ticket;
    }

    @Override
    protected void setSaveParameters(PreparedStatement statement, Ticket entity) throws SQLException
    {
        statement.setInt(1, entity.getClientId());
        statement.setInt(2, entity.getMatchId());
        statement.setString(3, entity.getSeatLocation());
    }

    @Override
    protected void setFindOneDeleteParameters(PreparedStatement statement, Integer integer) throws SQLException
    {
        statement.setInt(1, integer);
    }

    @Override
    protected void setUpdateParameters(PreparedStatement statement, Ticket entity) throws SQLException
    {
        statement.setInt(1, entity.getClientId());
        statement.setInt(2, entity.getMatchId());
        statement.setString(3, entity.getSeatLocation());
        statement.setInt(4, entity.getId());
    }

    @Override
    public int getNumberOfTicketsAtMatch(int clientId, int matchId)
    {
        String sql = "SELECT (COUNT(*)) AS ticketCount FROM tickets WHERE clientId = ? AND matchId = ?";
        int count = 0;
        try (PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.setInt(1, clientId);
            statement.setInt(2, matchId);
            try (ResultSet rs = statement.executeQuery())
            {
                if (rs.next())
                {
                    count = rs.getInt(1);
                }
            }
        }
        catch (SQLException e)
        {
            System.err.println("Error executing getNumberOfTicketsAtMatch: " + e.getMessage());
            e.printStackTrace();
        }
        return count;
    }

    public int getTicketId(int clientId, int matchId)
    {
        String sql = "SELECT id FROM tickets WHERE clientId = ? AND matchId = ? LIMIT 1";
        try (PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.setInt(1, clientId);
            statement.setInt(2, matchId);
            try (ResultSet rs = statement.executeQuery())
            {
                if (rs.next())
                {
                    return rs.getInt("id");
                }
            }
        }
        catch (SQLException e)
        {
            System.err.println("Error executing getTicketId: " + e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public List<ClientTicketDTO> getClientTicketsByCriteria(String name, String address)
    {
        List<ClientTicketDTO> results = new ArrayList<>();
        String sql = "SELECT c.id as cid, c.name, c.address," +
                    "m.id AS mid, m.teamA, m.teamB, m.matchType, m.ticketPrice, m.totalSeats, m.availableSeats, " +
                "COUNT (t.id) as ticketCount " +
                "FROM clients c " +
                "JOIN tickets t ON c.id = t.clientId " +
                "JOIN matches m ON t.matchId = m.id " +
                "WHERE c.name LIKE ? AND c.address LIKE ? " +
                "GROUP BY c.id, m.id " +
                "HAVING ticketCount > 0";

        try (PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.setString(1, "%" + name + "%");
            statement.setString(2, "%" + address + "%");
            try (ResultSet rs = statement.executeQuery())
            {
                while (rs.next())
                {
                    Client client = new Client(rs.getString("name"), rs.getString("address"));
                    client.setId(rs.getInt("cid"));

                    Match match = new Match(rs.getString("teamA"), rs.getString("teamB"), rs.getString("matchType"), rs.getDouble("ticketPrice"), rs.getInt("totalSeats"), rs.getInt("availableSeats"));
                    match.setId(rs.getInt("mid"));

                    int count = rs.getInt("ticketCount");
                    results.add(new ClientTicketDTO(client, match, count));
                }
            }
        }
        catch (SQLException e)
        {
            System.err.println("Error executing getClientTicketsByCriteria: " + e.getMessage());
            e.printStackTrace();
        }
        return results;
    }
}
