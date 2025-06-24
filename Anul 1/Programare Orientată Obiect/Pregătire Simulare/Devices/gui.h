#pragma once
#include <QWidget>
#include <QListWidget>
#include <QLineEdit>
#include <QPushButton>

#include "service.h"

class Gui : public QWidget
{
private:
	Service& service;
	QListWidget* listaDevices;
	QLineEdit* info;
	QPushButton* sortModel;
	QPushButton* sortPret;
	QPushButton* reload;

public:
	Gui(Service& service);
	~Gui() = default;
	void initGui();
	void connectSignalsSlots();
	void loadTable(const vector<Device>& lista) const;
};
