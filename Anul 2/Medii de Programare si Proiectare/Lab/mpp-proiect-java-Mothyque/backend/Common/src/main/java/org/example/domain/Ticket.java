package org.example.domain;

public class Ticket extends Entity<Integer>
{
    private Integer clientId;
    private Integer matchId;
    private String seatLocation;

    public Ticket(Integer clientId, Integer matchId, String seatLocation)
    {
        this.clientId = clientId;
        this.matchId = matchId;
        this.seatLocation = seatLocation;
    }

    public Ticket() {}
    public Integer getClientId() { return clientId; }
    public void setClientId(Integer clientId) { this.clientId = clientId; }
    public Integer getMatchId() { return matchId; }
    public void setMatchId(Integer matchId) { this.matchId = matchId; }
    public String getSeatLocation() { return seatLocation; }
    public void setSeatLocation(String seatLocation) { this.seatLocation = seatLocation; }

    @Override
    public String toString()
    {
        return "Ticket{" +
                "clientId=" + clientId +
                ", matchId=" + matchId +
                ", seatLocation='" + seatLocation + '\'' +
                '}';
    }
}
