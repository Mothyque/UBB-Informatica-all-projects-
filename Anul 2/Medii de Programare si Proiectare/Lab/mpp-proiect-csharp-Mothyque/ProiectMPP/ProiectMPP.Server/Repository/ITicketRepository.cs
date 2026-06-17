using ProiectMPP.Common.Domain;
using System.Collections.Generic;

namespace ProiectMPP.Server.Repository
{
    public interface ITicketRepository : IRepository<int, Ticket>
    {
        int GetNumberOfTicketsAtMatch(int clientId, int matchId);

        int GetTicketId(int clientId, int matchId);

        IEnumerable<ClientTicketDTO> GetClientTicketsByCriteria(string name, string address);
    }
}