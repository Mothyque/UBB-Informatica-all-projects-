package org.example.service;

import org.example.domain.Driver;
import org.example.repository.DriverRepository;

import java.util.List;

public class DriverService
{
    DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository)
    {
        this.driverRepository = driverRepository;
    }

    public List<Driver> getAll()
    {
        return driverRepository.findAll();
    }
}
