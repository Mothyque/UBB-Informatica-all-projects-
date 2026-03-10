#include "Gui.h"
#include <QHBoxLayout>
#include <QMessageBox>
Gui::Gui(Service& service) : service(service) 
{
	// Constructorul clasei Gui
	// paremetrii: service - referinta la service
	initGui();
	loadTable();
	connectBtnSignals();
}

void Gui::initGui()
{
	/* Initializeaza gui*/
	tableStudenti = new QTableView;
	model = new StudentTableModel(service.sorteaza());
	tableStudenti->setModel(model);
	QHBoxLayout* layoutMain = new QHBoxLayout;
	layoutMain->addWidget(tableStudenti);
	QVBoxLayout* layoutBtn = new QVBoxLayout;
	btnImbatranire = new QPushButton("Imbatranire");
	btnIntinerire = new QPushButton("Intinerire");
	btnUndo = new QPushButton("Undo");
	btnSterge = new QPushButton("Sterge");
	btnRedo = new QPushButton("Redo");
	layoutBtn->addWidget(btnImbatranire);
	layoutBtn->addWidget(btnIntinerire);
	layoutBtn->addWidget(btnSterge);
	layoutBtn->addWidget(btnUndo);
	layoutBtn->addWidget(btnRedo);
	layoutMain->addLayout(layoutBtn);
	setLayout(layoutMain);
}

void Gui::loadTable() const
{
	/* Incarca fereastra cu studentii sortati dupa varsta */
	model->setStudenti(service.sorteaza());
}

void Gui::connectBtnSignals()
{
	/* Face conexiunea intre butoane si aplicatie */
	QObject::connect(btnImbatranire, &QPushButton::clicked, [&]() {
		service.modifyAge(1);
		loadTable();
		});
	QObject::connect(btnIntinerire, &QPushButton::clicked, [&]() {
		service.modifyAge(-1);
		loadTable();
		});
	QObject::connect(tableStudenti, &QTableView::clicked, [&]()
		{
			int row = tableStudenti->currentIndex().row();
			int nrMatricol = model->data(model->index(row, 0)).toInt();
			indexes.push_back(nrMatricol);
		});
	QObject::connect(btnSterge, &QPushButton::clicked, [&]()
		{
			if (indexes.empty())
			{
				QMessageBox::warning(nullptr, "Eroare", "Selecteaza un item!");
			}
			else
			{
				service.removeStudent(indexes[indexes.size() - 1]);
				indexes.pop_back();
				loadTable();
			}
		});
	QObject::connect(btnUndo, &QPushButton::clicked, [&]() {
		try
		{
			service.undo();
			loadTable();
		}
		catch(const std::exception& e)
		{
			QMessageBox::warning(nullptr, "Eroare", e.what());
		}
		});
	QObject::connect(btnRedo, &QPushButton::clicked, [&]() {
		try
		{
			service.redo();
			loadTable();
		}
		catch (const std::exception& e)
		{
			QMessageBox::warning(nullptr, "Eroare", e.what());
		}
		});
}