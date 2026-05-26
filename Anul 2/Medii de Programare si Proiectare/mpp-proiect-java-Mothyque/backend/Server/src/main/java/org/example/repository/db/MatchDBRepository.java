package org.example.repository.db;

import org.example.domain.Match;
import org.example.repository.MatchRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MatchDBRepository extends DBRepository<Integer, Match> implements MatchRepository
{
    public MatchDBRepository(String url) {
        super(url);
    }

    @Override
    protected String getFindAllSQL() {
        return "SELECT * FROM matches";
    }

    @Override
    protected String getFindOneSQL() {
        return "SELECT * FROM matches WHERE id = ?";
    }

    @Override
    protected String getSaveSQL()
    {
        return "INSERT INTO matches (teamA, teamB, matchType, ticketPrice, totalSeats, availableSeats) VALUES (?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String getDeleteSQL() {
        return "DELETE FROM matches WHERE id = ?";
    }

    @Override
    protected String getUpdateSQL() {
        return "UPDATE matches SET teamA = ?, teamB = ?, matchType = ?, ticketPrice = ?, totalSeats = ?, availableSeats = ? WHERE id = ?";
    }

    @Override
    protected String getSizeSQL() {
        return "SELECT COUNT(*) FROM matches";
    }

    @Override
    protected Match extractEntity(ResultSet rs) throws SQLException
    {
        String teamA = rs.getString("teamA");
        String teamB = rs.getString("teamB");
        String matchType = rs.getString("matchType");
        double ticketPrice = rs.getDouble("ticketPrice");
        int totalSeats = rs.getInt("totalSeats");
        int availableSeats = rs.getInt("availableSeats");
        Match match = new Match(teamA, teamB, matchType, ticketPrice, totalSeats, availableSeats);
        match.setId(rs.getInt("id"));
        return match;
    }

    @Override
    protected void setSaveParameters(PreparedStatement statement, Match entity) throws SQLException
    {
        statement.setString(1, entity.getTeamA());
        statement.setString(2, entity.getTeamB());
        statement.setString(3, entity.getMatchType());
        statement.setDouble(4, entity.getTicketPrice());
        statement.setInt(5, entity.getTotalSeats());
        statement.setInt(6, entity.getAvailableSeats());
    }

    @Override
    protected void setFindOneDeleteParameters(PreparedStatement statement, Integer integer) throws SQLException
    {
        statement.setInt(1, integer);
    }

    @Override
    protected void setUpdateParameters(PreparedStatement statement, Match entity) throws SQLException
    {
        statement.setString(1, entity.getTeamA());
        statement.setString(2, entity.getTeamB());
        statement.setString(3, entity.getMatchType());
        statement.setDouble(4, entity.getTicketPrice());
        statement.setInt(5, entity.getTotalSeats());
        statement.setInt(6, entity.getAvailableSeats());
        statement.setInt(7, entity.getId());
    }
}

