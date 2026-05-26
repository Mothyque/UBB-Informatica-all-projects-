
using ProiectMPP.Common.Domain;

namespace ProiectMPP.Server.Repository
{
    public interface IUserRepository : IRepository<int, User>
    {
        User FindByUsername(string username);
    }
}