#pragma once
#include <QWidget>
#include <QTableView>
#include <QLineEdit>
#include <QPushButton>
#include <QComboBox>
#include <QMessageBox>

#include "DesenWidget.h"
#include "service.h"
#include "TractorTableModel.h"

class Gui : public QWidget
{
private:
	Service& service;
	QTableView* tableTractoare;
	TractorTableModel* model;
	QLineEdit* id;
	QLineEdit* nume;
	QLineEdit* tip;
	QLineEdit* nrRoti;
	QPushButton* btnAdd;
	QComboBox* tipuri;
	DesenWidget* desen;

public:
	Gui(Service& service);
	~Gui() = default;
	void initGui();
	void loadTable() const;
	void connectBtnSignals();
};

