using ProiectMPP.Domain;
using ProiectMPP.Repository;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Text;

namespace ProiectMPP
{
    internal class Program
    {
        static void Main(string[] args)
        {
            string executablePath = AppDomain.CurrentDomain.BaseDirectory;
            string dbPath = System.IO.Path.Combine(executablePath, "basketball.db");
            if (!System.IO.File.Exists(dbPath))
            {
                Console.WriteLine($"EROARE: Fișierul NU există la adresa: {dbPath}");
                return;
            }

            string connectionString = $"Data Source={dbPath};Version=3;";

            UserRepository userRepository = new UserRepository(connectionString);
            Console.WriteLine("Number of users: " + userRepository.Size());
            Console.WriteLine("User: " + userRepository.FindOne(1)?.Username + "Password: " + userRepository.FindOne(1)?.Password);
        }
    }
}

