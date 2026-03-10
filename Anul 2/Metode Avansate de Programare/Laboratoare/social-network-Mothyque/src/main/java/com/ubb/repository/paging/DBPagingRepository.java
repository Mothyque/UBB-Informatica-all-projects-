package com.ubb.repository.paging;

import com.ubb.domain.Entity;
import com.ubb.repository.db.DBRepository;
import com.ubb.utils.paging.Page;
import com.ubb.utils.paging.Pageable;
import com.ubb.validators.PersistenceException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class DBPagingRepository<ID, E extends Entity<ID>> extends DBRepository<ID, E> implements PagingRepository<ID, E> {
    public DBPagingRepository(String url, String username, String password) {
        super(url, username, password);
    }

    @Override
    public Page<E> findAllPaged(Pageable pageable)
    {
        List<E> entities = new ArrayList<>();
        int totalCount = this.size();

        String sql = getFindAllPagedSQL();

        try(Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql))
        {
            int limit = pageable.getPageSize();
            int offset = pageable.getPageNumber() * pageable.getPageSize();
            statement.setInt(1, limit);
            statement.setInt(2, offset);

            try(ResultSet rs = statement.executeQuery())
            {
                while(rs.next())
                {
                    E entity = extractEntity(rs);
                    entities.add(entity);
                }
            }
        }
        catch(SQLException e)
        {
            throw new PersistenceException("Error fetching paged entities: " + e.getMessage());
        }
        return new Page<>(entities, totalCount);
    }

    protected abstract String getFindAllPagedSQL();
}
