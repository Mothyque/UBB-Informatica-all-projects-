package org.example.repository;

import org.example.domain.Class;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClassRepository extends DBRepository<Integer, Class>
{
    public ClassRepository(String url)
    {
        super(url);
    }

    @Override
    protected String getFindAllSQL()
    {
        return "SELECT * FROM materii";
    }

    @Override
    protected String getFindOneSQL()
    {
        return "SELECT * FROM materii WHERE id = ?";
    }

    @Override
    protected String getSaveSQL()
    {
        return "INSERT INTO materii (nume, credite, id_profesor) VALUES (?, ?, ?)";
    }

    @Override
    protected String getDeleteSQL()
    {
        return "DELETE FROM materii WHERE id = ?";
    }

    @Override
    protected String getUpdateSQL()
    {
        return "UPDATE materii SET nume = ?, credite = ?, id_profesor = ? WHERE id = ?";
    }

    @Override
    protected String getSizeSQL()
    {
        return "SELECT COUNT(*) AS total FROM materii";
    }

    @Override
    protected Class extractEntity(ResultSet rs) throws SQLException
    {
        Class cls = new Class();
        cls.setId(rs.getInt("id"));
        cls.setName(rs.getString("nume"));
        cls.setCredits(rs.getInt("credite"));
        cls.setProfessorId(rs.getInt("id_profesor"));
        return cls;
    }

    @Override
    protected void setSaveParameters(PreparedStatement statement, Class entity) throws SQLException
    {
        statement.setString(1, entity.getName());
        statement.setInt(2, entity.getCredits());
        statement.setInt(3, entity.getProfessorId());
    }

    @Override
    protected void setFindOneDeleteParameters(PreparedStatement statement, Integer integer) throws SQLException
    {
        statement.setInt(1, integer);
    }

    @Override
    protected void setUpdateParameters(PreparedStatement statement, Class entity) throws SQLException
    {
        statement.setString(1, entity.getName());
        statement.setInt(2, entity.getCredits());
        statement.setInt(3, entity.getProfessorId());
        statement.setInt(4, entity.getId());
    }
}
