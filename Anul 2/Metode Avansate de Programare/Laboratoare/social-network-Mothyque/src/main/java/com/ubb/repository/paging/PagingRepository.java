package com.ubb.repository.paging;

import com.ubb.domain.Entity;
import com.ubb.repository.Repository;
import com.ubb.utils.paging.Page;
import com.ubb.utils.paging.Pageable;

public interface PagingRepository <ID, E extends Entity<ID>> extends Repository<ID, E>
{
    Page<E> findAllPaged(Pageable pageable);
}
