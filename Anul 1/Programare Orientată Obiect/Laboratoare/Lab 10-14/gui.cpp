#include "gui.h"
#include <QVBoxLayout>
#include <QLineEdit>
#include <QFormLayout>
#include <QMessageBox>

#include "cosgui.h"
#include "exceptii.h"

Gui::Gui(Service& service, CosInchiriere& cos, CosReadOnlyGui& cosReadOnlyGui) : service{ service }, cos{ cos }, cosReadOnlyGui{ cosReadOnlyGui }
{
	/* Constructorul clasei Gui
	 * parameter service: referinta la obiectul de tip Service
	 * parameter parent: pointer la obiectul parinte
	 */
	cos.addObserver(&cosReadOnlyGui);
	initGui();
	reload();
	connectSignalsAndSlots();
}

void Gui::initGui() 
{
	/* Initializeaza interfata grafica*/
	widgetRaportLitere = new QWidget();
	btnRaportlitere = new QPushButton("Deschide Raport Filme");
	btnCos = new QPushButton("Cos Filme");
	btnCosGrafic = new QPushButton("Cos Filme Grafic");
	QHBoxLayout* layoutMain = new QHBoxLayout();
	layoutRaportLitere = new QVBoxLayout();
	QVBoxLayout* layoutOperatii = new QVBoxLayout();
	QFormLayout* formLinii = new QFormLayout();
	QHBoxLayout* layoutButoaneCrud = new QHBoxLayout();
	QHBoxLayout* layoutSort = new QHBoxLayout();
	QVBoxLayout* layoutButoaneDreapta = new QVBoxLayout();
	cosGui = new CosGUI(cos);
	cosGui->setWindowTitle("Cos Inchirieri");
	titlu = new QLineEdit();
	regizor = new QLineEdit();
	an = new QLineEdit();
	actor = new QLineEdit();
	formLinii->addRow("Titlu:", titlu);
	formLinii->addRow("Regizor:", regizor);
	formLinii->addRow("An:", an);
	formLinii->addRow("Actor:", actor);
	layoutOperatii->addLayout(formLinii);
	btnAdauga = new QPushButton("Adauga");
	btnSterge = new QPushButton("Sterge");
	btnModifica = new QPushButton("Modifica");
	btnExit = new QPushButton("Iesire");
	layoutButoaneCrud->addWidget(btnAdauga);
	layoutButoaneCrud->addWidget(btnSterge);
	layoutButoaneCrud->addWidget(btnModifica);
	layoutButoaneCrud->addWidget(btnExit);
	layoutOperatii->addLayout(layoutButoaneCrud);

	btnSort = new QPushButton("Sortare");
	optiuneSort = new QComboBox();
	ordineSort = new QComboBox();
	optiuneSort->addItem("Titlu");
	optiuneSort->addItem("Regizor");
	optiuneSort->addItem("An");
	optiuneSort->addItem("Actor");
	ordineSort->addItem("Crescator");
	ordineSort->addItem("Descrescator");
	layoutSort->addWidget(optiuneSort);
	layoutSort->addWidget(ordineSort);
	layoutSort->addWidget(btnSort);
	layoutOperatii->addLayout(layoutSort);

	btnUndo = new QPushButton("Undo");
	layoutOperatii->addWidget(btnUndo);

	QHBoxLayout* layoutFilter = new QHBoxLayout();
	btnFilter = new QPushButton("Filtrare");
	lineFilter = new QLineEdit();
	optiuneFilter = new QComboBox();
	optiuneFilter->addItem("Titlu");
	optiuneFilter->addItem("Regizor");
	optiuneFilter->addItem("An");
	optiuneFilter->addItem("Actor");
	layoutFilter->addWidget(optiuneFilter);
	layoutFilter->addWidget(lineFilter);
	layoutFilter->addWidget(btnFilter);
	layoutOperatii->addLayout(layoutFilter);

	tableFilme = new QTableView;
	model = new FilmTableModel();
	tableFilme->setModel(model);
	lstOperatii = new QListWidget();
	layoutMain->addWidget(tableFilme);
	layoutMain->addWidget(lstOperatii);
	layoutMain->addLayout(layoutOperatii);
	setLayout(layoutMain);

	layoutButoaneDreapta->addWidget(btnRaportlitere);
	layoutButoaneDreapta->addWidget(btnCos);
	layoutButoaneDreapta->addWidget(btnCosGrafic);
	layoutMain->addLayout(layoutButoaneDreapta);
	widgetRaportLitere->setLayout(layoutRaportLitere);
}

void Gui::clear_lines() const
{
	/* Curata liniile de text */
	titlu->clear();
	regizor->clear();
	an->clear();
	actor->clear();
}


