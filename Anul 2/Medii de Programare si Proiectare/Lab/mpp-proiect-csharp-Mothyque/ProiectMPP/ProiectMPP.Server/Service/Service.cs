using ProiectMPP.Common.Domain;
using ProiectMPP.Common.Utils;
using ProiectMPP.Server.Repository;
using System.Collections.Generic;

namespace ProiectMPP.Server.Service
{
    public class Service<TId, TE> where TE : Entity<TId>
    {
        protected readonly IRepository<TId, TE> repository;
        private readonly List<IObserver> observers = new List<IObserver>();

        public Service(IRepository<TId, TE> repository)
        {
            this.repository = repository;
        }

        public virtual void Add(TE entity)
        {
            repository.Save(entity);
            UpdateObservers();
        }

        public virtual void Delete(TId id)
        {
            repository.Delete(id);
            UpdateObservers();
        }

        public virtual void Update(TE entity)
        {
            repository.Update(entity);
            UpdateObservers();
        }

        public virtual TE FindOne(TId id)
        {
            return repository.FindOne(id);
        }

        public virtual IEnumerable<TE> FindAll()
        {
            return repository.FindAll();
        }

        public virtual int Size()
        {
            return repository.Size();
        }

        public void BeginTransaction()
        {
            repository.BeginTransaction();
        }

        public void CommitTransaction()
        {
            repository.CommitTransaction();
        }

        public void RollbackTransaction()
        {
            repository.RollbackTransaction();
        }

        public void AddObserver(IObserver observer)
        {
            observers.Add(observer);
        }

        public void UpdateObservers()
        {
            foreach (var observer in observers)
            {
                observer.Update();
            }
        }
    }
}