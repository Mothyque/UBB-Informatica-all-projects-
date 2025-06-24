#include "cosgui.h"

#include <QFormLayout>
#include <QVBoxLayout>
#include <QLineEdit>
#include <QMessageBox>
#include <QLabel>
#include <qtablewidget.h>
#include <inchiriere.h>

using std::exception;

void CosGUI::initGui()
{
	/* Initializeaza interfata grafica*/
	QHBoxLayout* layoutMain = new QHBoxLayout;
	QVBoxLayout* layoutOperatii = new QVBoxLayout;
	QFormLayout* layoutLineEdit = new QFormLayout;
	QHBoxLayout* layoutCrud = new QHBoxLayout;
	QFormLayout* layoutExportLines = new QFormLayout;
	QHBoxLayout* layoutExport = new QHBoxLayout;
	QHBoxLayout* layoutGenereaza = new QHBoxLayout;
	QHBoxLayout* layoutBtnExporta = new QHBoxLayout;
	tableCos = new QTableWidget();	
	btnAdauga = new QPushButton("Adauga Film");
	btnGoleste = new QPushButton("Goleste Cos");
	btnGenerare = new QPushButton("Genereaza Cos");
	numeFilm = new QLineEdit();
	numeRegizor = new QLineEdit();
	btnExporta = new QPushButton("Exporta Cos");
	numeFisierExporta = new QLineEdit();
	sliderValueLabel = new QLabel("1");
	slider = new QSlider(Qt::Horizontal);
	layoutMain->addWidget(tableCos);
	layoutLineEdit->addRow("Nume: ", numeFilm);
	layoutLineEdit->addRow("Regizor: ", numeRegizor);
	layoutOperatii->addLayout(layoutLineEdit);
	layoutCrud->addWidget(btnAdauga);
	layoutCrud->addWidget(btnGoleste);
	layoutOperatii->addLayout(layoutCrud);
	layoutGenereaza->addWidget(sliderValueLabel);
	layoutGenereaza->addWidget(slider);
	layoutGenereaza->addWidget(btnGenerare);
	layoutOperatii->addLayout(layoutGenereaza);
	layoutExportLines->addRow("Nume fisier: ", numeFisierExporta);
	layoutBtnExporta->addWidget(btnExporta);
	layoutExport->addLayout(layoutExportLines);
	layoutExport->addLayout(layoutBtnExporta);
	layoutOperatii->addLayout(layoutExport);
	layoutMain->addLayout(layoutOperatii);
	setLayout(layoutMain);
}
void CosGUI::connectSignalsAndSlots()
{
	QObject::connect(btnAdauga, &QPushButton::clicked, [&]() {
		try
		{
			string titluText = numeFilm->text().toStdString();
			string regizorText = numeRegizor->text().toStdString();
			cos.adaugaFilm(titluText, regizorText);
			QMessageBox::information(nullptr, "Adaugare", "Film adaugat cu succes!");
			reload();
		}
		catch (const exception& e)
		{
			QMessageBox::warning(nullptr, "Eroare", e.what());
		}
		numeFilm->clear();
		numeRegizor->clear();
		});
	QObject::connect(btnGoleste, &QPushButton::clicked, [&]() {
		cos.golesteCos();
		QMessageBox::information(nullptr, "Golire", "Cos golit cu succes!");
		reload();
		});
	QObject::connect(btnExporta, &QPushButton::clicked, [&]()
		{
		string fisier = numeFisierExporta->text().toStdString();
		fisier = fisier + ".csv";
		cos.exporta(fisier);
		cos.golesteCos();
		QMessageBox::information(nullptr, "Exportare", "Cos exportat cu succes in fisierul " + QString::fromStdString(fisier) + "!");
		});
	QObject::connect(btnGenerare, &QPushButton::clicked, [&]() {
		int nr = slider->value();
		try
		{
			cos.golesteCos();
			cos.genereazaCos(nr);
			QMessageBox::information(nullptr, "Generare", "Cos generat cu succes!");
			reload();
		}
		catch (const exception& e)
		{
			QMessageBox::warning(nullptr, "Eroare", e.what());
		}
		slider->setValue(1);
		numeFilm->clear();
		numeRegizor->clear();
		});
	QObject::connect(slider, &QSlider::valueChanged, [&](int value) {
		sliderValueLabel->setText(QString::number(value));
		});
}

CosGUI::CosGUI(CosInchiriere& cos, QWidget* parent)
	: QWidget(parent), cos{ cos }
{
	/* Constructorul clasei CosGUI
	 * parameter cos: referinta la obiectul de tip CosInchiriere
	 * parameter parent: pointer la obiectul parinte
	 */
	initGui();
	connectSignalsAndSlots();
	reload();
}

void CosGUI::reload()
{
	/* Reincarca filmele in tabelul de filme dupa o modificare */
	
	slider->setRange(1, (cos.nrFilmeRepo()));
	slider->setValue(1);
	slider->setSingleStep(1);	tableCos->clear();
	tableCos->setRowCount(0);
	tableCos->setColumnCount(4);
	tableCos->setColumnWidth(0, 200);
	tableCos->setColumnWidth(1, 200);
	tableCos->setColumnWidth(2, 50);
	tableCos->setColumnWidth(3, 200);
	tableCos->setHorizontalHeaderLabels({ "Titlu", "Regizor", "An", "Actor" });
	const vector<Film>& filme = cos.getFilme();
	for (const auto& film : filme)
	{
		int row = tableCos->rowCount();
		tableCos->insertRow(row);
		tableCos->setItem(row, 0, new QTableWidgetItem(QString::fromStdString(film.getTitlu())));
		tableCos->setItem(row, 1, new QTableWidgetItem(QString::fromStdString(film.getRegizor())));
		tableCos->setItem(row, 2, new QTableWidgetItem(QString::number(film.getAn())));
		tableCos->setItem(row, 3, new QTableWidgetItem(QString::fromStdString(film.getActor())));
	}
}