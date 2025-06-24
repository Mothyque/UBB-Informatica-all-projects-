#include "gui.h"
#include <QHBoxLayout>
#include <QMessageBox>

Gui::Gui(Service& service) : service(service)
{
	initGui();
	connectBtnSlots();
	loadTable(service.afiseazaJucatori(), "");
}


void Gui::initGui()
{
	setWindowTitle("Jucatori");
	QHBoxLayout* layoutMain = new QHBoxLayout;
	listaJucatori = new QListWidget;
	layoutMain->addWidget(listaJucatori);
	QVBoxLayout* layoutInfo = new QVBoxLayout;
	nume = new QLineEdit;
	tara = new QLineEdit;
	ranking = new QSpinBox;
	layoutInfo->addWidget(nume);
	layoutInfo->addWidget(tara);
	layoutInfo->addWidget(ranking);
	QVBoxLayout* layoutDreapta = new QVBoxLayout;
	layoutDreapta->addLayout(layoutInfo);
	QHBoxLayout* layoutBtn = new QHBoxLayout;
	adauga = new QPushButton("Adauga");
	sorteaza = new QPushButton("Sorteaza dupa ranking");
	reload = new QPushButton("Reload");
	qualify = new QCheckBox("Calificari");
	layoutBtn->addWidget(adauga);
	layoutBtn->addWidget(sorteaza);
	layoutBtn->addWidget(reload);
	layoutBtn->addWidget(qualify);
	layoutDreapta->addLayout(layoutBtn);
	layoutMain->addLayout(layoutDreapta);
	setLayout(layoutMain);
}

void Gui::connectBtnSlots()
{
	QObject::connect(adauga, &QPushButton::clicked, [&]()
		{
			string numeAdaugat = nume->text().toStdString();
			string taraAdaugat = tara->text().toStdString();
			int rankingAdaugat = ranking->text().toInt();
			try
			{
				service.adaugaJucator(numeAdaugat, taraAdaugat, rankingAdaugat);
				loadTable(service.afiseazaJucatori(), "");
			}
			catch (const std::exception& e)
			{
				QMessageBox::warning(this, "Warning", QString::fromStdString(e.what()));

			}
		});
	QObject::connect(qualify, &QCheckBox::clicked, [&]()
		{
			loadTable(service.afiseazaJucatori(), "calificare");
		});
	QObject::connect(reload, &QPushButton::clicked, [&]()
		{
			loadTable(service.afiseazaJucatori(), "");
		});
	QObject::connect(sorteaza, &QPushButton::clicked, [&]()
		{
			loadTable(service.sorteazaJucatori(), "");
		});
}

void Gui::loadTable(const vector<Jucator>& lista, const string& optiune) const
{
	listaJucatori->clear();
	for (const auto& jucator : lista)
	{
		QListWidgetItem* item = new QListWidgetItem(QString::fromStdString(jucator.getNume() + ", " + jucator.getNume() + ", " +  std::to_string(jucator.getPuncte()) + ", " + std::to_string(jucator.getRanking())));
		if (jucator.getPuncte() >= 250 && optiune == "calificare")
		{
			item->setForeground(Qt::green);
		}
		listaJucatori->addItem(item);
	}
}

