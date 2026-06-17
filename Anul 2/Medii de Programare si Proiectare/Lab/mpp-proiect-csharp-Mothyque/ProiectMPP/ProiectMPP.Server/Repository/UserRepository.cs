using ProiectMPP.Common.Domain;
using System;
using System.Data;

namespace ProiectMPP.Server.Repository
{
    public class UserRepository : DBRepository<int, User>, IUserRepository
    {
        public UserRepository(string connectionString) : base(connectionString)
        {
        }

        protected override User ExtractEntity(IDataReader reader)
        {
            int id = reader.GetInt32(reader.GetOrdinal("id"));
            string username = reader.GetString(reader.GetOrdinal("username"));
            string password = reader.GetString(reader.GetOrdinal("password"));
            User user = new User(username, password)
            {
                Id = id
            };
            return user;
        }

        protected override string GetDeleteSQL()
        {
            return "DELETE FROM users WHERE id = @id";
        }

        protected override string GetFindAllSQL()
        {
            return "SELECT * FROM users";
        }

        protected override string GetFindOneSQL()
        {
            return "SELECT * FROM users WHERE id = @id";
        }

        protected override string GetSaveSQL()
        {
            return "INSERT INTO users (username, password) VALUES (@username, @password)";
        }

        protected override string GetSizeSQL()
        {
            return "SELECT COUNT(*) FROM users";
        }

        protected override string GetUpdateSQL()
        {
            return "UPDATE users SET username = @username, password = @password WHERE id = @id";
        }

        protected override void SetDeleteParameters(IDbCommand command, int id)
        {
            AddParameter(command, "@id", id);
        }

        protected override void SetSaveParameters(IDbCommand command, User entity)
        {
            AddParameter(command, "@username", entity.Username);
            AddParameter(command, "@password", entity.Password);
        }

        protected override void SetUpdateParameters(IDbCommand command, User entity)
        {
            AddParameter(command, "@username", entity.Username);
            AddParameter(command, "@password", entity.Password);
            AddParameter(command, "@id", entity.Id);
        }

        public User FindByUsername(string username)
        {
            string sql = "SELECT * FROM users WHERE username = @username";
            try
            {
                using (var command = GetConnection().CreateCommand())
                {
                    command.CommandText = sql;
                    if (transaction != null) command.Transaction = transaction;
                    AddParameter(command, "@username", username);

                    using (var reader = command.ExecuteReader())
                    {
                        if (reader.Read())
                        {
                            return ExtractEntity(reader);
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                log.Error($"Error finding user by username: {username}", ex);
            }
            return null;
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