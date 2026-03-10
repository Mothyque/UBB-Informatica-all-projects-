package org.example.repository;

import org.example.domain.Table;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TableRepository extends DBRepository<Integer, Table>
{
    public TableRepository(String url, String username, String password) {
        super(url, username, password);
    }

    @Override
    protected String getFindAllSQL() {
        return "";
    }

    @Override
    protected String getFindOneSQL() {
        return "";
    }

    @Override
    protected String getSaveSQL() {
        return "";
    }

    @Override
    protected String getDeleteSQL() {
        return "";
    }

    @Override
    protected String getUpdateSQL() {
        return "";
    }

    @Override
    protected String getSizeSQL() {
        return "SELECT COUNT(*) AS total FROM tables";
    }

    @Override
    protected Table extractEntity(ResultSet rs) throws SQLException {
        return null;
    }

    @Override
    protected void setSaveParameters(PreparedStatement statement, Table entity) throws SQLException {

    }

    @Override
    protected void setFindOneDeleteParameters(PreparedStatement statement, Integer integer) throws SQLException {

    }

    @Override
    protected void setUpdateParameters(PreparedStatement statement, Table entity) throws SQLException {

    }
}
