package com.ubb;

import com.ubb.domain.Message;
import com.ubb.domain.Person;
import com.ubb.domain.duck.Duck;
import com.ubb.domain.event.Event;
import com.ubb.domain.event.EventParticipant;
import com.ubb.domain.flock.Flock;
import com.ubb.domain.flock.FlockMembership;
import com.ubb.domain.friendship.Friendship;
import com.ubb.repository.IDuckRepository;
import com.ubb.repository.Repository;
import com.ubb.repository.paging.PagingRepository;
import com.ubb.repository.db.*;
import com.ubb.service.*;
import com.ubb.utils.Tuple;
import com.ubb.validators.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ApplicationContext
{

    private final PersonService personService;
    private final DuckService duckService;
    private final FriendshipService friendshipService;
    private final CommunityService communityService;
    private final FlockService flockService;
    private final RaceService raceService;
    private final MessageService messageService;

    private final ExecutorService executorService;

    public ApplicationContext()
    {
        this.executorService = Executors.newCachedThreadPool();

        String url = "jdbc:postgresql://localhost:5432/social_network";
        String username = "postgres";
        String password = "mathy";

        PagingRepository<Integer, Person> personRepo = new PersonDBRepository(url, username, password);
        IDuckRepository duckRepo = new DuckDBRepository(url, username, password);
        PagingRepository<Tuple<Integer, Integer>, Friendship> friendshipRepo = new FriendshipDBRepository(url, username, password);
        PagingRepository<Integer, Flock<? extends Duck>> flockRepo = new FlockDBRepository(url, username, password);
        PagingRepository<Tuple<Integer, Integer>, FlockMembership> flockMemRepo = new FlockMembershipDBRepository(url, username, password);
        PagingRepository<Integer, Event> eventRepo = new EventDBRepository(url, username, password);
        PagingRepository<Tuple<Integer, Integer>, EventParticipant> eventPartRepo = new EventParticipantDBRepository(url, username, password);
        Repository<Integer, Message> messageRepo = new MessageDBRepository(url, username, password, duckRepo, personRepo);

        Validator<Person> personVal = new PersonValidator();
        Validator<Duck> duckVal = new DuckValidator();
        Validator<Friendship> friendshipVal = new FriendshipValidator();

        this.personService = new PersonService(personRepo, duckRepo, friendshipRepo, personVal);
        this.duckService = new DuckService(duckRepo, personRepo, friendshipRepo, duckVal);
        this.communityService = new CommunityService(personRepo, duckRepo, friendshipRepo);
        this.flockService = new FlockService(flockRepo, flockMemRepo, duckRepo);
        this.raceService = new RaceService(duckRepo, eventRepo, eventPartRepo);
        this.friendshipService = new FriendshipService(friendshipRepo, personRepo, duckRepo, friendshipVal);
        this.messageService = new MessageService(messageRepo);
    }

    public PersonService getPersonService() { return personService; }
    public DuckService getDuckService() { return duckService; }
    public FriendshipService getFriendshipService() { return friendshipService; }
    public CommunityService getCommunityService() { return communityService; }
    public FlockService getFlockService() { return flockService; }
    public RaceService getRaceService() { return raceService; }
    public MessageService getMessageService() { return messageService; }
    public ExecutorService getExecutorService() { return executorService; }
}