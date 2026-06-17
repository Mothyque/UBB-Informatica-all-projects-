using ProiectMPP.Common.Domain;
using System;
using System.Collections.Generic;
using System.Data;

namespace ProiectMPP.Server.Repository
{
    public class ClientRepository : DBRepository<int, Client>, IClientRepository
    {
        public ClientRepository(string connectionString) : base(connectionString) { }

        protected override string GetFindAllSQL() => "SELECT * FROM clients";
        protected override string GetFindOneSQL() => "SELECT * FROM clients WHERE id = @id";
        protected override string GetSaveSQL() => "INSERT INTO clients (name, address) VALUES (@name, @address)";
        protected override string GetDeleteSQL() => "DELETE FROM clients WHERE id = @id";
        protected override string GetUpdateSQL() => "UPDATE clients SET name = @name, address = @address WHERE id = @id";
        protected override string GetSizeSQL() => "SELECT COUNT(*) FROM clients";

        protected override Client ExtractEntity(IDataReader reader)
        {
            int id = reader.GetInt32(reader.GetOrdinal("id"));
            string name = reader.GetString(reader.GetOrdinal("name"));
            string address = reader.GetString(reader.GetOrdinal("address"));
            Client client = new Client { Name = name, Address = address };
            client.Id = id;
            return client;
        }

        protected override void SetSaveParameters(IDbCommand command, Client entity)
        {
            AddParameter(command, "@name", entity.Name);
            AddParameter(command, "@address", entity.Address);
        }

        protected override void SetUpdateParameters(IDbCommand command, Client entity)
        {
            AddParameter(command, "@name", entity.Name);
            AddParameter(command, "@address", entity.Address);
            AddParameter(command, "@id", entity.Id);
        }

        protected override void SetDeleteParameters(IDbCommand command, int id)
        {
            AddParameter(command, "@id", id);
        }

        public Client FindClientByNameAndAddress(string name, string address)
        {
            try
            {
                using (var command = GetConnection().CreateCommand())
                {
                    command.CommandText = "SELECT * FROM clients WHERE name = @name AND address = @address";
                    if (transaction != null) command.Transaction = transaction;
                    AddParameter(command, "@name", name);
                    AddParameter(command, "@address", address);
                    using (var reader = command.ExecuteReader())
                    {
                        if (reader.Read()) return ExtractEntity(reader);
                    }
                }
            }
            catch (Exception ex)
            {
                log.Error($"Error finding client by name and address: {name}, {address}", ex);
            }
            return null;
        }

        public List<Client> FindClientsByNameAndAddress(string name, string address)
        {
            List<Client> clients = new List<Client>();
            try
            {
                using (var command = GetConnection().CreateCommand())
                {
                    command.CommandText = "SELECT * FROM clients WHERE name = @name AND address = @address";
                    if (transaction != null) command.Transaction = transaction;
                    AddParameter(command, "@name", name);
                    AddParameter(command, "@address", address);
                    using (var reader = command.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            clients.Add(ExtractEntity(reader));
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                log.Error($"Error finding clients by name and address: {name}, {address}", ex);
            }
            return clients;
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