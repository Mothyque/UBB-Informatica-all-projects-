#include "DesenWidget.h"
#include <QPainter>
#include <map>

DesenWidget::DesenWidget(Service& service) : service(service) {}

void DesenWidget::update()
{
	repaint();
}

void DesenWidget::paintEvent(QPaintEvent* event)
{
	QPainter p(this);
	std::map<string, int> genuriCnt;
	for (const auto& melodie : service.afiseazaMelodii())
	{
		genuriCnt[melodie.getGen()]++;
	}
	int padding = 50;
	QPoint centru(padding, padding);
	for (int i = 0; i < genuriCnt["Pop"]; i++)
	{
		int r = (i + 1) * 10;
		p.setPen(Qt::black);
		p.drawEllipse(centru, r, r);
	}
	centru.setX(width() - padding);
	for (int i = 0; i < genuriCnt["Rock"]; i++)
	{
		int r = (i + 1) * 10;
		p.setPen(Qt::black);
		p.drawEllipse(centru, r, r);
	}
	centru.setY(height() - padding);
	for (int i = 0; i < genuriCnt["Folk"]; i++)
	{
		int r = (i + 1) * 10;
		p.setPen(Qt::black);
		p.drawEllipse(centru, r, r);
	}
	centru.setX(padding);
	for (int i = 0; i < genuriCnt["Disco"]; i++)
	{
		int r = (i + 1) * 10;
		p.setPen(Qt::black);
		p.drawEllipse(centru, r, r);
	}
}

