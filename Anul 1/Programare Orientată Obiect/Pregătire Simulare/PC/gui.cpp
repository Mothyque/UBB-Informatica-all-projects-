#include "gui.h"
#include <QHBoxLayout>
#include <QFormLayout>
#include <QMessageBox>
Gui::Gui(Service& service) : service(service)
{
	initGui();
	connectBtnSignals();
	loadTable(service.afiseazaProcesoare(), service.afiseazaPlaciDeBaza());
}


void Gui::initGui()
{
	setWindowTitle("PC");
	QHBoxLayout* layoutMain = new QHBoxLayout;
	listaProcesoare = new QListWidget;
	listaPlaciDeBaza = new QListWidget;
	layoutMain->addWidget(listaProcesoare);
	layoutMain->addWidget(listaPlaciDeBaza);
	QFormLayout* layoutLineEdit = new QFormLayout;
	nume = new QLineEdit;
	socluProcesor = new QLineEdit;
	pret = new QLineEdit;
	sumaL = new QLineEdit;
	layoutLineEdit->addRow("Nume:", nume);
	layoutLineEdit->addRow("Soclu procesor:", socluProcesor);
	layoutLineEdit->addRow("Pret:", pret);
	layoutLineEdit->addRow("Suma:", sumaL);
	QVBoxLayout* layoutDreapta = new QVBoxLayout;
	layoutDreapta->addLayout(layoutLineEdit);
	QHBoxLayout* layoutBtn = new QHBoxLayout;
	adauga = new QPushButton("Adauga");
	filtreaza = new QPushButton("Filtreaza");
	reload = new QPushButton("Reload");
	suma = new QPushButton("Suma");
	layoutBtn->addWidget(adauga);
	layoutBtn->addWidget(filtreaza);
	layoutBtn->addWidget(reload);
	layoutBtn->addWidget(suma);
	layoutDreapta->addLayout(layoutBtn);
	layoutMain->addLayout(layoutDreapta);
	setLayout(layoutMain);
}

void Gui::loadTable(const vector<Procesor>& procesoare, const vector<PlacaDeBaza>& placiDeBaza) const
{
	listaPlaciDeBaza->clear();
	listaProcesoare->clear();
	for (const auto& procesor : procesoare)
	{
		listaProcesoare->addItem(QString::fromStdString(procesor.getNume() + " - " + std::to_string(procesor.getThreaduri())));
	}
	for (const auto& placa_de_baza : placiDeBaza)
	{
		listaPlaciDeBaza->addItem(QString::fromStdString(placa_de_baza.getNume()));
	}
}

void Gui::connectBtnSignals()
{
	QObject::connect(adauga, &QPushButton::clicked, [&]()
		{
			string numeAdaugat = nume->text().toStdString();
			string socluAdaugat = socluProcesor->text().toStdString();
			int pretAdaugat = pret->text().toInt();
			service.adaugaPlacaDeBaza(numeAdaugat, socluAdaugat, pretAdaugat);
			loadTable(service.afiseazaProcesoare(), service.afiseazaPlaciDeBaza());
		});
	QObject::connect(reload, &QPushButton::clicked, [&]()
		{
			loadTable(service.afiseazaProcesoare(), service.afiseazaPlaciDeBaza());
		});
	QObject::connect(filtreaza, &QPushButton::clicked, [&]()
		{
			if (listaProcesoare->currentItem() == nullptr)
			{
				QMessageBox::warning(nullptr, "Eroare", "Nu este selectat niciun procesor!");
				return;
			}
			QListWidgetItem* item = listaProcesoare->currentItem();
			QString text = item->text();
			QStringList lista = text.split(" - ");
			string numeCautat = lista.at(0).toStdString();
			int nrThreaduriCautat = lista.at(1).toInt();
			string soclu;
			for (const auto& procesor : service.afiseazaProcesoare())
			{
				if (procesor.getThreaduri() == nrThreaduriCautat && procesor.getNume() == numeCautat)
				{
					soclu = procesor.getSoclu();
					break;
				}
			}
			loadTable(service.afiseazaProcesoare(), service.filtreazaPlaciDeBaza(soclu));
		});
	QObject::connect(suma, &QPushButton::clicked, [&]()
		{
			if (listaPlaciDeBaza->currentItem() == nullptr || listaProcesoare->currentItem() == nullptr)
			{
				QMessageBox::warning(nullptr, "Eroare", "Nu este selectat procesor sau placa de baza!");
				return;
			}
			int sumaN = 0;
			QListWidgetItem* item = listaProcesoare->currentItem();
			QString text = item->text();
			QStringList lista = text.split(" - ");
			string numeCautat = lista.at(0).toStdString();
			int threaduri = lista.at(1).toInt();
			for (const auto& procesor : service.afiseazaProcesoare())
			{
				if (procesor.getThreaduri() == threaduri && procesor.getNume() == numeCautat)
				{
					sumaN += procesor.getPret();
					break;
				}
			}
			item = listaPlaciDeBaza->currentItem();
		    numeCautat = item->text().toStdString();
			for (const auto& placa_de_baza : service.afiseazaPlaciDeBaza())
			{
				if (placa_de_baza.getNume() == numeCautat)
				{
					sumaN += placa_de_baza.getPret();
					break;
				}
			}
			sumaL->setText(QString::fromStdString(std::to_string(sumaN)));
		});
}
