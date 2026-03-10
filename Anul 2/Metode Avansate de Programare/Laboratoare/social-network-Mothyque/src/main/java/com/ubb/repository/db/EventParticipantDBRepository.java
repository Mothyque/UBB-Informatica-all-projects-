package com.ubb.repository.db;

import com.ubb.domain.event.EventParticipant;
import com.ubb.repository.paging.DBPagingRepository;
import com.ubb.repository.paging.PagingRepository;
import com.ubb.utils.Tuple;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EventParticipantDBRepository extends DBPagingRepository<Tuple<Integer, Integer>, EventParticipant> implements PagingRepository<Tuple<Integer, Integer>, EventParticipant>
{
    public EventParticipantDBRepository(String url, String username, String password)
    {
        super(url, username, password);
    }

    @Override
    protected String getFindAllPagedSQL()
    {
        return "SELECT * FROM event_participants LIMIT ? OFFSET ?";
    }

    @Override
    protected String getFindAllSQL()
    {
        return "SELECT * FROM event_participants";
    }

    @Override
    protected String getFindOneSQL()
    {
        return "SELECT * FROM event_participants WHERE event_id=? and duck_id=?";
    }

    @Override
    protected String getSaveSQL()
    {
        return "INSERT INTO event_participants (event_id, duck_id) VALUES (?, ?)";
    }

    @Override
    protected String getDeleteSQL()
    {
        return "DELETE FROM event_participants WHERE event_id=? and duck_id=?";
    }

    @Override
    protected String getUpdateSQL()
    {
        return "";
    }

    @Override
    protected String getSizeSQL()
    {
        return "SELECT COUNT(*) AS total FROM event_participants";
    }

    @Override
    protected EventParticipant extractEntity(ResultSet rs) throws SQLException
    {
        Integer eventId = rs.getInt("event_id");
        Integer participantId = rs.getInt("duck_id");
        return new EventParticipant(eventId, participantId);
    }

    @Override
    protected void setSaveParameters(PreparedStatement statement, EventParticipant entity) throws SQLException
    {
        statement.setInt(1, entity.getEventId());
        statement.setInt(2, entity.getUserId());
    }

    @Override
    protected void setFindOneDeleteParameters(PreparedStatement statement, Tuple<Integer, Integer> integerIntegerTuple) throws SQLException
    {
        statement.setInt(1, integerIntegerTuple.getLeft());
        statement.setInt(2, integerIntegerTuple.getRight());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement statement, EventParticipant entity) throws SQLException
    {

    }
}

