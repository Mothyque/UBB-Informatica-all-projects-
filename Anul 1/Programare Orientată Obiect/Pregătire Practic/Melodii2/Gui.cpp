#include "Gui.h"
#include <QHBoxLayout>
#include <QFormLayout>
#include <QMessageBox>

Gui::Gui(Service& service) : service(service) 
{
	initGui();
	loadTable();
	connectBtnSignals();
}

void Gui::initGui()
{
	desen = new DesenWidget(service);
	service.addObserver(desen);
	desen->setMinimumSize(300, 300);
	tableMelodii = new QTableView;
	model = new MelodieTableModel(service.sorteazaMelodii());
	tableMelodii->setModel(model);
	QHBoxLayout* layoutMain = new QHBoxLayout;
	layoutMain->addWidget(desen);
	layoutMain->addWidget(tableMelodii);
	QFormLayout* layoutLineEdits = new QFormLayout;
	id = new QLineEdit;
	titlu = new QLineEdit;
	artist = new QLineEdit;
	gen = new QLineEdit;
	layoutLineEdits->addRow("Id:", id);
	layoutLineEdits->addRow("Titlu:", titlu);
	layoutLineEdits->addRow("Artist:", artist);
	layoutLineEdits->addRow("Gen:", gen);
	QVBoxLayout* layoutDreapta = new QVBoxLayout;
	layoutDreapta->addLayout(layoutLineEdits);
	btnAdd = new QPushButton("Add");
	btnDelete = new QPushButton("Delete");
	layoutDreapta->addWidget(btnAdd);
	layoutDreapta->addWidget(btnDelete);
	layoutMain->addLayout(layoutDreapta);
	setLayout(layoutMain);

}

void Gui::loadTable() const
{
	model->setMelodii(service.sorteazaMelodii());
}

void Gui::connectBtnSignals()
{
	QObject::connect(btnAdd, &QPushButton::clicked, [&]() {
		int idA = id->text().toInt();
		string titluA = titlu->text().toStdString();
		string artistA = artist->text().toStdString();
		string genA = gen->text().toStdString();
		service.adaugaMelodie(idA, titluA, artistA, genA);
		QMessageBox::information(nullptr, "Succes", "S-a adaugat melodia!");
		loadTable();
		});
	QObject::connect(btnDelete, &QPushButton::clicked, [&]() {
		int row = tableMelodii->currentIndex().row();
		int idS = model->data(model->index(row, 0)).toInt();
		service.stergeMelodie(idS);
		QMessageBox::information(nullptr, "Succes", "S-a reusit stergerea!");
		loadTable();
		});
}