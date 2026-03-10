#pragma once
#include "service.h"
#include <QListWidget>
#include <QPushButton>

class Gui : public QWidget
{
private:
	Service& service;
	QListWidget* listaDoctori;
	QLineEdit* cnpView;
	QPushButton* btnSectie;
	QPushButton* btnPrenume;
	QPushButton* btnToate;
	QPushButton* btnReload;
	QLineEdit* sectieFilter;
	QLineEdit* prenumeFilter;


public:
	Gui(Service& service);
	~Gui() = default;
	void initializeGui();
	void connectButtonsSignals();
	void loadTable(const vector<Doctor>& doctori) const;
};
