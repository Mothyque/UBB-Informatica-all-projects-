package com.ubb.service;

import com.ubb.domain.friendship.Friendship;
import com.ubb.domain.Person;
import com.ubb.domain.duck.Duck;
import com.ubb.domain.duck.DuckType;
import com.ubb.domain.duck.FlyingAndSwimmingDuck;
import com.ubb.domain.duck.FlyingDuck;
import com.ubb.domain.duck.SwimmingDuck;
import com.ubb.domain.event.ChangeEventType;
import com.ubb.domain.event.EntityChangeEvent;
import com.ubb.repository.IDuckRepository;
import com.ubb.repository.Repository;
import com.ubb.utils.PasswordHasher;
import com.ubb.utils.Tuple;
import com.ubb.utils.paging.Page;
import com.ubb.utils.paging.Pageable;
import com.ubb.validators.DuplicateException;
import com.ubb.validators.InputException;
import com.ubb.validators.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DuckService extends AbstractService<Duck>
{

    private final IDuckRepository duckRepository;
    private final Repository<Integer, com.ubb.domain.Person> personRepository;
    private final Repository<Tuple<Integer, Integer>, Friendship> friendshipRepository;
    private final Validator<Duck> duckValidator;

    public DuckService(IDuckRepository duckRepository, Repository<Integer, Person> personRepository, Repository<Tuple<Integer, Integer>, Friendship> friendshipRepository, Validator<Duck> duckValidator)
    {
        this.duckRepository = duckRepository;
        this.personRepository = personRepository;
        this.friendshipRepository = friendshipRepository;
        this.duckValidator = duckValidator;
    }

    @Override
    public Iterable<Duck> getAll()
    {
        return duckRepository.findAll();
    }

    @Override
    public Page<Duck> findAllOnPage(Pageable pageable)
    {
        return duckRepository.findAllPaged(pageable);
    }

    public Page<Duck> filterDucksByType(Pageable pageable, DuckType type)
    {
        if (type == null) {
            return duckRepository.findAllPaged(pageable);
        }
        return duckRepository.findAllByType(pageable, type);
    }

    public void addDuck(int id, String username, String email, String password, String tipRata, double viteza, double rezistenta)
    {
        String passwordHash = PasswordHasher.hash(password);
        Duck duck = switch (tipRata.toLowerCase())
        {
            case "f" -> new FlyingDuck(id, username, email, passwordHash, viteza, rezistenta);
            case "s" -> new SwimmingDuck(id, username, email, passwordHash, viteza, rezistenta);
            case "fs" -> new FlyingAndSwimmingDuck(id, username, email, passwordHash, viteza, rezistenta);
            default -> throw new InputException("Invalid duck type.");
        };

        duckValidator.validate(duck);

        if (duckRepository.findOne(id).isPresent() || personRepository.findOne(id).isPresent())
        {
            throw new DuplicateException("User/Duck with ID " + id + " already exists.");
        }
        duckRepository.save(duck);
        notifyObservers(new EntityChangeEvent<>(ChangeEventType.ADD, duck));
    }

    public void deleteDuck(int id)
    {
        Duck duck = duckRepository.findOne(id).orElseThrow(() -> new InputException("Duck with id " + id + " does not exist."));

        List<Tuple<Integer, Integer>> friendshipsToDelete = new ArrayList<>();
        for (com.ubb.domain.friendship.Friendship friendship : friendshipRepository.findAll())
        {
            if (Objects.equals(friendship.getId1(), id) || Objects.equals(friendship.getId2(), id))
            {
                friendshipsToDelete.add(friendship.getId());
            }
        }
        for (Tuple<Integer, Integer> fid : friendshipsToDelete)
        {
            friendshipRepository.delete(fid);
        }
        duckRepository.delete(id);
        notifyObservers(new EntityChangeEvent<>(ChangeEventType.DELETE, duck));
    }

    public Duck findDuckById(int id)
    {
        return duckRepository.findOne(id).orElseThrow(() -> new InputException("Duck not found."));
    }
}