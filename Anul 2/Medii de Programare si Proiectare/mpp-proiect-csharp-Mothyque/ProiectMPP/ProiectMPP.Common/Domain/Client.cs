using System;

namespace ProiectMPP.Common.Domain
{
    [Serializable]
    public class Client : Entity<int>
    {
        public string Name { get; set; }
        public string Address { get; set; }

        public Client() { }

        public Client(string name, string address)
        {
            Name = name;
            Address = address;
        }
    }
}