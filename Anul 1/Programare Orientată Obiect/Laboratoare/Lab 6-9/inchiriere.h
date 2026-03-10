#ifndef INCHIRIERE_H
#define INCHIRIERE_H
#include "film.h"
#include <vector>

#include "repo.h"
#include "validator.h"

using std::vector;

class CosInchiriere  
{  
private:  
   vector<Film> filme;  
   Validator& validator;  
   Repo& repo;  
public:  
   CosInchiriere(Validator& validator, Repo& repo) : validator{ validator }, repo{ repo } {}  
   ~CosInchiriere() = default;  
   void adaugaFilm(const string& titlu, const string& regizor);  
   void golesteCos();  
   const vector<Film>& getFilme() const;  
   int getNrFilme() const;
   void genereazaCos(int nr);
   void exporta(const string& numeFisier) const;
};
#endif
