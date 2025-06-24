#include "Taskuri.h"
#include <QtWidgets/QApplication>
#include "tests.h"

int main(int argc, char *argv[])
{
    testAll();
    QApplication app(argc, argv);
    Taskuri window;
    window.show();
    return app.exec();
}
