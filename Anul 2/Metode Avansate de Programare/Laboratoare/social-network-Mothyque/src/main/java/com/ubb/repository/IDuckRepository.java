package com.ubb.repository;

import com.ubb.domain.duck.Duck;
import com.ubb.domain.duck.DuckType;
import com.ubb.repository.paging.PagingRepository;
import com.ubb.utils.paging.Page;
import com.ubb.utils.paging.Pageable;

public interface IDuckRepository extends Repository<Integer, Duck>, PagingRepository<Integer, Duck>
{
    Page<Duck> findAllByType(Pageable pageable, DuckType type);
}
