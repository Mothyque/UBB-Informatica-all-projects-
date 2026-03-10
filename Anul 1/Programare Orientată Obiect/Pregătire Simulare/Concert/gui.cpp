#include "gui.h"
#include <QHBoxLayout>
#include <QMessageBox>

Gui::Gui(Service& service) : service(service)
{
	initGui();
	connectSignalSlots();
	loadTable(service.afiseazaConcerte());
}

void Gui::initGui()
{
	QHBoxLayout* layoutMain = new QHBoxLayout;
	tableConcerte = new QListWidget;
	btnModifica = new QPushButton("Modifica");
	btnSorteaza= new QPushButton("Sorteaza");
	btnCumpara = new QPushButton("Cumpara bilete");
	btnReload = new QPushButton("Reload");
	slider = new QSlider;
	slider->setOrientation(Qt::Horizontal);
	slider->setRange(1, 100);
	layoutMain->addWidget(tableConcerte);
	QVBoxLayout* layoutOperatii = new QVBoxLayout;
	layoutOperatii->addWidget(slider);
	QHBoxLayout* layoutBtn = new QHBoxLayout;
	layoutBtn->addWidget(btnModifica);
	layoutBtn->addWidget(btnCumpara);
	layoutBtn->addWidget(btnSorteaza);
	layoutBtn->addWidget(btnReload);
	layoutOperatii->addLayout(layoutBtn);
	layoutMain->addLayout(layoutOperatii);
	setLayout(layoutMain);

}

void Gui::connectSignalSlots()
{
	QObject::connect(btnModifica, &QPushButton::clicked, [&]()
		{
		int nrBilete = slider->value();
		QListWidgetItem* item = tableConcerte->currentItem();
		if (item == nullptr)
		{
			QMessageBox::warning(nullptr,"Eroare", "Trebuie sa selectezi un item");
			return;
		}
		QString text = item->text();
		QStringList cuvinte = text.split(", ");
		string nume = cuvinte.at(0).toStdString();
		string data = cuvinte.at(1).toStdString();
		ConcertD aux(nume, data, 0);
		for (const auto& concert : service.afiseazaConcerte())
		{
			if (concert == aux)
			{
				nrBilete += concert.getNrBilete();
			}
		}
		if (nrBilete > 500)
		{
			QMessageBox::warning(nullptr, "Eroare", "Prea multe bilete disponibile");
			return;
		}
		service.modificaConcert(nume, data, nrBilete);
		slider->setValue(1);
		loadTable(service.afiseazaConcerte());
		});
	QObject::connect(btnCumpara, &QPushButton::clicked, [&]()
		{
		QListWidgetItem* item = tableConcerte->currentItem();
		if (item == nullptr)
		{
			QMessageBox::warning(nullptr, "Eroare", "Trebuie sa selectezi un item");
			return;
		}
		QString text = item->text();
		QStringList cuvinte = text.split(", ");
		string nume = cuvinte.at(0).toStdString();
		string data = cuvinte.at(1).toStdString();
		int nrBilete = cuvinte.at(2).toInt();
		ConcertD aux(nume, data, nrBilete);
		for (const auto& concert : service.afiseazaConcerte())
		{
			if (aux == concert)
			{
				nrBilete = concert.getNrBilete() / 2;
				break;
			}
		}
		service.modificaConcert(nume, data, nrBilete);
		loadTable(service.afiseazaConcerte());
		});
	QObject::connect(btnSorteaza, &QPushButton::clicked, [&]() {
		loadTable(service.sorteazaConcerte());
		});
	QObject::connect(btnReload, &QPushButton::clicked, [&]()
		{
			loadTable(service.afiseazaConcerte());
		});
}


void Gui::loadTable(const vector<ConcertD>& concerte) const
{
	tableConcerte->clear();
	for (const auto& concert : concerte)
	{
		QListWidgetItem* item = new QListWidgetItem;
		if (concert.getNrBilete() <= 0)
		{
			item->setForeground(Qt::red);
		}
		item->setText(QString::fromStdString(concert.getNume() + ", " + concert.getData() + ", " + std::to_string(concert.getNrBilete())));
		tableConcerte->addItem(item);
	}
}

