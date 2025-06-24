#include "Gui.h"
#include <QVBoxLayout>
#include <QFormLayout>
#include <QPainter>
#include <set>

using std::set;

Gui::Gui(Service& service) : service(service)
{
	initGui();
	loadTable();
	connectBtnSignals();
}

void Gui::initGui()
{
	QVBoxLayout* layoutMain = new QVBoxLayout;
	QHBoxLayout* layoutObiecte = new QHBoxLayout;
	tableTractoare = new QTableView;
	model = new TractorTableModel(service.sorteazaTractoare());
	tableTractoare->setModel(model);
	layoutObiecte->addWidget(tableTractoare);
	QVBoxLayout* layoutDreapta = new QVBoxLayout;
	QFormLayout* layoutLineEdituri = new QFormLayout;
	id = new QLineEdit;
	nume = new QLineEdit;
	tip = new QLineEdit;
	nrRoti = new QLineEdit;
	btnAdd = new QPushButton("Add");
	layoutLineEdituri->addRow("Id:", id);
	layoutLineEdituri->addRow("Nume:", nume);
	layoutLineEdituri->addRow("Tip:", tip);
	layoutLineEdituri->addRow("Numar roti:", nrRoti);
	layoutDreapta->addLayout(layoutLineEdituri);
	tipuri = new QComboBox;
	layoutDreapta->addWidget(btnAdd);
	layoutDreapta->addWidget(tipuri);
	layoutObiecte->addLayout(layoutDreapta);
	layoutMain->addLayout(layoutObiecte);
	desen = new DesenWidget;
	desen->setFixedHeight(80);
	layoutMain->addWidget(desen);
	setLayout(layoutMain);
}

void Gui::loadTable() const
{
	model->setTractoare(service.sorteazaTractoare());
	tipuri->clear();
	set <string> toateTipurile;
	for (const auto& tractor : service.afiseazaTractoare())
	{
		toateTipurile.insert(tractor.getTip());
	}
	for (const auto& tipTractor : toateTipurile)
	{
		tipuri->addItem(QString::fromStdString(tipTractor));
	}
}

void Gui::connectBtnSignals()
{
	QObject::connect(btnAdd, &QPushButton::clicked, [&]() {
		int idA = id->text().toInt();
		string numeA = nume->text().toStdString();
		string tipA = tip->text().toStdString();
		int nrRotiA = nrRoti->text().toInt();
		try
		{
			service.addTractor(idA, numeA, tipA, nrRotiA);
			QMessageBox::information(nullptr, "Succes", "Tractor adaugat cu succes");
		}
		catch (const std::exception& e)
		{
			QMessageBox::warning(nullptr, "Eroare", e.what());
		}
		loadTable();
		});
	QObject::connect(tipuri, &QComboBox::currentIndexChanged, [&]() {
		model->setTip(tipuri->currentText().toStdString());
		model->setTractoare(service.sorteazaTractoare());
		});
	QObject::connect(tableTractoare, &QTableView::clicked, [&]() {
		int row = tableTractoare->currentIndex().row();
		int numarRoti = model->data(model->index(row, 3), Qt::DisplayRole).toInt();
		desen->setNrRoti(numarRoti);
		});
	QObject::connect(desen, &DesenWidget::cercClicked, [&]() {
		int row = tableTractoare->currentIndex().row();
		int idTractor = model->data(model->index(row, 0), Qt::DisplayRole).toInt();
		service.decrementeazaNrRoti(idTractor);
		loadTable();
		});
}