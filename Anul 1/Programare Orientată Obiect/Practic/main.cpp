#include "Practic.h"
#include <QtWidgets/QApplication>
#include "tests.h"
#include "Gui.h"
int main(int argc, char *argv[])
{
    /* Ruleaza aplicatia */
    testAll();
    QApplication app(argc, argv);
    Repository repo("studenti.txt");
    Service service(repo);
    Gui gui(service);
    gui.show();
    return app.exec();
}
