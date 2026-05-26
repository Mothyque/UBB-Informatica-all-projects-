using System.Collections.Generic;
using ProiectMPP.Common.Domain;
using ProiectMPP.Common.Utils;

namespace ProiectMPP.Common.Networking
{
    public interface IServices
    {
        void Login(User user, IObserver client);
        void Logout(User user, IObserver client);
        Match[] GetAllMatches();
        void BuyTickets(Match match, string clientName, string clientAddress, int numberOfTickets);
        IList<ClientTicketDTO> FilterTickets(string name, string address);
        void UpdateTickets(Match match, Client client, int oldQuantity, int newQuantity);
    }
}