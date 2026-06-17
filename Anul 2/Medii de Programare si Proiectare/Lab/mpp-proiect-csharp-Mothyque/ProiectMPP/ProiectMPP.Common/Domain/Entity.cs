using System;

using System.Collections.Generic;

namespace ProiectMPP.Common.Domain
{
    [Serializable]
    public class Entity<TID>
    {
        public TID Id { get; set; }
        public override bool Equals(object obj)
        {
            if (obj == null || GetType() != obj.GetType())
                return false;

            Entity<TID> other = (Entity<TID>)obj;
            return EqualityComparer<TID>.Default.Equals(Id, other.Id);
        }
        public override int GetHashCode()
        {
            return EqualityComparer<TID>.Default.GetHashCode(Id);
        }
    }
}