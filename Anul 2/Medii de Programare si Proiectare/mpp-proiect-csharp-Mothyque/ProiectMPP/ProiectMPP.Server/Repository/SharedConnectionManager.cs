using System;
using System.Data;
using System.Data.SQLite;
using log4net;

namespace ProiectMPP.Server.Repository
{
    /// <summary>
    /// Manages a shared database connection and transaction for a given thread.
    /// Ensures that all repositories running on the same thread use the same connection during transactions.
    /// </summary>
    public static class SharedConnectionManager
    {
        private static readonly ILog log = LogManager.GetLogger(typeof(SharedConnectionManager));

        [ThreadStatic]
        private static IDbConnection threadConnection;

        [ThreadStatic]
        private static IDbTransaction threadTransaction;

        [ThreadStatic]
        private static string threadConnectionString;

        /// <summary>
        /// Gets or creates a connection for the current thread.
        /// </summary>
        public static IDbConnection GetConnection(string connectionString)
        {
            if (threadTransaction != null && threadConnection != null && threadConnection.State == ConnectionState.Open)
            {
                return threadConnection;
            }

            if (threadConnection == null || threadConnection.State == ConnectionState.Closed)
            {
                try
                {
                    threadConnection = new SQLiteConnection(connectionString);
                    threadConnection.Open();
                    threadConnectionString = connectionString;
                }
                catch (Exception ex)
                {
                    throw;
                }
            }

            return threadConnection;
        }

        /// <summary>
        /// Begins a transaction on the shared connection for the current thread.
        /// </summary>
        public static void BeginTransaction(string connectionString)
        {
            if (threadTransaction == null)
            {
                if (threadConnection != null && threadConnection.State != ConnectionState.Closed)
                {
                    try
                    {
                        threadConnection.Close();
                        threadConnection.Dispose();
                    }
                    catch { }
                }

                threadConnection = new SQLiteConnection(connectionString);
                threadConnection.Open();
                threadConnectionString = connectionString;

                threadTransaction = threadConnection.BeginTransaction();
            }
        }

        /// <summary>
        /// Commits the transaction for the current thread.
        /// </summary>
        public static void CommitTransaction()
        {
            if (threadTransaction != null)
            {
                try
                {
                    threadTransaction.Commit();
                }
                catch (Exception ex)
                {
                    throw;
                }
                finally
                {
                    CleanupTransaction();
                }
            }
        }

        /// <summary>
        /// Rolls back the transaction for the current thread.
        /// </summary>
        public static void RollbackTransaction()
        {
            if (threadTransaction != null)
            {
                try
                {
                    threadTransaction.Rollback();
                    log.Debug("SharedConnectionManager: Transaction rolled back");
                }
                catch (Exception ex)
                {
                    log.Error("SharedConnectionManager: Error rolling back transaction", ex);
                }
                finally
                {
                    CleanupTransaction();
                }
            }
        }

        /// <summary>
        /// Checks if there's an active transaction on the current thread.
        /// </summary>
        public static bool HasActiveTransaction()
        {
            return threadTransaction != null;
        }

        /// <summary>
        /// Gets the current transaction if one exists.
        /// </summary>
        public static IDbTransaction GetCurrentTransaction()
        {
            return threadTransaction;
        }

        /// <summary>
        /// Cleanup transaction resources.
        /// </summary>
        private static void CleanupTransaction()
        {
            try
            {
                if (threadTransaction != null)
                {
                    threadTransaction.Dispose();
                    threadTransaction = null;
                }

                if (threadConnection != null && threadConnection.State != ConnectionState.Closed)
                {
                    threadConnection.Close();
                }

                if (threadConnection != null)
                {
                    threadConnection.Dispose();
                    threadConnection = null;
                }

                threadConnectionString = null;
                log.Debug("SharedConnectionManager: Transaction cleanup completed");
            }
            catch (Exception ex)
            {
                log.Error("SharedConnectionManager: Error during cleanup", ex);
            }
        }

        /// <summary>
        /// Closes the connection for the current thread.
        /// </summary>
        public static void CloseConnection()
        {
            try
            {
                if (threadConnection != null && threadConnection.State != ConnectionState.Closed)
                {
                    threadConnection.Close();
                    threadConnection.Dispose();
                    log.Info("SharedConnectionManager: Connection closed");
                }
                threadConnection = null;
            }
            catch (Exception ex)
            {
                log.Error("SharedConnectionManager: Error closing connection", ex);
            }
        }
    }
}
