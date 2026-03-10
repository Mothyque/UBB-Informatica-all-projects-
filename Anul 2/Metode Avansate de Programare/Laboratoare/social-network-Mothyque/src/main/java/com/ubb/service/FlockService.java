package com.ubb.service;

import com.ubb.domain.duck.*;
import com.ubb.domain.event.ChangeEventType;
import com.ubb.domain.event.EntityChangeEvent;
import com.ubb.domain.event.Event;
import com.ubb.domain.flock.*;
import com.ubb.observer.Observer;
import com.ubb.repository.Repository;
import com.ubb.repository.paging.PagingRepository;
import com.ubb.utils.Tuple;
import com.ubb.utils.paging.Page;
import com.ubb.utils.paging.Pageable;
import com.ubb.validators.DuplicateException;
import com.ubb.validators.InputException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FlockService extends AbstractService<Flock<? extends Duck>>
{
    private final PagingRepository<Integer, Flock<? extends Duck>> flockRepository;
    private final Repository<Tuple<Integer, Integer>, FlockMembership> flockMembershipRepository;
    private final Repository<Integer, Duck> duckRepository;

    public FlockService(PagingRepository<Integer, Flock<? extends Duck>> flockRepository, Repository<Tuple<Integer, Integer>, FlockMembership> flockMembershipRepository, Repository<Integer, Duck> duckRepository)
    {
        this.flockRepository = flockRepository;
        this.flockMembershipRepository = flockMembershipRepository;
        this.duckRepository = duckRepository;
    }

    public Flock<? extends Duck> getFlockWithMembers(int flockId)
    {
        Flock<? extends Duck> flock = flockRepository.findOne(flockId).orElseThrow(() -> new InputException("Flock with the given ID does not exist!"));
        hydrateFlock(flock);
        return flock;
    }

    public void addMemberToFlock(int flockId, int duckId)
    {
        Flock<? extends Duck> flock = flockRepository.findOne(flockId).orElseThrow(() -> new InputException("Flock with the given ID does not exist!"));
        Duck duck = duckRepository.findOne(duckId).orElseThrow(() -> new InputException("Duck with the given ID does not exist!"));

        if((flock instanceof SwimmingFlock && duck instanceof SwimmingDuck) ||
           (flock instanceof FlyingFlock && duck instanceof FlyingDuck) ||
           (flock instanceof FlyingAndSwimmingFlock && duck instanceof FlyingAndSwimmingDuck))
        {
            FlockMembership membership = new FlockMembership(flockId, duckId);
            if(flockMembershipRepository.findOne(membership.getId()).isPresent())
            {
                throw new DuplicateException("Duck is already a member of the flock!");
            }
            flockMembershipRepository.save(membership);
            notifyObservers(new EntityChangeEvent<>(ChangeEventType.ADD_MEMBER_TO_FLOCK, flock));
        }
        else
        {
            throw new InputException("Duck type does not match flock type!");
        }
    }

    public void removeMemberFromFlock(int flockId, int duckId)
    {
        Flock<? extends Duck> flock = flockRepository.findOne(flockId).orElseThrow(() -> new InputException("Flock with the given ID does not exist!"));
        Duck duck = duckRepository.findOne(duckId).orElseThrow(() -> new InputException("Duck with the given ID does not exist!"));

        FlockMembership membership = new FlockMembership(flockId, duckId);
        flockMembershipRepository.delete(membership.getId()).orElseThrow(() -> new InputException("Duck is not a member of the flock!"));
        notifyObservers(new EntityChangeEvent<>(ChangeEventType.REMOVE_MEMBER_FROM_FLOCK, flock));
    }

    public void addFlock(int id, String name, DuckType type)
    {
        if(checkFlockExists(id))
        {
            throw new DuplicateException("Flock with the given ID already exists.");
        }
        Flock<? extends Duck> flock;
        switch(type)
        {
            case FLYING -> flock = new FlyingFlock(id, name);
            case SWIMMING -> flock = new SwimmingFlock(id, name);
            case FLYING_AND_SWIMMING -> flock = new FlyingAndSwimmingFlock(id, name);
            default -> throw new InputException("Invalid duck type.");
        }
        flockRepository.save(flock);
        notifyObservers(new EntityChangeEvent<>(ChangeEventType.ADD, flock));
    }

    public void deleteFlock(int id)
    {
        flockRepository.delete(id).orElseThrow(() -> new InputException("Flock with the given ID does not exist."));
        notifyObservers(new EntityChangeEvent<>(ChangeEventType.DELETE, null));
    }

    private boolean checkFlockExists(int flockId)
    {
        return flockRepository.findOne(flockId).isPresent();
    }

    @Override
    public Iterable<Flock<? extends Duck>> getAll()
    {
        Iterable<Flock<? extends Duck>> flocks = flockRepository.findAll();
        for(Flock<? extends Duck> flock : flocks)
        {
            hydrateFlock(flock);
        }
        return flocks;
    }

    @Override
    public Page<Flock<? extends Duck>> findAllOnPage(Pageable pageable)
    {
        Page<Flock<? extends Duck>> page = flockRepository.findAllPaged(pageable);

        for (Flock<? extends Duck> flock : page.getElementsOnPage())
        {
            hydrateFlock(flock);
        }
        return page;
    }

    private void hydrateFlock(Flock<? extends Duck> flock)
    {
        Iterable<FlockMembership> memberships = flockMembershipRepository.findAll();

        for(FlockMembership membership : memberships)
        {
            if(membership.getFlockId().equals(flock.getId()))
            {
                duckRepository.findOne(membership.getDuckId()).ifPresent(duck ->
                {
                    addDuckToFlockInstance(flock, duck);
                });
            }
        }
    }

    private void addDuckToFlockInstance(Flock<? extends Duck> flock, Duck duck)
    {
        if(flock instanceof SwimmingFlock swimmingFlock && duck instanceof SwimmingDuck swimmingDuck)
        {
            swimmingFlock.addMember(swimmingDuck);
        }
        else if(flock instanceof FlyingFlock flyingFlock && duck instanceof FlyingDuck flyingDuck)
        {
            flyingFlock.addMember(flyingDuck);
        }
        else if(flock instanceof FlyingAndSwimmingFlock flyingAndSwimmingFlock && duck instanceof FlyingAndSwimmingDuck flyingAndSwimmingDuck)
        {
            flyingAndSwimmingFlock.addMember(flyingAndSwimmingDuck);
        }
    }
}

