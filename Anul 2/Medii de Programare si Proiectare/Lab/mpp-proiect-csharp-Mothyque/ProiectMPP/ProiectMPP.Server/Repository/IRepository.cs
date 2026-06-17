using ProiectMPP.Common.Domain;
using System;
using System.Collections.Generic;
using System.Text;

namespace ProiectMPP.Server.Repository
{
    public interface IRepository<ID, E> where E : Entity<ID>
    {
        E FindOne(ID id);
        IEnumerable<E> FindAll();
        E Save(E entity);
        E Delete(ID id);
        E Update(E entity);
        int Size();
        void BeginTransaction();
        void CommitTransaction();
        void RollbackTransaction();
    }
}
