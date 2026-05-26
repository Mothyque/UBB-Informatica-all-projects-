using System;
using System.Data;
using ProiectMPP.Common.Domain;

namespace ProiectMPP.Server.Repository
{
    public class MatchRepository : DBRepository<int, Match>, IMatchRepository
    {
        public MatchRepository(string connectionString) : base(connectionString)
        {
        }

        protected override Match ExtractEntity(IDataReader reader)
        {
            int id = reader.GetInt32(reader.GetOrdinal("id"));
            string teamA = reader.GetString(reader.GetOrdinal("teamA"));
            string teamB = reader.GetString(reader.GetOrdinal("teamB"));
            string matchType = reader.GetString(reader.GetOrdinal("matchType"));
            double ticketPrice = reader.GetDouble(reader.GetOrdinal("ticketPrice"));
            int totalSeats = reader.GetInt32(reader.GetOrdinal("totalSeats"));
            int availableSeats = reader.GetInt32(reader.GetOrdinal("availableSeats"));

            Match match = new Match()
            {
                TeamA = teamA,
                TeamB = teamB,
                MatchType = matchType,
                TicketPrice = ticketPrice,
                TotalSeats = totalSeats,
                AvailableSeats = availableSeats
            };
            match.Id = id;
            return match;
        }

        protected override string GetDeleteSQL()
        {
            return "DELETE FROM matches WHERE id = @id";
        }

        protected override string GetFindAllSQL()
        {
            return "SELECT * FROM matches";
        }

        protected override string GetFindOneSQL()
        {
            return "SELECT * FROM matches WHERE id = @id";
        }

        protected override string GetSaveSQL()
        {
            return "INSERT INTO matches (teamA, teamB, matchType, ticketPrice, totalSeats, availableSeats) VALUES (@teamA, @teamB, @matchType, @ticketPrice, @totalSeats, @availableSeats)";
        }

        protected override string GetSizeSQL()
        {
            return "SELECT COUNT(*) FROM matches";
        }

        protected override string GetUpdateSQL()
        {
            return "UPDATE matches SET teamA = @teamA, teamB = @teamB, matchType = @matchType, ticketPrice = @ticketPrice, totalSeats = @totalSeats, availableSeats = @availableSeats WHERE id = @id";
        }

        protected override void SetDeleteParameters(IDbCommand command, int id)
        {
            AddParameter(command, "@id", id);
        }

        protected override void SetSaveParameters(IDbCommand command, Match entity)
        {
            AddParameter(command, "@teamA", entity.TeamA);
            AddParameter(command, "@teamB", entity.TeamB);
            AddParameter(command, "@matchType", entity.MatchType);
            AddParameter(command, "@ticketPrice", entity.TicketPrice);
            AddParameter(command, "@totalSeats", entity.TotalSeats);
            AddParameter(command, "@availableSeats", entity.AvailableSeats);
        }

        protected override void SetUpdateParameters(IDbCommand command, Match entity)
        {
            AddParameter(command, "@teamA", entity.TeamA);
            AddParameter(command, "@teamB", entity.TeamB);
            AddParameter(command, "@matchType", entity.MatchType);
            AddParameter(command, "@ticketPrice", entity.TicketPrice);
            AddParameter(command, "@totalSeats", entity.TotalSeats);
            AddParameter(command, "@availableSeats", entity.AvailableSeats);
            AddParameter(command, "@id", entity.Id);
        }

        private void AddParameter(IDbCommand command, string name, object value)
        {
            var parameter = command.CreateParameter();
            parameter.ParameterName = name;
            parameter.Value = value ?? DBNull.Value;
            command.Parameters.Add(parameter);
        }
    }
}