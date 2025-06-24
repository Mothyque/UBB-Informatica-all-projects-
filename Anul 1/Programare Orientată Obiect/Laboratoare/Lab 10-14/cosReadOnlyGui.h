#pragma once
#include <QWidget>

#include "inchiriere.h"
#include "observer.h"

class CosReadOnlyGui : public QWidget, public Observer
{
private:
	CosInchiriere& cos;

public:
	CosReadOnlyGui(CosInchiriere& cos);
	~CosReadOnlyGui() = default;
	void actualizeaza() override;
	void paintEvent(QPaintEvent* event) override;
};
