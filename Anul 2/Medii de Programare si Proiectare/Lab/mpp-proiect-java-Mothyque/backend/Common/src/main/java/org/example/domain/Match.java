package org.example.domain;
import jakarta.persistence.*;

@jakarta.persistence.Entity
@Table(name = "matches")
public class Match extends Entity<Integer>
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "teamA")
    private String teamA;

    @Column(name = "teamB")
    private String teamB;

    @Column(name = "matchType")
    private String matchType;

    @Column(name = "ticketPrice")
    private Double ticketPrice;

    @Column(name = "totalSeats")
    private Integer totalSeats;

    @Column(name = "availableSeats")
    private Integer availableSeats;

    public Match() {}

    public Match(String teamA, String teamB, String matchType, Double ticketPrice, Integer totalSeats, Integer availableSeats)
    {
        this.teamA = teamA;
        this.teamB = teamB;
        this.matchType = matchType;
        this.ticketPrice = ticketPrice;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
    }

    @Override
    public Integer getId() {return id;}

    @Override
    public void setId(Integer id) {this.id = id;}


    public String getTeamA() { return teamA; }
    public void setTeamA(String teamA) { this.teamA = teamA; }
    public String getTeamB() { return teamB; }
    public void setTeamB(String teamB) { this.teamB = teamB; }
    public String getMatchType() { return matchType; }
    public void setMatchType(String matchType) { this.matchType = matchType; }
    public Double getTicketPrice() { return ticketPrice; }
    public void setTicketPrice(Double ticketPrice) { this.ticketPrice = ticketPrice; }
    public Integer getTotalSeats() { return totalSeats; }
    public void setTotalSeats(Integer totalSeats) { this.totalSeats = totalSeats; }
    public Integer getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(Integer availableSeats) { this.availableSeats = availableSeats; }

    @Override
    public String toString()
    {
        return "Match: " + matchType + " | " + teamA + " vs " + teamB + " | Ticket Price: " + ticketPrice + " | Available Seats: " + availableSeats;
    }
}
