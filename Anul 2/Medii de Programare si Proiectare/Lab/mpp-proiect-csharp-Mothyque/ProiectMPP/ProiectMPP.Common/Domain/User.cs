using System;

namespace ProiectMPP.Common.Domain
{
    [Serializable]
    public class User : Entity<int>
    {
        public string Username { get; set; }
        public string Password { get; set; }

        public User() { }

        public User(string username, string password)
        {
            Username = username;
            Password = password;
        }

        public override string ToString()
        {
            return $"User{{username='{Username}', password='{Password}'}}";
        }
    }
}
