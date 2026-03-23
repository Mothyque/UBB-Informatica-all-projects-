package org.example.repository;

import org.example.domain.Professor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfessorRepository extends DBRepository<Integer, Professor>
{

    public ProfessorRepository(String url)
    {
        super(url);
    }

    @Override
    protected String getFindAllSQL() {
        return "SELECT * FROM profesori";
    }

    @Override
    protected String getFindOneSQL()
    {
        return "SELECT * FROM profesori WHERE id = ?";
    }

    @Override
    protected String getSaveSQL()
    {
        return "INSERT INTO profesori (nume, varsta) VALUES (?, ?)";
    }

    @Override
    protected String getDeleteSQL()
    {
        return "DELETE FROM profesori WHERE id = ?";
    }

    @Override
    protected String getUpdateSQL()
    {
        return "UPDATE profesori SET nume = ?, varsta = ? WHERE id = ?";
    }

    @Override
    protected String getSizeSQL()
    {
        return "SELECT COUNT(*) AS total FROM profesori";
    }

    @Override
    protected Professor extractEntity(ResultSet rs) throws SQLException
    {
        String name = rs.getString("nume");
        Integer age = rs.getInt("varsta");
        Professor professor = new Professor(name, age);
        professor.setId(rs.getInt("id"));
        return professor;
    }

    @Override
    protected void setSaveParameters(PreparedStatement statement, Professor entity) throws SQLException {

    }

    @Override
    protected void setFindOneDeleteParameters(PreparedStatement statement, Integer integer) throws SQLException {

    }

    @Override
    protected void setUpdateParameters(PreparedStatement statement, Professor entity) throws SQLException {

    }
}
