#pragma once
#include <QWidget>
#include <QPushButton>
#include <QListWidget>
#include <QLineEdit>
#include "service.h"

class Gui : public QWidget 
{
private:
	Service& service;
	QListWidget* listaProcesoare;
	QListWidget* listaPlaciDeBaza;
	QPushButton* adauga;
	QPushButton* filtreaza;
	QPushButton* reload;
	QPushButton* suma;
	QLineEdit* nume;
	QLineEdit* socluProcesor;
	QLineEdit* pret;
	QLineEdit* sumaL;

public:
	Gui(Service& service);
	~Gui() = default;
	void initGui();
	void connectBtnSignals();
	void loadTable(const vector<Procesor>& procesoare, const vector<PlacaDeBaza>& placiDeBaza) const;
};