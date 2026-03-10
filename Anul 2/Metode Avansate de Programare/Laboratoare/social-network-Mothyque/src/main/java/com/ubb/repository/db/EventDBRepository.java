package com.ubb.repository.db;

import com.ubb.domain.event.Event;
import com.ubb.domain.event.RaceEvent;
import com.ubb.repository.paging.DBPagingRepository;
import com.ubb.repository.paging.PagingRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class EventDBRepository extends DBPagingRepository<Integer, Event> implements PagingRepository<Integer, Event>
{

    public EventDBRepository(String url, String username, String password)
    {
        super(url, username, password);
    }

    @Override
    protected String getFindAllSQL()
    {
        return "SELECT * FROM events";
    }

    @Override
    protected String getFindOneSQL()
    {
        return "SELECT * FROM events WHERE id = ?";
    }

    @Override
    protected String getSaveSQL()
    {
        return "INSERT INTO events (id, type, date, winner_id) VALUES (?, ?, ?, ?)";
    }

    @Override
    protected String getDeleteSQL()
    {
        return "DELETE FROM events WHERE id = ?";
    }

    @Override
    protected String getSizeSQL()
    {
        return "SELECT COUNT(*) AS total FROM events";
    }

    @Override
    protected String getUpdateSQL()
    {
        return "UPDATE events SET type = ?, date = ?, winner_id = ? WHERE id = ?";
    }

    @Override
    protected void setUpdateParameters(PreparedStatement statement, Event entity) throws SQLException
    {
        if (entity instanceof RaceEvent)
        {
            statement.setString(1, "RACE");
            statement.setTimestamp(2, Timestamp.valueOf(entity.getDate()));
            int winnerId = ((RaceEvent) entity).getWinnerId();
            if (winnerId > 0) {
                statement.setInt(3, winnerId);
            } else {
                statement.setNull(3, java.sql.Types.INTEGER);
            }
        }
        statement.setInt(4, entity.getId());
    }

    @Override
    protected Event extractEntity(ResultSet rs) throws SQLException
    {
        Integer id = rs.getInt("id");
        String type = rs.getString("type");
        Timestamp timestamp = rs.getTimestamp("date");
        LocalDateTime date = timestamp.toLocalDateTime();

        int winnerId = rs.getInt("winner_id");

        if(type.equalsIgnoreCase("RACE"))
        {
            RaceEvent event = new RaceEvent(new ArrayList<>());
            event.setId(id);
            event.setDate(date);
            event.setWinnerId(winnerId);
            return event;
        }
        else
        {
            return null;
        }
    }
    @Override
    protected void setSaveParameters(PreparedStatement statement, Event entity) throws SQLException
    {
        statement.setInt(1, entity.getId());
        if (entity instanceof RaceEvent) {
            statement.setString(2, "RACE");
            statement.setTimestamp(3, Timestamp.valueOf(entity.getDate()));

            int winnerId = ((RaceEvent) entity).getWinnerId();
            if(winnerId > 0)
            {
                statement.setInt(4,  winnerId);
            }
            else
            {
                statement.setNull(4, java.sql.Types.INTEGER);
            }
        }
    }

    @Override
    protected void setFindOneDeleteParameters(PreparedStatement statement, Integer id) throws SQLException
    {
        statement.setInt(1, id);
    }

    @Override
    protected String getFindAllPagedSQL() {
        return "SELECT * FROM events LIMIT ? OFFSET ?";
    }
}
