using ProiectMPP.Common.Domain;
using ProiectMPP.Server.Repository;
using System;
using System.Collections.Generic;
using System.Text;

namespace ProiectMPP.Server.Service
{
    public class MatchService : Service<int, Match>
    {
        public MatchService(IRepository<int, Match> repository) : base(repository)
        {
        }
    }
}
