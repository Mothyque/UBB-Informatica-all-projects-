package com.ubb.service;

import com.ubb.domain.event.Event;
import com.ubb.observer.Observable;
import com.ubb.observer.Observer;
import com.ubb.utils.paging.Page;
import com.ubb.utils.paging.Pageable;


public interface Service<E> extends Observable<Event>
{
    Iterable<E> getAll();

    void addObserver(Observer<Event> observer);

    Page<E> findAllOnPage(Pageable pageable);
}