#pragma once
#include "Service.h"
#include <QTableView>
#include <QPushButton>
#include <QLineEdit>
#include "MelodiiTableModel.h"

class Gui : public QWidget
{
private:
	Service& service;
	QTableView* tableMelodii;
	MelodiiTableModel* model;
	QLineEdit* titlu;
	QSlider* rank;
	QPushButton* btnModifica;
	QPushButton* btnSterge;

public:
	Gui(Service& service);
	~Gui() = default;
	void loadTable() const;
	void initGui();
	void connectBtnSignals();
};

