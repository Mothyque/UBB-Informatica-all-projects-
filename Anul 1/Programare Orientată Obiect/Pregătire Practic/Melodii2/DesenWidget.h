#pragma once
#include <QWidget>
#include "Service.h"
class DesenWidget : public QWidget, public Observer
{
private:
	Service& service;
public:
	DesenWidget(Service& service);	
	~DesenWidget() = default;
	void update() override;
	void paintEvent(QPaintEvent* event) override;
}; 

