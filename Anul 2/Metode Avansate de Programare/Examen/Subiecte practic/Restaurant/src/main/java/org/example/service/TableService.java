package org.example.service;

import org.example.repository.TableRepository;

public class TableService
{
    private final TableRepository tableRepository;


    public TableService(TableRepository tableRepository)
    {
        this.tableRepository = tableRepository;
    }

    public int getTables()
    {
        return tableRepository.size();
    }
}
