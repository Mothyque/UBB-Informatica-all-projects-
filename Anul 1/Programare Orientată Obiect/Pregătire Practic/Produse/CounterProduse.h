#pragma once
#include <QMessageBox>
#include <QWidget>

#include "Observer.h"
#include "Service.h"

using std::string;

class CounterProduse : public QWidget, public Observer
{
private:
	Service& service;
	string tip;
	QLabel* label;

public:
	CounterProduse(Service& serivce, const string& tip);
	~CounterProduse() override = default;
	void update() override;
	
};

