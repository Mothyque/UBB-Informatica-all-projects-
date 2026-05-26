using ProiectMPP.Common.Domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Net.Http.Json;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;
using System.Diagnostics;

namespace ProiectMPP.Client.Rest
{
    public class CSharpTest
    {
        private static TestOutputCapture _capture = new TestOutputCapture();

        public static async Task RunTests()
        {
            var baseUrl = "http://localhost:8080/api/matches";

            var options = new JsonSerializerOptions { PropertyNameCaseInsensitive = true };

            var handler = new LoggingHandler(new HttpClientHandler());
            using var client = new HttpClient(handler);

            LogOutput("Starting C# REST Tests using existing Match entity...\n");

            try
            {
                var newMatch = new Match
                {
                    TeamA = "CSharp_Lions",
                    TeamB = "Java_Tigers",
                    MatchType = "CrossPlatform",
                    TicketPrice = 50.0,
                    TotalSeats = 200,
                    AvailableSeats = 200
                };

                var postResponse = await client.PostAsJsonAsync(baseUrl, newMatch);
                int newId = await postResponse.Content.ReadFromJsonAsync<int>();
                LogOutput($"Match created with ID: {newId}");

                var found = await client.GetFromJsonAsync<Match>($"{baseUrl}/{newId}", options);
                LogOutput($"Found: {found.TeamA} vs {found.TeamB} [Price: {found.TicketPrice}]");

                var all = await client.GetFromJsonAsync<List<Match>>(baseUrl, options);
                LogOutput($"Total matches in DB: {all.Count}");

                var filtered = await client.GetFromJsonAsync<List<Match>>($"{baseUrl}?matchType=CrossPlatform", options);
                LogOutput($"Filtered count: {filtered.Count}");

                found.TicketPrice = 10.99;
                await client.PutAsJsonAsync($"{baseUrl}/{newId}", found);
                LogOutput("Match modified successfully.");

                await client.DeleteAsync($"{baseUrl}/{newId}");
                LogOutput("Match deleted successfully.");

                LogOutput("\nC# REST Tests finished.");
            }
            catch (Exception ex)
            {
                LogOutput($"ERROR during tests: {ex.Message}");
                LogOutput($"Stack Trace: {ex.StackTrace}");
                throw;
            }
        }

        private static void LogOutput(string message)
        {
            Debug.WriteLine(message);
            Trace.WriteLine(message);
            _capture.WriteLine(message);
        }

        public static string GetTestOutput()
        {
            return _capture.GetOutput();
        }
    }
}
