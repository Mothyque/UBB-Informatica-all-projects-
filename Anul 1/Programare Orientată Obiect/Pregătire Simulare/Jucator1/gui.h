#pragma once
#include <QListWidget>

#include "service.h"
#include <QSpinBox>
#include <QPushButton>
#include <QCheckBox>
#include <QLineEdit>

class Gui : public QWidget
{
private:
	Service& service;
	QListWidget* listaJucatori;
	QLineEdit* nume;
	QLineEdit* tara;
	QSpinBox* ranking;
	QPushButton* adauga;
	QPushButton* sorteaza;
	QPushButton* reload;
	QCheckBox* qualify;

public:
	Gui(Service& service);
	~Gui() = default;
	void initGui();
	void connectBtnSlots();
	void loadTable(const vector<Jucator>& lista, const string& optiune) const;
};