void Gui::connectSignalsAndSlots()
{
	/* Conecteaza semnalele cu sloturile corespunzatoare */
	QObject::connect(btnAdauga, &QPushButton::clicked, [&]() {
		try
		{
			string titluText = titlu->text().toStdString();
			string regizorText = regizor->text().toStdString();
			int anText = an->text().toInt();
			string actorText = actor->text().toStdString();
			service.adaugaFilm(titluText, regizorText, anText, actorText);
			QMessageBox::information(nullptr, "Adaugare", "Film adaugat cu succes!");
			reload();
			lstOperatii->addItem(QString::fromStdString("Adaugare: " + titluText + ", " + regizorText + ", " + std::to_string(anText) + ", " + actorText));
			cosGui->reload();
		}
		catch (const ValidareException& ex)
		{
			QMessageBox::warning(nullptr, "Eroare", QString::fromStdString(ex.what()));

		}
		catch (const DuplicatException& ex)
		{
			QMessageBox::warning(nullptr, "Eroare", QString::fromStdString(ex.what()));
		}
		clear_lines();
		});
	QObject::connect(btnSterge, &QPushButton::clicked, [&]() {
		try
		{
			string titluText = titlu->text().toStdString();
			string regizorText = regizor->text().toStdString();
			service.stergeFilm(titluText, regizorText);
			QMessageBox::information(nullptr, "Stergere", "Film sters cu succes!");
			reload();
			lstOperatii->addItem(QString::fromStdString("Stergere: " + titluText + ", " + regizorText));
			cosGui->reload();
		}
		catch (const NotFoundException& ex)
		{
			QMessageBox::warning(nullptr, "Eroare", QString::fromStdString(ex.what()));
		}
		clear_lines();
		});
	QObject::connect(btnModifica, &QPushButton::clicked, [&]() {
		try
		{
			string titluText = titlu->text().toStdString();
			string regizorText = regizor->text().toStdString();
			int anText = an->text().toInt();
			string actorText = actor->text().toStdString();
			service.modificaFilm(titluText, regizorText, titluText, regizorText, anText, actorText);
			QMessageBox::information(nullptr, "Modificare", "Film modificat cu succes!");
			reload();
			lstOperatii->addItem(QString::fromStdString("Modificare: " + titluText + ", " + regizorText + ", " + std::to_string(anText) + ", " + actorText));
		}
		catch (const NotFoundException& ex)
		{
			QMessageBox::warning(nullptr, "Eroare", QString::fromStdString(ex.what()));
		}
		catch (const ValidareException& ex)
		{
			QMessageBox::warning(nullptr, "Eroare", QString::fromStdString(ex.what()));
		}
		clear_lines();
		});
	QObject::connect(btnExit, &QPushButton::clicked, [&]() {
		QMessageBox::information(nullptr,"Exit","Ai parasit aplicatia!");
		close();
		});
	QObject::connect(btnSort, &QPushButton::clicked, [&]()
		{
			string optiune = optiuneSort->currentText().toStdString();
			string ordine = ordineSort->currentText().toStdString();
			vector<Film> filme = service.sorteazaFilme(optiune, ordine);
			lstOperatii->addItem(QString::fromStdString("Sortare: " + optiune + " " + ordine));
			load_filme(filme);
		});
	QObject::connect(btnUndo, &QPushButton::clicked, [&]() {
		try
		{
			service.undo();
			QMessageBox::information(nullptr, "Undo", "Operatie undo efectuata cu succes!");
			reload();
			lstOperatii->addItem(QString::fromStdString("Undo"));
			cosGui->reload();
		}
		catch (const InvalidCommandException& ex)
		{
			QMessageBox::warning(nullptr, "Eroare", QString::fromStdString(ex.what()));
		}
		});
	QObject::connect(btnFilter, &QPushButton::clicked, [&]() {
		string optiune = optiuneFilter->currentText().toStdString();
		string text = lineFilter->text().toStdString();
		vector<Film> filme = service.filtreazaFilme(optiune, text);
		load_filme(filme);

		lineFilter->clear();
		});
	QObject::connect(btnRaportlitere, &QPushButton::clicked, [&]()
		{
		widgetRaportLitere->setWindowTitle("Raport Filme");
		widgetRaportLitere->show();
		});
	QObject::connect(btnCos, &QPushButton::clicked, [&]()
		{
		cosGui->show();
		});
	QObject::connect(btnCosGrafic, &QPushButton::clicked, [&]()
		{
			cosReadOnlyGui.show();
		});
}

void Gui::reset_table() const
{
	/* Reseteaza tabelul de filme */
	for (int i = 0; i < tableFilme->model()->rowCount(); ++i)
	{
		tableFilme->model()->removeRow(i);
	}
}


void Gui::reload()
{
	/* Reincarca filmele in tabelul de filme dupa o modificare */
	vector<Film> filme = service.afiseazaFilme();
	load_filme(filme);
	removeLayout(layoutRaportLitere);
	layoutRaportLitere = new QVBoxLayout();
	widgetRaportLitere->setLayout(layoutRaportLitere);
	loadRaport();
}

void Gui::load_filme(const vector<Film>& filme) const
{
	/* Incarca un vector de filme in tabelul de filme
	 * parameter filme: lista de filme
	 */
	model->setFilme(filme);
}

void Gui::loadRaport()
{
	/* Incarca raportul in tabelul de filme */
	for (QPushButton* btn : btnRaport)
	{
		layoutRaportLitere->removeWidget(btn);
		btn->setParent(nullptr);
		delete btn;
	}
	btnRaport.clear();
	for (const auto& pair : service.grupareLitera())
	{
		QPushButton* btn = new QPushButton(QString::fromStdString(std::string(1, pair.first)));
		layoutRaportLitere->addWidget(btn);
		QObject::connect(btn, &QPushButton::clicked, [this, pair]() {
			int nr = pair.second;
			vector<StatDTO> raport = service.raportFilme();
			int an_min = 0, an_max = 0;
			for (const auto& stat : raport)
			{
				if (stat.get_litera() == pair.first)
				{
					an_min = stat.get_an_minim();
					an_max = stat.get_an_maxim();
					break;
				}
			}
			QMessageBox::information(nullptr, "Raport", QString::fromStdString("Numarul de filme care incep cu litera " + std::string(1, pair.first) + " este: " + std::to_string(nr) + "\n" + "Anul minim este: " + std::to_string(an_min) + "\n" + "Anul maxim este: " + std::to_string(an_max)));
			});
	}

}

void Gui::removeLayout(QLayout* layout)
{
	QLayoutItem* item;
	while ((item = layout->takeAt(0)) != nullptr)
	{
		if (QWidget* widget = item->widget())
		{
			widget->setParent(nullptr);
			delete widget;
		}
		delete item;
	}
	delete layout;
}
