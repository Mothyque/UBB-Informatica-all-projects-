package com.ubb.service;

import com.ubb.domain.User;
import com.ubb.domain.duck.Duck;
import com.ubb.domain.friendship.Friendship;
import com.ubb.domain.Person;
import com.ubb.repository.Repository;
import com.ubb.utils.Tuple;

import java.util.*;


public class CommunityService
{
    private final Repository<Integer, Person> persons;
    private final Repository<Integer, Duck> ducks;
    private final Repository<Tuple<Integer, Integer>, Friendship> friendships;

    public CommunityService(Repository<Integer, Person> persons, Repository<Integer, Duck> ducks, Repository<Tuple<Integer, Integer>, Friendship> friendships)
    {
        this.persons = persons;
        this.ducks = ducks;
        this.friendships = friendships;
    }

    public int calculateNumberOfCommunities()
    {
        List<List<Integer>> allCommunities = getAllCommunities();
        return (int) allCommunities.stream()
                .filter(c -> c.size() >= 2)
                .count();
    }

    public int findLargestCommunitySize()
    {
        List<List<Integer>> allCommunities = getAllCommunities();
        if(allCommunities.isEmpty())
        {
            return 0;
        }
        return allCommunities.stream()
                .mapToInt(List::size)
                .max()
                .orElse(0);
    }

    private List<List<Integer>> getAllCommunities()
    {
        Map<Integer, List<Integer>> graph = buildGraph();
        Set<Integer> visited = new HashSet<>();
        List<List<Integer>> communities = new ArrayList<>();

        for(Integer userId : graph.keySet())
        {
            if(!visited.contains(userId))
            {
                List<Integer> currentCommunity = new ArrayList<>();
                Queue<Integer> queue = new LinkedList<>();

                queue.add(userId);
                visited.add(userId);

                while(!queue.isEmpty())
                {
                    Integer currentId = queue.poll();
                    currentCommunity.add(currentId);
                    if (graph.containsKey(currentId)) {
                        for(Integer neighborId : graph.get(currentId))
                        {
                            if(!visited.contains(neighborId))
                            {
                                visited.add(neighborId);
                                queue.add(neighborId);
                            }
                        }
                    }
                }
                communities.add(currentCommunity);
            }
        }
        return communities;
    }
    public Map<Integer, List<Integer>> buildGraph()
    {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (Person person : persons.findAll())
        {
            graph.put(person.getId(), new ArrayList<>());
        }
        for (Duck duck : ducks.findAll())
        {
            graph.put(duck.getId(), new ArrayList<>());
        }
        for (Friendship friendship : friendships.findAll())
        {
            Integer id1 = friendship.getId().getLeft();
            Integer id2 = friendship.getId().getRight();
            if(graph.containsKey(id1) && graph.containsKey(id2))
            {
                graph.get(id1).add(id2);
                graph.get(id2).add(id1);
            }
        }
        return graph;
    }

    public List<User> getCommunityMembers(int userId)
    {
        List<User> communityMembers = new ArrayList<>();
        Map<Integer, List<Integer>> adjList = new HashMap<>();

        for(Friendship friendship : friendships.findAll())
        {
            adjList.putIfAbsent(friendship.getId1(), new ArrayList<>());
            adjList.putIfAbsent(friendship.getId2(), new ArrayList<>());

            adjList.get(friendship.getId1()).add(friendship.getId2());
            adjList.get(friendship.getId2()).add(friendship.getId1());
        }
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();

        if(adjList.containsKey(userId))
        {
            queue.add(userId);
            visited.add(userId);

            while(!queue.isEmpty())
            {
                int currentId = queue.poll();
                findUserById(currentId).ifPresent(communityMembers::add);

                if(adjList.containsKey(currentId))
                {
                    for(int neighborId : adjList.get(currentId))
                    {
                        if(!visited.contains(neighborId))
                        {
                            visited.add(neighborId);
                            queue.add(neighborId);
                        }
                    }
                }
            }
        }
        else
        {
            findUserById(userId).ifPresent(communityMembers::add);
        }
        return communityMembers;
    }

    private Optional<User> findUserById(int id)
    {
        Optional<Duck> d = ((Repository<Integer, Duck>) ducks).findOne(id);
        if(d.isPresent())
        {
            return Optional.of(d.get());
        }
        Optional<Person> p = ((Repository<Integer, Person>) persons).findOne(id);
        if(p.isPresent())
        {
            return Optional.of(p.get());
        }
        return Optional.empty();
    }

}