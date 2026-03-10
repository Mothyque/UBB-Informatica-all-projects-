#ifndef GUI_H
#define GUI_H

#include <qtablewidget.h>
#include "service.h"
#include <QPushButton>
#include <QComboBox>
#include <QVBoxLayout>

#include "cosgui.h"
#include <QListWidget>

#include "cosReadOnlyGui.h"
#include "filmTableModel.h"

class Gui : public QWidget
{
private:
	Service& service;
	CosInchiriere& cos;
	CosReadOnlyGui& cosReadOnlyGui;
	CosGUI* cosGui;
	QVBoxLayout* layoutRaportLitere;
	QWidget* widgetRaportLitere;
	QList<QPushButton*> btnRaport;
	QTableView* tableFilme;
	FilmTableModel* model;
	QListWidget* lstOperatii;
	QPushButton* btnAdauga;
	QPushButton* btnSterge;
	QPushButton* btnModifica;
	QPushButton* btnExit;
	QPushButton* btnSort;
	QPushButton* btnUndo;
	QPushButton* btnFilter;
	QPushButton* btnRaportlitere;
	QPushButton* btnCos;
	QPushButton* btnCosGrafic;
	QLineEdit* titlu;
	QLineEdit* regizor;
	QLineEdit* an;
	QLineEdit* actor;
	QLineEdit* lineFilter;
	QComboBox* optiuneSort;
	QComboBox* ordineSort;
	QComboBox* optiuneFilter;

	void initGui();
	void reload();
	void load_filme(const vector<Film>& filme) const;
	void connectSignalsAndSlots();
	void clear_lines() const;
	void reset_table() const;
	void loadRaport();
	void removeLayout(QLayout* layout);

public:
	Gui(Service& service, CosInchiriere& cos, CosReadOnlyGui& cosReadOnlyGui);
	~Gui() = default;
};

#endif
