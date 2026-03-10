#include "Gui.h"
#include <QHBoxLayout>
#include <QMessageBox>
#include <random>
#include <algorithm>

Gui::Gui(Service& service) : service(service)
{
	initGui();
	loadTable(service.afiseazaEchipe());
	connectBtnSignals();
}

void Gui::initGui()
{
	setWindowTitle("Meciuri");
	QHBoxLayout* layoutMain = new QHBoxLayout;
	QVBoxLayout* layoutDreapta = new QVBoxLayout;
	QHBoxLayout* layoutStandings = new QHBoxLayout;
	QHBoxLayout* layoutRezultate = new QHBoxLayout;

	listaEchipe = new QListWidget;
	btnGenereaza = new QPushButton("Genereaza conferinte");
	btnJoaca = new QPushButton("Joaca meci");
	echipeConferinta1 = new QListWidget;
	echipeConferinta2 = new QListWidget;
	rezultateConferinta1 = new QListWidget;
	rezultateConferinta2 = new QListWidget;
	layoutMain->addWidget(listaEchipe);
	layoutMain->addWidget(btnGenereaza);
	layoutStandings->addWidget(echipeConferinta1);
	layoutStandings->addWidget(echipeConferinta2);
	layoutDreapta->addLayout(layoutStandings);
	layoutDreapta->addWidget(btnJoaca);
	layoutRezultate->addWidget(rezultateConferinta1);
	layoutRezultate->addWidget(rezultateConferinta2);
	layoutDreapta->addLayout(layoutRezultate);
	layoutMain->addLayout(layoutDreapta);
	setLayout(layoutMain);
}

void Gui::loadTable(const vector<Echipa>& echipe) const
{
	listaEchipe->clear();
	for (const auto& echipa : echipe)
	{
		listaEchipe->addItem(QString::fromStdString(echipa.getNume()));
	}
}

void Gui::connectBtnSignals()
{
	QObject::connect(btnGenereaza, &QPushButton::clicked, [&]()
		{
			echipeConferinta1->clear();
			echipeConferinta2->clear();
			rezultateConferinta1->clear();
			rezultateConferinta2->clear();
			conferinta1.clear();
			conferinta2.clear();
			meciuri1.clear();
			meciuri2.clear();
			vector<Echipa> aux;
			for (const auto& echipa : service.afiseazaEchipe())
			{
				aux.push_back(echipa);
			}
			while (!aux.empty())
			{
                
				int n = rand() % aux.size();
                conferinta1.push_back(aux[n]);
                aux.erase(aux.begin() + n);
                if (aux.empty())
                {
                    break;
                }
                std::uniform_int_distribution<> dist2(0, static_cast<int>(aux.size()) - 1);
				n = rand() % aux.size();
                conferinta2.push_back(aux[n]);
                aux.erase(aux.begin() + n);
			}
			for (const auto& echipa : conferinta1)
			{
				QListWidgetItem* item = new QListWidgetItem;
				item->setForeground(Qt::blue);
				item->setText(QString::fromStdString(echipa.getNume()));
				echipeConferinta1->addItem(item);
			}
			for (const auto& echipa : conferinta2)
			{
				QListWidgetItem* item = new QListWidgetItem;
				item->setForeground(Qt::red);
				item->setText(QString::fromStdString(echipa.getNume()));
				echipeConferinta2->addItem(item);
			}
			for (int i = 0; i < static_cast<int>(conferinta1.size()); i++)
			{
				for (int j = i + 1; j < static_cast<int>(conferinta1.size()); j++)
				{
						Meci meci(conferinta1[i].getNume(), conferinta1[j].getNume());
						meciuri1.push_back(meci);
				}
			}

			for (int i = 0; i < static_cast<int>(conferinta2.size()); i++)
			{
				for (int j = i + 1; j < static_cast<int>(conferinta2.size()); j++)
				{
						Meci meci(conferinta2[i].getNume(), conferinta2[j].getNume());
						meciuri2.push_back(meci);
				}
			}
		});
	QObject::connect(btnJoaca, &QPushButton::clicked, [&]()
		{
			QListWidgetItem* item1 = new QListWidgetItem;
			QListWidgetItem* item2 = new QListWidgetItem;
			item1 = echipeConferinta1->currentItem();
			item2 = echipeConferinta2->currentItem();
			string numeEchipa1 = item1 ? item1->text().toStdString() : "";
			string numeEchipa2 = item2 ? item2->text().toStdString() : "";
			if (!item1 && !item2)
			{
				QMessageBox::warning(this, "Eroare", "Selectati o echipa!");
				return;
			}
			if (item1)
			{
				bool found = false;
				for (const auto& meci : meciuri1)
				{
					if (meci.getEchipa1() == numeEchipa1 || meci.getEchipa2() == numeEchipa1)
					{
						found = true;
						int scor1 = rand() % 50 + 60;
						int scor2 = rand() % 50 + 60;
						QString rezultat = QString::fromStdString(meci.getEchipa1() + " " + std::to_string(scor1) + " - " + std::to_string(scor2) + " " + meci.getEchipa2());
						rezultateConferinta1->addItem(rezultat);
						meciuri1.erase(std::remove(meciuri1.begin(), meciuri1.end(), meci), meciuri1.end());
						break;
					}
				}
				if (!found)
				{
					QMessageBox::warning(this, "Eroare", QString::fromStdString("Echipa " + numeEchipa1 + " nu mai are meciuri de jucat!"));
					return;
				}
			}
			if (item2)
			{
				bool found = false;
				for (const auto& meci : meciuri2)
				{
					if (meci.getEchipa1() == numeEchipa2 || meci.getEchipa2() == numeEchipa2)
					{
						found = true;
						int scor1 = rand() % 50 + 60;
						int scor2 = rand() % 50 + 60;
						QString rezultat = QString::fromStdString(meci.getEchipa1() + " " + std::to_string(scor1) + " - " + std::to_string(scor2) + " " + meci.getEchipa2());
						rezultateConferinta2->addItem(rezultat);
						meciuri2.erase(std::remove(meciuri2.begin(), meciuri2.end(), meci), meciuri2.end());
						break;
					}
				}
				if (!found)
				{
					QMessageBox::warning(this, "Eroare", QString::fromStdString("Echipa " + numeEchipa1 + " nu mai are meciuri de jucat!"));
					return;
				}
			}
			
		});
}