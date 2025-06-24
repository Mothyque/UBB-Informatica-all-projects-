#include "Lab12Simulare.h"
#include <QtWidgets/QApplication>

#include "Gui.h"
#include "tests.h"

int main(int argc, char *argv[])
{
    testAll();
    QApplication app(argc, argv);
    Repository repo("echipe.txt");
    Service service(repo);
    Gui gui(service);
    gui.show();
    return app.exec();
}
