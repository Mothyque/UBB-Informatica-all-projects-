#pragma once
#include <Service.h>
#include <QTableView>
#include <QLineEdit>
#include <QWidget>
#include <QPushButton>
#include "MelodieTableModel.h"
#include "DesenWidget.h"
class Gui : public QWidget
{
private:
	Service& service;
	QTableView* tableMelodii;
	MelodieTableModel* model;
	QLineEdit* id;
	QLineEdit* titlu;
	QLineEdit* artist;
	QLineEdit* gen;
	QPushButton* btnAdd;
	QPushButton* btnDelete;
	DesenWidget* desen;
public:
	Gui(Service& service);
	~Gui() = default;
	void initGui();
	void loadTable() const;
	void connectBtnSignals();
};

