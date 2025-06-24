#include "tests.h"
#include "ui.h"
#include "inchiriere.h"

using std::cout;
using std::cin;

int main()
{
	/* Ruleaza aplicatia */
	ruleazaTeste();

	cout << "Alege tipul de repo: 1 - normal, 2 - probabilistic\n";
	int optiune;
	cin >> optiune;
	Repo* repo;
	if (optiune == 1)
	{
		repo = new Repository("filme.txt");
	}
	else
	{
		double probabilitate;
		cout << "Introduceti probabilitatea de eroare (0-1): ";
		cin >> probabilitate;
		repo = new RepoProbabilistic(probabilitate);
	}

	Validator validator;
	Service service{ *repo, validator };
	CosInchiriere cos(validator, *repo);
	Console console{ service, cos };
	console.runMeniu();
	delete repo;
	return 0;
}