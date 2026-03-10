package org.example.service;

import org.example.domain.Transaction;
import org.example.repository.TransacitonRepository;
import org.example.utils.Observable;

public class TransactionService extends Observable
{
    private TransacitonRepository transacitonRepository;

    public TransactionService(TransacitonRepository transacitonRepository)
    {
        this.transacitonRepository = transacitonRepository;
    }

    public Iterable<Transaction> findAll()
    {
        return transacitonRepository.findAll();
    }
}
