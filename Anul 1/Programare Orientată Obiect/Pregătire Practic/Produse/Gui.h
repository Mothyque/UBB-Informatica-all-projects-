#pragma once
#include <QWidget>
#include <QTableView>
#include <QLineEdit>
#include <QPushButton>

#include "Service.h"
#include "ProdusTableModel.h"

class Gui : public QWidget
{
private:
	Service& service;
	QTableView* tableProduse;
	ProdusTableModel* model;
	QLineEdit* id;
	QLineEdit* nume;
	QLineEdit* tip;
	QLineEdit* pret;
	QPushButton* btnAdd;
	QSlider* filtrare;

public:
	Gui(Service& service);
	~Gui() = default;
	void initGui();
	void loadTable() const;
	void connectBtnSignals();
};

