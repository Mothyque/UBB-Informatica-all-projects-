#include "Doctori.h"
#include <QtWidgets/QApplication>

#include "gui.h"
#include "teste.h"

int main(int argc, char *argv[])
{
    testAll();
    QApplication a(argc, argv);
    Repository repo("doctori.txt");
    Service service(repo);
    Gui app(service);
    app.show();
    return a.exec();
}
