#include "Lab1011.h"
#include <QtWidgets/QApplication>

#include "gui.h"
#include "repo.h"
#include "service.h"
#include "tests.h"
#include "validator.h"
#include <inchiriere.h>

#include "cosReadOnlyGui.h"

int main(int argc, char *argv[])
{
	ruleazaTeste();
    QApplication a(argc, argv);
	Repository repo("filme.txt");
	Validator validator;
	Service service{ repo, validator };
	CosInchiriere cos{ validator, repo };
	CosReadOnlyGui cosReadOnlyGui{ cos };
	Gui gui{ service, cos , cosReadOnlyGui };
	gui.setWindowTitle("Filme");
	gui.show();
    return a.exec();
    
}
