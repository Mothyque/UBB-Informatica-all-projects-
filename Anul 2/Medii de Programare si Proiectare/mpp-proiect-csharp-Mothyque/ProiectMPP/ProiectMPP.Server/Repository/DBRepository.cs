using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SQLite;
using log4net;
using ProiectMPP.Common.Domain;

namespace ProiectMPP.Server.Repository
{
    public abstract class DBRepository<TID, TE> : IRepository<TID, TE> where TE : Entity<TID>
    {
        protected readonly string connectionString;
        protected static readonly ILog log = LogManager.GetLogger(typeof(DBRepository<TID, TE>));
        protected IDbConnection connection;
        protected IDbTransaction transaction;

        public DBRepository(string connectionString)
        {
            this.connectionString = connectionString;
        }

        protected IDbConnection GetConnection()
        {
            return SharedConnectionManager.GetConnection(connectionString);
        }

        public void BeginTransaction()
        {
            SharedConnectionManager.BeginTransaction(connectionString);
            transaction = SharedConnectionManager.GetCurrentTransaction();
        }

        public void CommitTransaction()
        {
            try
            {
                SharedConnectionManager.CommitTransaction();
                transaction = null;
            }
            catch (Exception ex)
            {
                RollbackTransaction();
                throw;
            }
        }

        public void RollbackTransaction()
        {
            try
            {
                SharedConnectionManager.RollbackTransaction();
                transaction = null;
            }
            catch (Exception ex)
            {
            }
        }

        public void CloseConnection()
        {
            if (connection != null && connection.State != ConnectionState.Closed)
            {
                connection.Close();
                connection.Dispose();
                connection = null;
            }
        }

        public TE FindOne(TID id)
        {
            string sql = GetFindOneSQL();
            try
            {
                using (var command = GetConnection().CreateCommand())
                {
                    command.CommandText = sql;
                    if (transaction != null) command.Transaction = transaction;
                    SetDeleteParameters(command, id);
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
                throw;
            }
            return null;
        }

        public IEnumerable<TE> FindAll()
        {
            List<TE> entities = new List<TE>();
            string sql = GetFindAllSQL();
            try
            {
                using (var command = GetConnection().CreateCommand())
                {
                    command.CommandText = sql;
                    if (transaction != null) command.Transaction = transaction;
                    using (var reader = command.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            entities.Add(ExtractEntity(reader));
                        }
                    }
                }
                return entities;
            }
            catch (Exception ex)
            {
                throw;
            }
        }

        public TE Save(TE entity)
        {
            string sql = GetSaveSQL();
            try
            {
                using (var command = GetConnection().CreateCommand())
                {
                    command.CommandText = sql;
                    if (transaction != null) command.Transaction = transaction;
                    SetSaveParameters(command, entity);
                    command.ExecuteNonQuery();
                    return entity;
                }
            }
            catch (Exception ex)
            {
                throw;
            }
        }

        public TE Delete(TID id)
        {
            TE entityToDelete = FindOne(id);
            if (entityToDelete != null)
            {
                string sql = GetDeleteSQL();
                try
                {
                    using (var command = GetConnection().CreateCommand())
                    {
                        command.CommandText = sql;
                        if (transaction != null) command.Transaction = transaction;
                        SetDeleteParameters(command, id);
                        command.ExecuteNonQuery();
                        return entityToDelete;
                    }
                }
                catch (Exception ex)
                {
                    throw;
                }
            }
            return null;
        }

        public TE Update(TE entity)
        {
            string sql = GetUpdateSQL();
            try
            {
                using (var command = GetConnection().CreateCommand())
                {
                    command.CommandText = sql;
                    if (transaction != null) command.Transaction = transaction;
                    SetUpdateParameters(command, entity);
                    int rowsAffected = command.ExecuteNonQuery();
                    return rowsAffected > 0 ? entity : null;
                }
            }
            catch (Exception ex)
            {
                throw;
            }
        }

        public int Size()
        {
            string sql = GetSizeSQL();
            try
            {
                using (var command = GetConnection().CreateCommand())
                {
                    command.CommandText = sql;
                    if (transaction != null) command.Transaction = transaction;
                    return Convert.ToInt32(command.ExecuteScalar());
                }
            }
            catch (Exception ex)
            {
                throw;
            }
        }

        protected abstract string GetFindAllSQL();
        protected abstract string GetFindOneSQL();
        protected abstract string GetSaveSQL();
        protected abstract string GetDeleteSQL();
        protected abstract string GetUpdateSQL();
        protected abstract string GetSizeSQL();
        protected abstract TE ExtractEntity(IDataReader reader);
        protected abstract void SetSaveParameters(IDbCommand command, TE entity);
        protected abstract void SetDeleteParameters(IDbCommand command, TID id);
        protected abstract void SetUpdateParameters(IDbCommand command, TE entity);
    }
}