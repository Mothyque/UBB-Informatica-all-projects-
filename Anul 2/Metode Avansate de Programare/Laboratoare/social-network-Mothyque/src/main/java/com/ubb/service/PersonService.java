package com.ubb.service;

import com.ubb.domain.friendship.Friendship;
import com.ubb.domain.Person;
import com.ubb.domain.event.ChangeEventType;
import com.ubb.domain.event.EntityChangeEvent;
import com.ubb.repository.IDuckRepository;
import com.ubb.repository.Repository;
import com.ubb.repository.paging.PagingRepository;
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
import java.util.Optional;

public class PersonService extends AbstractService<Person>
{

    private final PagingRepository<Integer, Person> personRepository;
    private final IDuckRepository duckRepository;
    private final Repository<Tuple<Integer, Integer>, Friendship> friendshipRepository;
    private final Validator<Person> personValidator;

    public PersonService(PagingRepository<Integer, Person> personRepository, IDuckRepository duckRepository, Repository<Tuple<Integer, Integer>, Friendship> friendshipRepository, Validator<Person> personValidator)
    {
        this.personRepository = personRepository;
        this.duckRepository = duckRepository;
        this.friendshipRepository = friendshipRepository;
        this.personValidator = personValidator;
    }

    @Override
    public Iterable<Person> getAll()
    {
        return personRepository.findAll();
    }

    @Override
    public Page<Person> findAllOnPage(Pageable pageable)
    {
        return personRepository.findAllPaged(pageable);
    }

    public void addPerson(int id, String username, String email, String password, String firstName, String lastName, String birthDate, String occupation, int empathyLevel)
    {
        String passwordHash = PasswordHasher.hash(password);
        Person person = new Person(id, username, email, passwordHash, firstName, lastName, birthDate, occupation, empathyLevel);
        personValidator.validate(person);

        if (personRepository.findOne(id).isPresent() || duckRepository.findOne(id).isPresent())
        {
            throw new DuplicateException("User with ID " + id + " already exists.");
        }

        personRepository.save(person);
        notifyObservers(new EntityChangeEvent<>(ChangeEventType.ADD, null));
    }

    public void deletePerson(int id)
    {
        Person person = personRepository.findOne(id).orElseThrow(() -> new InputException("Person with id " + id + " does not exist."));

        List<Tuple<Integer, Integer>> friendshipsToDelete = new ArrayList<>();
        for (com.ubb.domain.friendship.Friendship friendship : friendshipRepository.findAll()) {
            if (Objects.equals(friendship.getId1(), id) || Objects.equals(friendship.getId2(), id)) {
                friendshipsToDelete.add(friendship.getId());
            }
        }
        for (Tuple<Integer, Integer> friendshipId : friendshipsToDelete) {
            friendshipRepository.delete(friendshipId);
        }
        personRepository.delete(id);
        notifyObservers(new EntityChangeEvent<>(ChangeEventType.DELETE, null));
    }

    public Person findPersonById(int id)
    {
        return personRepository.findOne(id).orElseThrow(() -> new InputException("Person with id " + id + " not found."));
    }

}
