package org.example.service;

import org.example.domain.Coin;
import org.example.repository.CoinRepository;

public class CoinService
{
    private CoinRepository coinRepository;

    public CoinService(CoinRepository coinRepository)
    {
        this.coinRepository = coinRepository;
    }

    public Iterable<Coin> findAll()
    {
        return this.coinRepository.findAll();
    }
}
