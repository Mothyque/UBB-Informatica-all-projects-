using ProiectMPP.Common.Domain;
using System.Collections.Generic;

namespace ProiectMPP.Server.Repository
{
    public interface IClientRepository : IRepository<int, Client>
    {
        List<Client> FindClientsByNameAndAddress(string name, string address);

        Client FindClientByNameAndAddress(string name, string address);
    }
}