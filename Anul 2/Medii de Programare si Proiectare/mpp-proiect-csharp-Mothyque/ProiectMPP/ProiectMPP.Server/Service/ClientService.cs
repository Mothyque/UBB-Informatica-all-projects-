using System.Collections.Generic;
using ProiectMPP.Common.Domain;
using ProiectMPP.Server.Repository;

namespace ProiectMPP.Server.Service
{
    public class ClientService : Service<int, Client>
    {
        private readonly IClientRepository _clientRepository;

        public ClientService(IClientRepository clientRepository)
            : base(clientRepository)
        {
            this._clientRepository = clientRepository;
        }

        public Client FindByNameAndAddress(string name, string address)
        {
            return _clientRepository.FindClientByNameAndAddress(name, address);
        }
    }
}