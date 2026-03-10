#include "gui.h"
#include <QHBoxLayout>
#include <QLineEdit>
#include <QPushButton>
#include <QFormLayout>

Gui::Gui(Service& service) : service(service)
{
	initializeGui();
	connectButtonsSignals();
	loadTable(service.afiseazaDoctori());
}

void Gui::initializeGui()
{
	setWindowTitle("Doctori");
	QHBoxLayout* layoutMain = new QHBoxLayout;
	listaDoctori = new QListWidget;
	cnpView = new QLineEdit;
	cnpView->setReadOnly(true);
	QVBoxLayout* layoutLista = new QVBoxLayout;
	layoutLista->addWidget(listaDoctori);
	layoutLista->addWidget(cnpView);
	layoutMain->addLayout(layoutLista);
	QVBoxLayout* layoutFiltrare = new QVBoxLayout;
	sectieFilter = new QLineEdit;
	prenumeFilter = new QLineEdit;
	QFormLayout* layoutLineEdit = new QFormLayout;
	layoutLineEdit->addRow("Sectie", sectieFilter);
	layoutLineEdit->addRow("Prenume", prenumeFilter);
	layoutFiltrare->addLayout(layoutLineEdit);
	QHBoxLayout* layoutButoane = new QHBoxLayout;
	btnSectie = new QPushButton("Filtreaza dupa sectie");
	btnPrenume = new QPushButton("Filtreaza dupa prenume");
	btnToate = new QPushButton("Filtreaza dupa ambele");
	btnReload = new QPushButton("Reload");
	layoutButoane->addWidget(btnSectie);
	layoutButoane->addWidget(btnPrenume);
	layoutButoane->addWidget(btnToate);
	layoutButoane->addWidget(btnReload);
	layoutFiltrare->addLayout(layoutButoane);
	layoutMain->addLayout(layoutFiltrare);
	setLayout(layoutMain);
}

void Gui::connectButtonsSignals()
{
	QObject::connect(listaDoctori, &QListWidget::clicked, [&]()
		{
			QListWidgetItem* item = listaDoctori->currentItem();
			QString text = item->text();
			QStringList parts = text.split(" - ");
			string prenume = parts.at(0).toStdString();
			string sectie = parts.at(1).toStdString();
			for (const auto& doctor : service.afiseazaDoctori())
			{
				if (doctor.getPrenume() == prenume && doctor.getSectie() == sectie)
				{
					QString cnp = QString::fromStdString(doctor.getCnp());
					cnpView->setText(cnp);
					break;
				}
			}
		});
	QObject::connect(btnPrenume, &QPushButton::clicked, [&]()
		{
			string prenume = prenumeFilter->text().toStdString();
			loadTable(service.filtreaza("Prenume", "", prenume));
		});
	QObject::connect(btnSectie, &QPushButton::clicked, [&]()
		{
			string sectie = sectieFilter->text().toStdString();
			loadTable(service.filtreaza("Sectie", sectie, ""));
		});
	QObject::connect(btnToate, &QPushButton::clicked, [&]()
		{
			string prenume = prenumeFilter->text().toStdString();
			string sectie = sectieFilter->text().toStdString();
			loadTable(service.filtreaza("Toate", sectie, prenume));
		});
	QObject::connect(btnReload, &QPushButton::clicked, [&]()
		{
			loadTable(service.afiseazaDoctori());
		});
}

void Gui::loadTable(const vector<Doctor>& doctori) const
{
	listaDoctori->clear();
	for (const auto& doctor : doctori)
	{
		QListWidgetItem* item = new QListWidgetItem(QString::fromStdString(doctor.getPrenume() + " - " + doctor.getSectie()));
		if (doctor.getConcediu() == true)
		{
			item->setForeground(Qt::red);
		}
		else
		{
			item->setForeground(Qt::green);
		}
		listaDoctori->addItem(item);
	}
}



