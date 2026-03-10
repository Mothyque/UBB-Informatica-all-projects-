package com.ubb.service;

import com.ubb.domain.friendship.Friendship;
import com.ubb.domain.friendship.FriendshipStatus;
import com.ubb.domain.Person;
import com.ubb.domain.User;
import com.ubb.domain.event.ChangeEventType;
import com.ubb.domain.event.EntityChangeEvent;
import com.ubb.repository.IDuckRepository;
import com.ubb.repository.Repository;
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
import java.util.stream.StreamSupport;

public class FriendshipService extends AbstractService<Friendship>
{
    private final Repository<Tuple<Integer, Integer>, Friendship> friendshipRepository;
    private final Repository<Integer, Person> personRepository;
    private final IDuckRepository duckRepository;
    private final Validator<Friendship> friendshipValidator;

    public FriendshipService(Repository<Tuple<Integer, Integer>, Friendship> friendshipRepository, Repository<Integer, Person> personRepository, IDuckRepository duckRepository, Validator<Friendship> friendshipValidator)
    {
        this.friendshipRepository = friendshipRepository;
        this.personRepository = personRepository;
        this.duckRepository = duckRepository;
        this.friendshipValidator = friendshipValidator;
    }

    public void sendFriendRequest(int senderId, int receiverId)
    {
        if(senderId == receiverId)
        {
            throw new InputException("You cannot send a friend request to yourself!");
        }

        findUserById(senderId).orElseThrow(() -> new InputException("User with id " + senderId + " does not exist."));
        findUserById(receiverId).orElseThrow(() -> new InputException("User with id " + receiverId + " does not exist."));

        Friendship existing = findOneByUsers(senderId, receiverId);

        if(existing != null)
        {
            if (existing.getStatus() == FriendshipStatus.PENDING)
            {
                throw new DuplicateException("Friend request already sent!");
            }
            if (existing.getStatus() == FriendshipStatus.APPROVED)
            {
                throw new DuplicateException("You are already friends!");
            }
            if(existing.getStatus() == FriendshipStatus.REJECTED)
            {
                friendshipRepository.delete(existing.getId());
            }
        }
        Friendship request = new Friendship(senderId, receiverId);
        friendshipValidator.validate(request);
        friendshipRepository.save(request);
        notifyObservers(new EntityChangeEvent<>(ChangeEventType.ADD, request));
    }

    private Friendship findOneByUsers(int userId1, int userId2)
    {
        for (Friendship friendship : friendshipRepository.findAll())
        {
            boolean direct = friendship.getId().getLeft() == userId1 && friendship.getId().getRight() == userId2;
            boolean reverse = friendship.getId().getLeft() == userId2 && friendship.getId().getRight() == userId1;
            if (direct || reverse)
            {
                return friendship;
            }
        }
        return null;
    }

    public void respondToFriendRequest(Friendship request, FriendshipStatus newStatus)
    {
        if(request.getStatus() != FriendshipStatus.PENDING)
        {
            throw new InputException("Friend request is not pending!");
        }
        request.setStatus(newStatus);
        friendshipRepository.update(request);
        notifyObservers(new EntityChangeEvent<>(ChangeEventType.UPDATE, null));
    }

    public List<Friendship> getPendingFriendRequests(int userId)
    {
        return StreamSupport.stream(friendshipRepository.findAll().spliterator(), false)
                .filter(f -> f.getId2() == userId)
                .filter(f -> f.getStatus() == FriendshipStatus.PENDING)
                .toList();
    }

    public void addFriendship(int personId1, int personId2)
    {
        findUserById(personId1).orElseThrow(() -> new InputException("User with id " + personId1 + " does not exist."));
        findUserById(personId2).orElseThrow(() -> new InputException("User with id " + personId2 + " does not exist."));

        int first = Math.min(personId1, personId2);
        int second = Math.max(personId1, personId2);

        Friendship friendship = new Friendship(first, second);
        friendshipValidator.validate(friendship);
        if (friendshipRepository.findOne(friendship.getId()).isPresent())
        {
            throw new DuplicateException("Friendship already exists!");
        }
        friendshipRepository.save(friendship);
        notifyObservers(new EntityChangeEvent<>(ChangeEventType.ADD, null));
    }

    public void deleteFriendship(int personId1, int personId2)
    {
        Optional<Friendship> f1 = friendshipRepository.findOne(new Tuple<>(personId1, personId2));
        Optional<Friendship> f2 = friendshipRepository.findOne(new Tuple<>(personId2, personId1));

        if (f1.isPresent())
        {
            friendshipRepository.delete(new Tuple<>(personId1, personId2));
        }
        else if (f2.isPresent())
        {
            friendshipRepository.delete(new Tuple<>(personId2, personId1));
        }
        else
        {
            throw new InputException("Friendship does not exist.");
        }

        notifyObservers(new EntityChangeEvent<>(ChangeEventType.DELETE, null));
    }

    public List<User> getFriendsOfUser(int userId)
    {
        findUserById(userId).orElseThrow(() -> new InputException("User with id " + userId + " does not exist."));
        List<User> friends = new ArrayList<>();

        for (Friendship friendship : friendshipRepository.findAll())
        {
            if(friendship.getStatus() == FriendshipStatus.APPROVED)
            {
                if (Objects.equals(friendship.getId().getLeft(), userId))
                {
                    findUserById(friendship.getId().getRight()).ifPresent(friends::add);
                }
                else if (Objects.equals(friendship.getId().getRight(), userId))
                {
                    findUserById(friendship.getId().getLeft()).ifPresent(friends::add);
                }
            }
        }
        return friends;
    }

    private boolean existsFriendshipOrRequest(int userId1, int userId2)
    {
        for (Friendship friendship : friendshipRepository.findAll())
        {
            boolean direct = friendship.getId().getLeft() == userId1 && friendship.getId().getRight() == userId2;
            boolean reverse = friendship.getId().getLeft() == userId2 && friendship.getId().getRight() == userId1;
            if (direct || reverse)
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterable<Friendship> getAll()
    {
        return friendshipRepository.findAll();
    }

    @Override
    public Page<Friendship> findAllOnPage(Pageable pageable)
    {
        List<Friendship> friendships = new ArrayList<>();
        friendshipRepository.findAll().forEach(friendships::add);

        int totalElements = friendships.size();
        int fromIndex = pageable.getPageNumber() * pageable.getPageSize();
        int toIndex = Math.min(fromIndex + pageable.getPageSize(), totalElements);

        if (fromIndex >= totalElements)
        {
            return new Page<>(new ArrayList<>(), totalElements);
        }
        List<Friendship> pageElements = friendships.subList(fromIndex, toIndex);
        return new Page<>(pageElements, totalElements);
    }

    private Optional<User> findUserById(int userId)
    {
        Optional<Person> personOpt = personRepository.findOne(userId);
        if (personOpt.isPresent())
        {
            return personOpt.map(person -> (User) person);
        }
        return duckRepository.findOne(userId).map(duck -> (User) duck);
    }
}
