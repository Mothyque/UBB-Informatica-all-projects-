using System;

namespace ProiectMPP.Common.Domain
{
    [Serializable]
    public class Match : Entity<int>
    {
        public string TeamA { get; set; }
        public string TeamB { get; set; }
        public string MatchType { get; set; }
        public double TicketPrice { get; set; }
        public int TotalSeats { get; set; }
        public int AvailableSeats { get; set; }

        public Match() { }


        public Match(string teamA, string teamB, string matchType, double ticketPrice, int totalSeats, int availableSeats)
        {
            TeamA = teamA;
            TeamB = teamB;
            MatchType = matchType;
            TicketPrice = ticketPrice;
            TotalSeats = totalSeats;
            AvailableSeats = availableSeats;
        }

        public override string ToString()
        {
            string info = $"Match: {MatchType} | {TeamA} vs {TeamB} | Ticket Price: {TicketPrice} | Available Seats: {AvailableSeats}";
            if (AvailableSeats == 0)
            {
                info += " | SOLD OUT";
            }
            return info;
        }
    }
}