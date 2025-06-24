#pragma once
#include "Service.h"
#include "StudentTableModel.h"
#include <QWidget>
#include <QPushButton>
#include <QTableView>
class Gui : public QWidget
{
private:
	Service& service;
	QTableView* tableStudenti;
	StudentTableModel* model;
	QPushButton* btnImbatranire;
	QPushButton* btnIntinerire;
	QPushButton* btnUndo;
	QPushButton* btnRedo;
	QPushButton* btnSterge;
	vector<int> indexes;
public:
	Gui(Service& service);
	~Gui() = default;
	void initGui();
	void loadTable() const;
	void connectBtnSignals();
};

