#include "Devices.h"
#include <QtWidgets/QApplication>

#include "tests.h"
#include "gui.h"

int main(int argc, char *argv[])
{
    testAll();
    QApplication a(argc, argv);
    Repository repo("deviceuri.txt");
    Service service(repo);
    Gui gui(service);
    gui.show();
    return a.exec();
}
