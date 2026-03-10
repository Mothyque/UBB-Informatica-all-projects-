package com.ubb.service;

import com.ubb.domain.duck.Duck;
import com.ubb.domain.duck.SwimmingDuck;
import com.ubb.domain.event.*;
import com.ubb.observer.Observer;
import com.ubb.repository.Repository;
import com.ubb.utils.Tuple;
import com.ubb.utils.paging.Page;
import com.ubb.utils.paging.Pageable;
import com.ubb.validators.InputException;
import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.List;

public class RaceService extends AbstractService<Event>
{
    private final Repository<Integer, Duck> duckRepository;
    private final Repository<Integer, Event> eventRepository;
    private final Repository<Tuple<Integer, Integer>, EventParticipant> eventParticipantRepository;

    public RaceService(Repository<Integer, Duck> duckRepository, Repository<Integer, Event> eventRepository, Repository<Tuple<Integer, Integer>, EventParticipant> eventParticipantRepository)
    {
        this.duckRepository = duckRepository;
        this.eventRepository = eventRepository;
        this.eventParticipantRepository = eventParticipantRepository;
    }

    public Iterable<Event> getAllRaces()
    {
        Iterable<Event> events = eventRepository.findAll();
        for(Event event : events)
        {
            if(event instanceof RaceEvent)
            {
                hydrateParticipants((RaceEvent) event);
            }
        }
        return events;
    }

    public List<Event> getAllActiveRaces()
    {
        Iterable<Event> events = eventRepository.findAll();
        List<Event> activeRaces = new ArrayList<>();
        for (Event event : events)
        {
            if (event instanceof RaceEvent && ((RaceEvent) event).getWinnerId() == 0)
            {
                hydrateParticipants((RaceEvent) event);
                activeRaces.add(event);
            }
        }
        return activeRaces;
    }

    public void addParticipant(int eventId, int duckId)
    {
        Event event = eventRepository.findOne(eventId).orElseThrow(() -> new InputException("Event with id " + eventId + " does not exist."));
        if(!(event instanceof RaceEvent))
        {
            throw new InputException("Event with id " + eventId + " is not a race event.");
        }
        if(((RaceEvent) event).getWinnerId() != 0)
        {
            throw new InputException("Cannot add participants to a race that has already been finished.");
        }
        Duck duck = duckRepository.findOne(duckId).orElseThrow(() -> new InputException("Duck with id " + duckId + " does not exist."));
        if(!(duck instanceof SwimmingDuck))
        {
            throw new InputException("Duck with id " + duckId + " is not a swimming duck.");
        }
        hydrateParticipants((RaceEvent) event);
        boolean alreadyoined = (( (RaceEvent) event).getParticipants().stream().anyMatch(d -> d.getId() == duckId));
        if(alreadyoined)
        {
            throw new InputException("Duck with id " + duckId + " is already a participant in the race with id " + eventId + ".");
        }
        EventParticipant eventParticipant = new EventParticipant(eventId, duckId);
        eventParticipantRepository.save(eventParticipant);

        ((RaceEvent) event).getParticipants().add((SwimmingDuck) duck);

        notifyObservers(new EntityChangeEvent<>(ChangeEventType.ADD, event));
    }

    public Iterable<SwimmingDuck> getParticipants(int eventId)
    {
        Event event = eventRepository.findOne(eventId).orElseThrow(() -> new InputException("Event with id " + eventId + " does not exist."));
        if (!(event instanceof RaceEvent)) {

            throw new InputException("Event with id " + eventId + " is not a race event.");
        }
        hydrateParticipants((RaceEvent) event);
        return ((RaceEvent) event).getParticipants();
    }

    public void addRace(List<Integer> duckIds)
    {
        List<SwimmingDuck> participants = new ArrayList<>();
        for (Integer i : duckIds)
        {
            Duck duck = duckRepository.findOne(i)
                    .orElseThrow(() -> new InputException("Duck with id " + i + " does not exist."));
            if(!(duck instanceof SwimmingDuck))
            {
                throw new InputException("Duck with id " + i + " is not a swimming duck.");
            }
            participants.add((SwimmingDuck) duck);
        }
        RaceEvent raceEvent = new RaceEvent(participants);
        int eventId = eventRepository.size() + 1;
        raceEvent.setId(eventId);
        eventRepository.save(raceEvent);
        for (SwimmingDuck duck : participants)
        {
            EventParticipant eventParticipant = new EventParticipant(eventId, duck.getId());
            eventParticipantRepository.save(eventParticipant);
        }
        notifyObservers(new EntityChangeEvent<>(ChangeEventType.ADD, raceEvent));
    }

    public void runRace(int eventId)
    {
        RaceEvent event = (RaceEvent) eventRepository.findOne(eventId)
                .filter(e -> e instanceof RaceEvent)
                .orElseThrow(() -> new InputException("Event with id " + eventId + " does not exist or is not a race event."));

        if(event.getWinnerId() != 0)
        {
            throw new InputException("Race already finished.");
        }

        hydrateParticipants(event);

        Task<Void> raceTask = new Task<Void>()
        {
            @Override
            protected Void call() throws Exception
            {
                Thread.sleep(5000);
                event.runRace();
                eventRepository.update(event);
                return null;
            }
            @Override
            protected void succeeded()
            {
                notifyObservers(new EntityChangeEvent<>(ChangeEventType.UPDATE, event));
            }
        };
        new Thread(raceTask).start();
    }

    public void deleteRace(int eventId)
    {
        eventRepository.delete(eventId).orElseThrow(() -> new InputException("Event with id " + eventId + " does not exist."));
        notifyObservers(new EntityChangeEvent<>(ChangeEventType.DELETE, null));
    }


    private void hydrateParticipants(RaceEvent race)
    {
        int eventId = race.getId();
        race.getParticipants().clear();
        Iterable<EventParticipant> eventParticipants = eventParticipantRepository.findAll();
        for (EventParticipant ep : eventParticipants)
        {
            if (ep.getEventId().equals(eventId))
            {
                duckRepository.findOne(ep.getUserId())
                        .filter(duck -> duck instanceof SwimmingDuck)
                        .map(duck -> (SwimmingDuck) duck)
                        .ifPresent(duck -> race.getParticipants().add(duck));
            }
        }
    }

    @Override
    public Iterable<Event> getAll()
    {
        return getAllRaces();
    }

    @Override
    public Page<Event> findAllOnPage(Pageable pageable)
    {
        List<Event> allEvents = new ArrayList<>();
        for(Event event : getAllRaces())
        {
            allEvents.add(event);
        }
        int totalElements = allEvents.size();
        int fromIndex = pageable.getPageNumber() * pageable.getPageSize();
        int toIndex = Math.min(fromIndex + pageable.getPageSize(), totalElements);
        List<Event> eventsOnPage = allEvents.subList(fromIndex, toIndex);
        return new Page<>(eventsOnPage, totalElements);
    }
}
