using Sem12.Repository;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Sem12.Model;
using Sem12.Model.Validator;


namespace Sem12.Repository
{
    class AngajatInFileRepository : InFileRepository<string, Angajat>
    {

        public AngajatInFileRepository(IValidator<Angajat> vali, string fileName) : base(vali, fileName, DataReader.CreateAngajat)           
        {
           
        }

    }
}
