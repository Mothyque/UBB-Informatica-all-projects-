#include "Gui.h"
#include <QHBoxLayout>

Gui::Gui(Service& service) : service(service)
{
	initGui();
	loadTable(service.afiseazaJocuri());
	connectBtnSignals();
}

void Gui::initGui() 
{
	setWindowTitle("Gaming store");
	QHBoxLayout* layoutMain = new QHBoxLayout;
	QVBoxLayout* layoutDreapta = new QVBoxLayout;
	tableJocuri = new QTableWidget;
	varstaRecomandata = new QLabel;
	btnFiltreaza = new QPushButton("Jocuri recomandate pentru copii");
	btnSorteaza = new QPushButton("Sortare pret");
	btnReload = new QPushButton("Refresh");
	layoutMain->addWidget(tableJocuri);
	layoutDreapta->addWidget(varstaRecomandata);
	layoutDreapta->addWidget(btnFiltreaza);
	layoutDreapta->addWidget(btnSorteaza);
	layoutDreapta->addWidget(btnReload);
	layoutMain->addLayout(layoutDreapta);
	setLayout(layoutMain);
}


void Gui::loadTable(const vector<Joc>& jocuri) const
{
    tableJocuri->clear();
    tableJocuri->setColumnCount(2);
    tableJocuri->setHorizontalHeaderLabels(QStringList() << "Titlu" << "Pret");
    tableJocuri->setRowCount(static_cast<int>(jocuri.size()));

    int row = 0;
    for (const auto& joc : jocuri)
    {
        QTableWidgetItem* itemTitlu = new QTableWidgetItem(QString::fromStdString(joc.getTitlu()));
        QTableWidgetItem* itemPret = new QTableWidgetItem(QString::number(joc.getPret()));

        QColor color = Qt::black;
        if (joc.getPlatforma() == "XBOX")
            color = Qt::green;
        else if (joc.getPlatforma() == "PlayStation")
            color = Qt::blue;
        else if (joc.getPlatforma() == "Nintendo")
            color = Qt::red;

        itemTitlu->setForeground(color);
        itemPret->setForeground(color);

        tableJocuri->setItem(row, 0, itemTitlu);
        tableJocuri->setItem(row, 1, itemPret);

        ++row;
    }
}

void Gui::connectBtnSignals() const
{
	QObject::connect(btnReload, &QPushButton::clicked, [&]()
		{
			loadTable(service.afiseazaJocuri());
		});
	QObject::connect(btnFiltreaza, &QPushButton::clicked, [&]()
		{
			loadTable(service.filtreazaJocuri());
		});
	QObject::connect(btnSorteaza, &QPushButton::clicked, [&]()
		{
			loadTable(service.sorteazaJocuri());
		});
}
