#include "Melodii2.h"
#include <QtWidgets/QApplication>
#include "tests.h"
#include "Gui.h"

int main(int argc, char *argv[])
{
    testAll();
    QApplication app(argc, argv);
    Repository repo("melodii.txt");
    Service service(repo);
    Gui gui(service);
    gui.show();
    return app.exec();
}
