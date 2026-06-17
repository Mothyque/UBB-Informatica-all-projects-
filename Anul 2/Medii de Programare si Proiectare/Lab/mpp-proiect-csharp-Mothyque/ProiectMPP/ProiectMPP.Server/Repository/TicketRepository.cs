using ProiectMPP.Common.Domain;
using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SQLite;

namespace ProiectMPP.Server.Repository
{
    public class TicketRepository : DBRepository<int, Ticket>, ITicketRepository
    {
        public TicketRepository(string connectionString) : base(connectionString)
        {
        }

        protected override Ticket ExtractEntity(IDataReader reader)
        {
            int id = reader.GetInt32(reader.GetOrdinal("id"));
            int clientId = reader.GetInt32(reader.GetOrdinal("clientId"));
            int matchId = reader.GetInt32(reader.GetOrdinal("matchId"));
            string seatLocation = reader.GetString(reader.GetOrdinal("seatLocation"));
            Ticket ticket = new Ticket
            {
                ClientId = clientId,
                MatchId = matchId,
                SeatLocation = seatLocation
            };
            ticket.Id = id;
            return ticket;
        }

        protected override string GetDeleteSQL()
        {
            return "DELETE FROM tickets WHERE id = @id";
        }

        protected override string GetFindAllSQL()
        {
            return "SELECT * FROM tickets";
        }

        protected override string GetFindOneSQL()
        {
            return "SELECT * FROM tickets WHERE id = @id";
        }

        protected override string GetSaveSQL()
        {
            return "INSERT INTO tickets (clientId, matchId, seatLocation) VALUES (@clientId, @matchId, @seatLocation)";
        }

        protected override string GetSizeSQL()
        {
            return "SELECT COUNT(*) FROM tickets";
        }

        protected override string GetUpdateSQL()
        {
            return "UPDATE tickets SET clientId = @clientId, matchId = @matchId, seatLocation = @seatLocation WHERE id = @id";
        }

        protected override void SetDeleteParameters(IDbCommand command, int id)
        {
            AddParameter(command, "@id", id);
        }

        protected override void SetSaveParameters(IDbCommand command, Ticket entity)
        {
            AddParameter(command, "@clientId", entity.ClientId);
            AddParameter(command, "@matchId", entity.MatchId);
            AddParameter(command, "@seatLocation", entity.SeatLocation);
        }

        protected override void SetUpdateParameters(IDbCommand command, Ticket entity)
        {
            AddParameter(command, "@clientId", entity.ClientId);
            AddParameter(command, "@matchId", entity.MatchId);
            AddParameter(command, "@seatLocation", entity.SeatLocation);
            AddParameter(command, "@id", entity.Id);
        }

        public int GetNumberOfTicketsAtMatch(int clientId, int matchId)
        {
            string sql = "SELECT COUNT(*) FROM tickets WHERE clientId = @clientId AND matchId = @matchId";
            try
            {
                using (var command = GetConnection().CreateCommand())
                {
                    command.CommandText = sql;
                    if (transaction != null) command.Transaction = transaction;
                    AddParameter(command, "@clientId", clientId);
                    AddParameter(command, "@matchId", matchId);
                    return Convert.ToInt32(command.ExecuteScalar());
                }
            }
            catch (Exception ex)
            {
                log.Error("Error executing GetNumberOfTicketsAtMatch", ex);
                return 0;
            }
        }

        public int GetTicketId(int clientId, int matchId)
        {
            string sql = "SELECT id FROM tickets WHERE clientId = @clientId AND matchId = @matchId LIMIT 1";
            try
            {
                using (var command = GetConnection().CreateCommand())
                {
                    command.CommandText = sql;
                    if (transaction != null) command.Transaction = transaction;
                    AddParameter(command, "@clientId", clientId);
                    AddParameter(command, "@matchId", matchId);
                    var result = command.ExecuteScalar();
                    return result != null ? Convert.ToInt32(result) : -1;
                }
            }
            catch (Exception ex)
            {
                log.Error("Error executing GetTicketId", ex);
                return -1;
            }
        }

        public IEnumerable<ClientTicketDTO> GetClientTicketsByCriteria(string name, string address)
        {
            List<ClientTicketDTO> results = new List<ClientTicketDTO>();
            string sql = @"SELECT c.id as cid, c.name, c.address, 
                           m.id AS mid, m.teamA, m.teamB, m.matchType, m.ticketPrice, m.totalSeats, m.availableSeats, 
                           COUNT(t.id) as ticketCount 
                           FROM clients c 
                           JOIN tickets t ON c.id = t.clientId 
                           JOIN matches m ON t.matchId = m.id 
                           WHERE c.name LIKE @name AND c.address LIKE @address 
                           GROUP BY c.id, m.id 
                           HAVING ticketCount > 0";

            try
            {
                using (var command = GetConnection().CreateCommand())
                {
                    command.CommandText = sql;
                    if (transaction != null) command.Transaction = transaction;
                    AddParameter(command, "@name", "%" + name + "%");
                    AddParameter(command, "@address", "%" + address + "%");

                    using (var reader = command.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            Client client = new Client(reader.GetString(reader.GetOrdinal("name")), reader.GetString(reader.GetOrdinal("address")))
                            {
                                Id = reader.GetInt32(reader.GetOrdinal("cid"))
                            };

                            Match match = new Match()
                            {
                                TeamA = reader.GetString(reader.GetOrdinal("teamA")),
                                TeamB = reader.GetString(reader.GetOrdinal("teamB")),
                                MatchType = reader.GetString(reader.GetOrdinal("matchType")),
                                TicketPrice = reader.GetDouble(reader.GetOrdinal("ticketPrice")),
                                TotalSeats = reader.GetInt32(reader.GetOrdinal("totalSeats")),
                                AvailableSeats = reader.GetInt32(reader.GetOrdinal("availableSeats")),
                                Id = reader.GetInt32(reader.GetOrdinal("mid"))
                            };

                            int count = reader.GetInt32(reader.GetOrdinal("ticketCount"));
                            results.Add(new ClientTicketDTO(client, match, count));
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                log.Error("Error executing GetClientTicketsByCriteria", ex);
            }
            return results;
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