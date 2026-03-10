#pragma once
#include "Service.h"
#include <QWidget>
#include <QTableWidget>
#include <QLabel>
#include <QPushButton>

class Gui : public QWidget
{
private:
	Service& service;
	QTableWidget* tableJocuri;
	QLabel* varstaRecomandata;
	QPushButton* btnSorteaza;
	QPushButton* btnFiltreaza;
	QPushButton* btnReload;

public:
	Gui(Service& service);
	~Gui() = default;
	void initGui();
	void loadTable(const vector<Joc>& jocuri) const;
	void connectBtnSignals() const;
};

