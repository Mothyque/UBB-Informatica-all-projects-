package com.ubb.domain.event;

import com.ubb.domain.duck.SwimmingDuck;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class RaceEvent extends Event
{
    private final List<SwimmingDuck> participants;
    private int winnerId;

    public RaceEvent(List<SwimmingDuck> participants)
    {
        super();
        this.participants = participants;
    }

    public void sortPaticipantsByPerformance()
    {
        participants.sort((d1, d2) -> {
            double performance1 = d1.getSpeed() * d1.getEndurance();
            double performance2 = d2.getSpeed() * d2.getEndurance();
            return Double.compare(performance2, performance1);
        });
    }

    public void runRace()
    {
        if (participants.isEmpty())
        {
            winnerId = -1;
        }
        else
        {
            sortPaticipantsByPerformance();
            winnerId = participants.getFirst().getId();
        }
        notifyObservers(this);
    }

    public List<SwimmingDuck> getParticipants()
    {
        return participants;
    }

    public int getWinnerId()
    {
        return winnerId;
    }

    public void setWinnerId(Integer winnerId)
    {
        this.winnerId = winnerId;
    }

    private String addMembersToString(List<SwimmingDuck> members, String string)
    {
        for (SwimmingDuck member : members)
        {
            string += member.getId() + ", ";
        }
        if(!members.isEmpty())
            string = string.substring(0, string.length() - 2);
        string += "]";
        return string;
    }

    @Override
    public String toString()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String string = "RaceEvent{" +
                "id=" + getId() +
                ", date=" + getDate().format(formatter) +
                ", participants=[";
        string = addMembersToString(participants, string);
        string += ", winnerId=" + winnerId +
                '}';
        return string;
    }
}
