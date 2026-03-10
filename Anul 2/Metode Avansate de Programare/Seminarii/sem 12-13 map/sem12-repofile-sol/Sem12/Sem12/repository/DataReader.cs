using Sem12.Repository;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Sem12.Model;

namespace Sem12.Repository
{
    class DataReader
    {
        public static List<T> ReadData<T>(string fileName, CreateEntity<T> createEntity)
        {
            List<T> list = new List<T>();
            using (StreamReader sr = new StreamReader(fileName))
            {
                string s;
                while ((s = sr.ReadLine()) != null)
                {
                    T entity= createEntity(s);
                    list.Add(entity);
                }
            }
            return list;
        }
        public static Angajat CreateAngajat(string line)
        {
            string[] fields = line.Split(','); // new char[] { ',' } 
            Angajat angajat = new Angajat()
            {

                ID = fields[0],
                Nume = fields[1],
                VenitPeOra = Double.Parse(fields[2]),
                Nivel = (KnowledgeLevel) Enum.Parse(typeof(KnowledgeLevel), fields[3])
            };
            return angajat;
        }

        public static Pontaj CreatePontaj(string line)
        {
            //TODO
            List<Angajat> angajati = DataReader.ReadData<Angajat>("..\\..\\..\\data\\angajati.txt", DataReader.CreateAngajat);
            List<Sarcina> sarcini = DataReader.ReadData<Sarcina>
                ("..\\..\\..\\data\\sarcini.txt", DataReader.CreateSarcina);
            string[] fields = line.Split(','); // new char[] { ',' } 
            Angajat a = angajati.Find(x => x.ID.Equals(fields[0]));
            Sarcina s = sarcini.Find(x => x.ID.Equals(fields[1]));
            String id = fields[0] + "," + fields[1];
            Pontaj pontaj = new Pontaj()
            {
                ID = id,
                Angajat = a,
                Sarcina = s,
                Date=DateTime.ParseExact(fields[2], "d/M/yyyy", System.Globalization.CultureInfo.InvariantCulture)
            };
            return pontaj; 
            
        }

        public static Sarcina CreateSarcina(string line)
        {
            string[] fields = line.Split(','); // new char[] { ',' } 
            Sarcina sarcina = new Sarcina()
            {

                ID = fields[0],
                TipDificultate = (Dificultate)Enum.Parse(typeof(Dificultate), fields[1]),
                NrOreEstimate = Int32.Parse(fields[2])
            };
            return sarcina;
        }
    }
}

