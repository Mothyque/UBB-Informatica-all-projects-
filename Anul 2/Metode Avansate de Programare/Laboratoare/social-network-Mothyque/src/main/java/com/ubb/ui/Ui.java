package com.ubb.ui;

import com.ubb.domain.duck.Duck;
import com.ubb.domain.duck.DuckType;
import com.ubb.domain.event.EntityChangeEvent;
import com.ubb.domain.event.Event;
import com.ubb.observer.Observer;
import com.ubb.domain.event.RaceEvent;
import com.ubb.domain.flock.Flock;
import com.ubb.domain.Person;
import com.ubb.domain.User;
import com.ubb.service.*;
import com.ubb.utils.paging.Pageable;
import com.ubb.utils.paging.Page;
import com.ubb.validators.DuplicateException;
import com.ubb.validators.InputException;
import com.ubb.validators.PersistenceException;
import com.ubb.validators.ValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ui implements Observer<Event>
{
    private final PersonService personService;
    private final DuckService duckService;
    private final CommunityService communityService;
    private final FlockService flockService;
    private final RaceService raceService;
    private final FriendshipService friendshipService;
    private final Scanner scanner;

    public Ui(PersonService personService, DuckService duckService, CommunityService communityService, FlockService flockService, RaceService raceService, FriendshipService friendshipService)
    {
        this.personService = personService;
        this.duckService = duckService;
        this.communityService = communityService;
        this.flockService = flockService;
        this.raceService = raceService;
        this.friendshipService = friendshipService;
        this.scanner = new Scanner(System.in);

        this.personService.addObserver(this);
        this.duckService.addObserver(this);
        this.flockService.addObserver(this);
        this.raceService.addObserver(this);
        this.friendshipService.addObserver(this);
    }

    @Override
    public void update(Event event)
    {
        if(event instanceof RaceEvent race)
        {
            int winnerId = race.getWinnerId();
            if(winnerId != -1)
            {
                Duck winner = duckService.findDuckById(winnerId);
                System.out.println("Race finished! The winner is: " + winner.getUsername());
            }
            else
            {
                System.out.println("Race finished! No participants.");
            }
            System.out.println("-------------------------------");
        }
        else if(event instanceof EntityChangeEvent<?> entityChangeEvent)
        {
            System.out.println("Entity changed: " + entityChangeEvent.getType() +
                               " | Entity details: " + entityChangeEvent.getEntity());
            System.out.println("-------------------------------");
        }
    }

    private void printMenu()
    {
        System.out.println("D. Duck menu");
        System.out.println("P. Person menu");
        System.out.println("F. Friendship menu");
        System.out.println("M. Flock menu");
        System.out.println("E. Event menu");
        System.out.println("0. Exit");
    }

    private void duckMenu()
    {
        System.out.println("A. Show all ducks");
        System.out.println("1. Add duck");
        System.out.println("2. Delete duck");
        System.out.println("0. Back to main menu");
    }

    private void personMenu()
    {
        System.out.println("A. Show all persons");
        System.out.println("1. Add person");
        System.out.println("2. Delete person");
        System.out.println("0. Back to main menu");
    }

    private void friendshipMenu()
    {
        System.out.println("A. Show all friendships");
        System.out.println("1. Add friendship");
        System.out.println("2. Delete friendship");
        System.out.println("3. Number of communities");
        System.out.println("4. Largest community");
        System.out.println("0. Back to main menu");
    }

    private void flockMenu()
    {
        System.out.println("A. Show flock by ID");
        System.out.println("1. Add duck to flock");
        System.out.println("2. Remove duck from flock");
        System.out.println("3. Create new flock");
        System.out.println("4. Delete flock");
        System.out.println("0. Back to main menu");
    }

    private void eventMenu()
    {
        System.out.println("A. Show all events");
        System.out.println("1. Add event");
        System.out.println("2. Delete event");
        System.out.println("3. Run event");
        System.out.println("0. Back to main menu");
    }

    public void run()
    {
        while(true)
        {
            try {
                printMenu();
                System.out.print("Choose an option: ");
                String option = scanner.nextLine();
                option = option.toUpperCase();
                switch (option)
                {
                    case "D" ->
                    {
                       while(true)
                        {
                            duckMenu();
                            System.out.print("Choose an option: ");
                            String duckOption = scanner.nextLine().toUpperCase();
                            System.out.println("-------------------------------");
                            if(duckOption.equals("0"))
                            {
                                break;
                            }
                            switch (duckOption)
                            {
                                 case "A" -> showDucksPaginated();
                                 case "1" -> addDuck();
                                 case "2" -> deleteDuck();
                                 default -> throw new InputException("Invalid option!");
                            }
                        }
                    }
                    case "P" ->
                    {
                        while(true)
                        {
                            personMenu();
                            System.out.print("Choose an option: ");
                            String personOption = scanner.nextLine().toUpperCase();
                            System.out.println("-------------------------------");
                            if(personOption.equals("0"))
                            {
                                break;
                            }
                            switch (personOption)
                            {
                                case "A" -> showPersons();
                                case "1" -> addPerson();
                                case "2" -> deletePerson();
                                default -> throw new InputException("Invalid option!");
                            }
                        }
                    }
                    case "F" ->
                    {
                        while(true)
                        {
                            friendshipMenu();
                            System.out.print("Choose an option: ");
                            String friendshipOption = scanner.nextLine().toUpperCase();
                            System.out.println("-------------------------------");
                            if(friendshipOption.equals("0"))
                            {
                                break;
                            }
                            switch (friendshipOption)
                            {
                                case "A" -> showFriendships();
                                case "1" -> addFriendship();
                                case "2" -> deleteFriendship();
                                case "3" -> numberOfCommunities();
                                case "4" -> largestCommunity();
                                default -> throw new InputException("Invalid option!");
                            }
                        }
                    }
                    case "M" ->
                    {
                        while(true)
                        {
                            flockMenu();
                            System.out.print("Choose an option: ");
                            String flockOption = scanner.nextLine().toUpperCase();
                            System.out.println("-------------------------------");
                            if(flockOption.equals("0"))
                            {
                                break;
                            }
                            switch (flockOption)
                            {
                                case "A" -> showFlockById();
                                case "1" -> addDuckToFlock();
                                case "2" -> removeDuckFromFlock();
                                case "3" -> createNewFlock();
                                case "4" -> deleteFlock();
                                default -> throw new InputException("Invalid option!");
                            }
                        }
                    }
                    case "E" ->
                    {
                        while (true)
                        {
                            eventMenu();
                            System.out.print("Choose an option: ");
                            String eventOption = scanner.nextLine().toUpperCase();
                            System.out.println("-------------------------------");
                            if (eventOption.equals("0"))
                            {
                                break;
                            }
                            switch (eventOption.toUpperCase())
                            {
                                case "A" ->
                                {
                                    showEvents();
                                }
                                case "1" ->
                                {
                                    addEventMenu();
                                }
                                case "2" ->
                                {
                                    deleteEvent();
                                }
                                case "3" ->
                                {
                                    runRaceEvent();
                                }
                            }
                        }
                    }
                    case "0" ->
                    {
                        System.out.println("Exiting...");
                        return;
                    }
                    default ->
                    {
                        throw new InputException("Invalid option!");
                    }
                }
            }
            catch (ValidationException | DuplicateException | InputException | NumberFormatException |
                       PersistenceException e)
                {
                    System.out.println("Error: " + e.getMessage());
                    System.out.println("-------------------------------");
                }
        }
    }

    private void showPersons()
    {
        Iterable<Person> persons = personService.getAll();
        for(Person person : persons)
        {
            System.out.println(person);
        }
        System.out.println("-------------------------------");
    }

    private void showDucks()
    {
        Iterable<Duck> ducks = duckService.getAll();
        for(Duck duck : ducks)
        {
            System.out.println(duck);
        }
        System.out.println("-------------------------------");
    }

    private void showDucksPaginated()
    {
        int pageSize = 5;
        int currentPage = 0;
        while(true)
        {
            Pageable pageable = new Pageable(currentPage, pageSize);
            Page<Duck> ducksPage = duckService.findAllOnPage(pageable);

            for(Duck duck : ducksPage.getElementsOnPage())
            {
                System.out.println(duck);
            }
            int totalElements = ducksPage.getTotalElements();
            int totalPages = (int) Math.ceil((double) totalElements / pageSize);
            if(totalPages == 0)
            {
                totalPages = 1;
            }
            System.out.println("Page " + (currentPage + 1) + " of " + totalPages);
            System.out.println("-------------------------------");
            System.out.println("N. Next page");
            System.out.println("P. Previous page");
            System.out.println("0. Back to main menu");
            System.out.print("Choose an option: ");
            String option = scanner.nextLine();
            if(option.equalsIgnoreCase("0"))
            {
                break;
            }
            else if(option.equalsIgnoreCase("N"))
            {
                if(currentPage + 1 >= totalPages)
                {
                    System.out.println("No more pages.");
                    System.out.println("-------------------------------");
                    continue;
                }
                currentPage++;
            }
            else if(option.equalsIgnoreCase("P"))
            {
                if(currentPage == 0)
                {
                    System.out.println("No previous pages.");
                    System.out.println("-------------------------------");
                    continue;
                }
                else
                {
                    currentPage--;
                }
            }
        }
    }

    private void showFriendships()
    {
        System.out.print("Enter User ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        List<User> friends = friendshipService.getFriendsOfUser(id);
        if(!friends.isEmpty())
        {
            System.out.println("Friends of user " + id + ":");
            for (User friend : friends)
            {
                System.out.println(friend.getId() + " | " + friend.getUsername());
            }
            System.out.println("-------------------------------");
        }
        else
        {
            System.out.println("User not found!");
            System.out.println("-------------------------------");
        }
    }

    private void addPerson()
    {
        System.out.print("Enter person ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter birth date (DD-MM-YYYY): ");
        String birthDate = scanner.nextLine();
        System.out.print("Enter occupation: ");
        String occupation = scanner.nextLine();
        System.out.print("Enter empathy level (1-10): ");
        int empathyLevel = Integer.parseInt(scanner.nextLine());
        personService.addPerson(id, username, email, password, lastName, firstName, birthDate, occupation, empathyLevel);
    }

    private void addDuck()
    {
        System.out.print("Enter duck ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter type of duck (F for Flying, S for Swimming, FS for Flying and Swimming): ");
        String type = scanner.nextLine().toUpperCase();
        System.out.print("Enter speed: ");
        double speed = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter endurance: ");
        double endurance = Double.parseDouble(scanner.nextLine());
        duckService.addDuck(id, username, email, password, type, speed, endurance);
    }

    private void addFriendship()
    {
        System.out.print("Enter users' IDs: ");
        String[] ids = scanner.nextLine().split(" ");
        int id1 = Integer.parseInt(ids[0]);
        int id2 = Integer.parseInt(ids[1]);
        friendshipService.addFriendship(id1, id2);
    }

    private void deletePerson()
    {
        System.out.print("Enter person ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine());
        personService.deletePerson(id);
    }

    private void deleteDuck()
    {
        System.out.print("Enter duck ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine());
        duckService.deleteDuck(id);
    }

    private void deleteFriendship()
    {
        System.out.print("Enter users' IDs to remove friendship separated by space: ");
        String[] ids = scanner.nextLine().split(" ");
        int id1 = Integer.parseInt(ids[0]);
        int id2 = Integer.parseInt(ids[1]);
        friendshipService.deleteFriendship(id1, id2);
    }

    private void numberOfCommunities()
    {
        int number = communityService.calculateNumberOfCommunities();
        System.out.println("The number of communities is: " + number);
        System.out.println("-------------------------------");
    }

    private void largestCommunity()
    {
        int size = communityService.findLargestCommunitySize();
        System.out.println("The largest community has " + size + " members.");
        System.out.println("-------------------------------");
    }

    private void showFlockById()
    {
        System.out.print("Enter Flock ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        Flock<? extends Duck> flock = flockService.getFlockWithMembers(id);
        if(flock != null)
        {
            System.out.println(flock);
        }
        else
        {
            throw new InputException("Flock not found.");
        }
        System.out.println("-------------------------------");
    }

    private void addDuckToFlock()
    {
        System.out.print("Enter Flock ID: ");
        int flockId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Duck ID: ");
        int duckId = Integer.parseInt(scanner.nextLine());
        flockService.addMemberToFlock(flockId, duckId);
    }

    private void removeDuckFromFlock()
    {
        System.out.print("Enter Flock ID: ");
        int flockId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Duck ID: ");
        int duckId = Integer.parseInt(scanner.nextLine());
        flockService.removeMemberFromFlock(flockId, duckId);
    }

    private void createNewFlock()
    {
        System.out.print("Enter Flock type (S for Swimming, F for Flying, FS for Flying and Swimming): ");
        String flockType = scanner.nextLine().toUpperCase();
        DuckType flockTypeEnum;
        switch(flockType)
        {
            case "S" -> flockTypeEnum = DuckType.SWIMMING;
            case "F" -> flockTypeEnum = DuckType.FLYING;
            case "FS" -> flockTypeEnum = DuckType.FLYING_AND_SWIMMING;
            default -> throw new InputException("Invalid flock type!");
        }
        System.out.print("Enter Flock ID: ");
        int flockId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Flock name: ");
        String flockName = scanner.nextLine();
        flockService.addFlock(flockId, flockName, flockTypeEnum);
    }

    private void deleteFlock()
    {
        System.out.print("Enter Flock ID to delete: ");
        int flockId = Integer.parseInt(scanner.nextLine());
        flockService.deleteFlock(flockId);
    }

    private void showEvents()
    {
        Iterable<Event> events = raceService.getAllRaces();
        for(Event event : events)
        {
            System.out.println(event);
        }
        if(!events.iterator().hasNext())
        {
            System.out.println("No events found.");
        }
        System.out.println("-------------------------------");
    }

    private void addEventMenu()
    {
        while(true)
        {
            System.out.println("1. Add Race Event");
            System.out.println("0. Back to Event Menu");
            System.out.print("Choose an option: ");
            String option = scanner.nextLine();
            System.out.println("-------------------------------");
            if(option.equals("0"))
            {
                break;
            }
            if (option.equals("1"))
            {
                addEvent();
            }
            else
            {
                throw new InputException("Invalid option!");
            }
        }
    }

    private void addEvent()
    {
        System.out.print("Enter Swimming Duck IDs for the race (separated by spaces): ");
        String[] idsStr = scanner.nextLine().split(" ");
        List<Integer> duckIds = new ArrayList<>();
        for(String idStr : idsStr)
        {
            try
            {
                duckIds.add(Integer.parseInt(idStr));
            }
            catch(InputException e)
            {
                throw new InputException("Invalid duck ID: " + idStr);
            }
        }
        if(duckIds.isEmpty())
        {
            throw new InputException("No duck IDs provided.");
        }

        raceService.addRace(duckIds);
    }

    private void runRaceEvent()
    {
        System.out.print("Enter Event ID to run: ");
        int eventId = Integer.parseInt(scanner.nextLine());
        raceService.runRace(eventId);
    }

    public void deleteEvent()
    {
        System.out.print("Enter Event ID to delete: ");
        int eventId = Integer.parseInt(scanner.nextLine());
        raceService.deleteRace(eventId);
    }
}
