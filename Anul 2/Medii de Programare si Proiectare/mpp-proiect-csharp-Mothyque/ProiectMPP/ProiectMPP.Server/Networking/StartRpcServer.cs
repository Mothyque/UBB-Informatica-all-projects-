using ProiectMPP.Common.Networking;
using ProiectMPP.Server.Networking;
using ProiectMPP.Server.Repository;
using ProiectMPP.Server.Service;
using System;
using System.Collections.Generic;
using System.IO;

namespace ProiectMPP.Server
{
    public class StartRpcServer
    {
        private static int defaultPort = 5555;

        public static void Main(string[] args)
        {
            IDictionary<string, string> serverProps = new Dictionary<string, string>();
            
            // Try multiple locations for db.properties
            string[] possiblePaths = new[]
            {
                "db.properties",
                "ProiectMPP.Server/db.properties",
                Path.Combine(Directory.GetCurrentDirectory(), "db.properties"),
                Path.Combine(Directory.GetCurrentDirectory(), "ProiectMPP.Server", "db.properties"),
                Path.Combine(AppDomain.CurrentDomain.BaseDirectory, "db.properties"),
                Path.Combine(AppDomain.CurrentDomain.BaseDirectory, "..", "..", "ProiectMPP.Server", "db.properties"),
            };

            string propsPath = null;
            foreach (var path in possiblePaths)
            {
                if (File.Exists(path))
                {
                    propsPath = path;
                    break;
                }
            }

            if (propsPath == null)
            {
                Console.Error.WriteLine("Configuration file not found: db.properties");
                return;
            }

            try
            {
                foreach (var line in File.ReadAllLines(propsPath))
                {
                    if (string.IsNullOrWhiteSpace(line) || line.StartsWith("#"))
                        continue;
                    
                    var parts = line.Split('=');
                    if (parts.Length >= 2)
                    {
                        string key = parts[0].Trim();
                        string value = line.Substring(line.IndexOf('=') + 1).Trim();
                        serverProps[key] = value;
                    }
                }
            }
            catch (IOException e)
            {
                Console.Error.WriteLine("Error reading properties: " + e.Message);
                return;
            }

            string url = serverProps.ContainsKey("url") ? serverProps["url"] : "";

            try
            {
                // Enable WAL mode for better concurrent access
                using (var connection = new System.Data.SQLite.SQLiteConnection(url))
                {
                    connection.Open();
                    using (var command = connection.CreateCommand())
                        {
                            command.CommandText = "PRAGMA journal_mode=WAL;";
                            command.ExecuteNonQuery();
                        }
                        using (var command = connection.CreateCommand())
                        {
                            command.CommandText = "PRAGMA synchronous=NORMAL;";
                            command.ExecuteNonQuery();
                        }
                    connection.Close();
                }

                IUserRepository userRepo = new UserRepository(url);
                IMatchRepository matchRepo = new MatchRepository(url);
                IClientRepository clientRepo = new ClientRepository(url);
                ITicketRepository ticketRepo = new TicketRepository(url);

                UserService userService = new UserService(userRepo);
                MatchService matchService = new MatchService(matchRepo);
                ClientService clientService = new ClientService(clientRepo);
                TicketService ticketService = new TicketService(ticketRepo, matchRepo);

                IServices serverImpl = new ServicesImpl(userService, matchService, clientService, ticketService);

                AbstractServer server = new RpcConcurrentServer(defaultPort, serverImpl);

                server.Start();
            }
            catch (Exception e)
            {
                Console.Error.WriteLine("Error starting the server: " + e.Message);
                Console.Error.WriteLine(e.StackTrace);
            }
        }
    }
}
