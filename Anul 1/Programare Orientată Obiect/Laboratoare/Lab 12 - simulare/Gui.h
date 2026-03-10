#pragma once
#include <QWidget>
#include <QListWidget>

#include "Service.h"
#include <QPushButton>

#include "Meci.h"

class Gui : public QWidget
{
private:
	Service& service;
	vector<Echipa> conferinta1;
	vector<Echipa> conferinta2;
	vector<Meci> meciuri1;
	vector<Meci> meciuri2;
	QListWidget* listaEchipe;
	QListWidget* rezultateConferinta1;
	QListWidget* rezultateConferinta2;
	QListWidget* echipeConferinta1;
	QListWidget* echipeConferinta2;
	QPushButton* btnGenereaza;
	QPushButton* btnJoaca;

public:
	Gui(Service& service);
	~Gui() = default;
	void initGui();
	void loadTable(const vector<Echipa>& echipe) const;
	void connectBtnSignals();
};

