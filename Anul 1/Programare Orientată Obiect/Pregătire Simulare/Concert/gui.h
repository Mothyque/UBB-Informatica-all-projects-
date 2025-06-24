#pragma once
#include <service.h>
#include <QPushButton>
#include <QListWidget>
class Gui : public QWidget
{
private:
	Service& service;
	QListWidget* tableConcerte;
	QPushButton* btnModifica;
	QPushButton* btnSorteaza;
	QPushButton* btnCumpara;
	QPushButton* btnReload;
	QSlider* slider;


public:
	Gui(Service& service);
	~Gui() = default;
	void initGui();
	void connectSignalSlots();
	void loadTable(const vector<ConcertD>& concerte) const;
};