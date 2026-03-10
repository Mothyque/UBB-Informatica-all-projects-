#include "Concert.h"
#include <QtWidgets/QApplication>

#include "gui.h"
#include "repo.h"
#include "service.h"
#include "tests.h"

int main(int argc, char *argv[])
{
    testAll();
    QApplication a(argc, argv);
    Repository repo("concerte.txt");
    Service service(repo);
    Gui gui(service);
    gui.show();
    return a.exec();
}
