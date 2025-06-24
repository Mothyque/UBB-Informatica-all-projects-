#pragma once
#include <QWidget>
#include "Observer.h"
#include "Service.h"

class Design : public QWidget, public Observer
{
private:
	Service& service;
public:
	Design(Service& service);
	~Design() = default;
	void update() override;
	void paintEvent(QPaintEvent* ev) override;
};

