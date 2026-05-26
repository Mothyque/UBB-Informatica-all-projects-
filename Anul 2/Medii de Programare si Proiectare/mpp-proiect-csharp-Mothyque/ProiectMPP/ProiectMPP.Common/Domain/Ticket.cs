using System;

namespace ProiectMPP.Common.Domain
{
    [Serializable]
    public class Ticket : Entity<int>
    {
        public int ClientId { get; set; }
        public int MatchId { get; set; }
        public string SeatLocation { get; set; }

        public Ticket(int clientId, int matchId, string seatLocation)
        {
            ClientId = clientId;
            MatchId = matchId;
            SeatLocation = seatLocation;
        }

        public Ticket() { }

        public override string ToString()
        {
            return $"Ticket{{clientId={ClientId}, matchId={MatchId}, seatLocation='{SeatLocation}'}}";
        }
    }
}