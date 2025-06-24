#include "Design.h"
#include <QPainter>

Design::Design(Service& service) : service(service) {}

void Design::update() 
{
	this->repaint();
}
 
void Design::paintEvent(QPaintEvent* ev) 
{
	QPainter p{ this };
	auto melodii = service.afiseazaMelodii();
	int fr[11] = { 0 };
	for (const auto& melodie : melodii)
	{
		fr[melodie.getRank()]++;
	}
	int x = 10, y = 10, w = 20;
	for (int i = 0; i < 11; ++i)
	{
		if (fr[i])
		{
			int h = 50 * fr[i];
			p.drawRect(x, y, w, h);
			x += 40;
		}
	}
}