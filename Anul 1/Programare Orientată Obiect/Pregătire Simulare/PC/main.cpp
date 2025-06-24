#include "PC.h"
#include <QtWidgets/QApplication>

#include "teste.h"
#include "gui.h"

int main(int argc, char *argv[])
{
	testAll();
    QApplication a(argc, argv);
    Repository repo("procesoare.txt", "placi_de_baza.txt");
    Service service(repo);
    Gui gui(service);
    gui.show();
    return a.exec();
}
