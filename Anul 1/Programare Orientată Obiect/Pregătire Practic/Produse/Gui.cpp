#include "Gui.h"
#include <QHBoxLayout>
#include <QMessageBox>
#include <QFormLayout>
#include <map>

#include "CounterProduse.h"

using std::map;

Gui::Gui(Service& service) : service(service)
{
	initGui();
	loadTable();
	connectBtnSignals();
}

void Gui::initGui()
{
	tableProduse = new QTableView;
	model = new ProdusTableModel(service.sortProduse());
	tableProduse->setModel(model);
	QHBoxLayout* layoutMain = new QHBoxLayout;
	QVBoxLayout* layoutDreapta = new QVBoxLayout;
	QFormLayout* layoutLineEdit = new QFormLayout;
	id = new QLineEdit;
	nume = new QLineEdit;
	tip = new QLineEdit;
	pret = new QLineEdit;
	layoutLineEdit->addRow("Id", id);
	layoutLineEdit->addRow("Nume", nume);
	layoutLineEdit->addRow("Tip", tip);
	layoutLineEdit->addRow("Pret", pret);
	layoutDreapta->addLayout(layoutLineEdit);
	btnAdd = new QPushButton("Add");
	filtrare = new QSlider;
	filtrare->setRange(1, 100);
	filtrare->setOrientation(Qt::Horizontal);
	layoutDreapta->addWidget(btnAdd);
	layoutDreapta->addWidget(filtrare);
	layoutMain->addWidget(tableProduse);
	layoutMain->addLayout(layoutDreapta);
	setLayout(layoutMain);
	map<string, int> tipuri;
	for (const auto& produs : service.afiseazaProduse())
	{
		tipuri[produs.getTip()]++;
	}
	for (const auto pair : tipuri)
	{
		CounterProduse* window = new CounterProduse(service, pair.first);
		window->show();
		service.addObserver(window);
	}
}

void Gui::loadTable() const
{
	model->setProduse(service.sortProduse());
}

void Gui::connectBtnSignals()
{
	QObject::connect(btnAdd, &QPushButton::clicked, [&]()
		{
			int idA = id->text().toInt();
			string numeA = nume->text().toStdString();
			string tipA = tip->text().toStdString();
			double pretA = pret->text().toDouble();
			try
			{
				service.addProdus(idA, numeA, tipA, pretA);
				QMessageBox::information(nullptr, "Succes", "S-a adaugat un produs");
			}
			catch (const std::exception& e)
			{
				QMessageBox::warning(nullptr, "Eroare", e.what());
			}
			loadTable();
		});
	QObject::connect(filtrare, &QSlider::valueChanged, [&]() {
		model->setSliderValue(filtrare->value());
		loadTable();
		});
}