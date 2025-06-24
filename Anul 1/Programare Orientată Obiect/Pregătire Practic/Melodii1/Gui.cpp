#include "Gui.h"
#include <QHBoxLayout>
#include <QMessageBox>

#include "Design.h"

Gui::Gui(Service& service) : service(service)
{
	initGui();
	loadTable();
	connectBtnSignals();
}

void Gui::initGui()
{
	tableMelodii = new QTableView;
	model = new MelodiiTableModel(service.afiseazaMelodii());
  	tableMelodii->setModel(model);
	QHBoxLayout* layoutMain = new QHBoxLayout;
	QVBoxLayout* layoutDreapta = new QVBoxLayout;
	titlu = new QLineEdit;
	rank = new QSlider;
	rank->setOrientation(Qt::Horizontal);
	rank->setRange(0, 10);
	btnModifica = new QPushButton("Modifica");
	btnSterge = new QPushButton("Sterge");
	QHBoxLayout* layoutButoane = new QHBoxLayout;
	layoutDreapta->addWidget(titlu);
	layoutDreapta->addWidget(rank);
	layoutButoane->addWidget(btnModifica);
	layoutButoane->addWidget(btnSterge);
	layoutDreapta->addLayout(layoutButoane);
	layoutMain->addWidget(tableMelodii);
	layoutMain->addLayout(layoutDreapta);
	setLayout(layoutMain);
	Design* windowDesign = new Design(service);
	windowDesign->show();
	service.addObserver(windowDesign);
}

void Gui::connectBtnSignals()
{
	QObject::connect(tableMelodii, &QTableView::clicked, [&]() {
		QModelIndex current = tableMelodii->currentIndex();
		int row = current.row();
		QModelIndex index = model->index(row, 1);
		QString titluS = model->data(index, Qt::DisplayRole).toString();
		titlu->setText(titluS);
		index = model->index(row, 3);
		int rankS = model->data(index, Qt::DisplayRole).toInt();
		rank->setValue(rankS);
		});
	QObject::connect(btnModifica, &QPushButton::clicked, [&]() {
		string titluCautat = titlu->text().toStdString(), artist;
		for (const auto& melodie : service.afiseazaMelodii())
		{
			if (melodie.getTitlu() == titluCautat)
			{
				artist = melodie.getArtist();
				break;
			}
		}
		int rankNou = rank->value();
		service.modificaMelodie(titluCautat, artist, rankNou);
		QMessageBox::information(nullptr, "Succes", "Melodia a fost modificata cu succes!");
		loadTable();
		});
	QObject::connect(btnSterge, &QPushButton::clicked, [&]() {
		string titluCautat = titlu->text().toStdString(), artist;
		for (const auto& melodie : service.afiseazaMelodii())
		{
			if (melodie.getTitlu() == titluCautat)
			{
				artist = melodie.getArtist();
				break;
			}
		}
		try
		{
			service.stergeMelodie(titluCautat, artist);		
			QMessageBox::information(nullptr, "Succes", "Melodia s-a sters");
		}
		catch (const std::exception& e)
		{
			QMessageBox::warning(nullptr, "Eroare", e.what());
		}
		});
}
void Gui::loadTable() const
{
	model->setMelodii(service.sorteazaMelodii());
}