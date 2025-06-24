#include "CounterProduse.h"
#include <QVBoxLayout>
#include <QLabel>
CounterProduse::CounterProduse(Service& service, const string& tip) : service(service), tip(tip)
{
	setWindowTitle(QString::fromStdString(tip));
	QVBoxLayout* layoutMain = new QVBoxLayout;
	int cnt = 0;
	for (const auto& produs : service.afiseazaProduse())
	{
		if (produs.getTip() == tip)
		{
			cnt++;
		}
	}
	label = new QLabel(QString::number(cnt));
	layoutMain->addWidget(label);
	setLayout(layoutMain);
	setMinimumSize(200, 100);
}

void CounterProduse::update() 
{
	int cnt = 0;
	for (const auto& produs : service.afiseazaProduse())
	{
		if (produs.getTip() == tip)
		{
			cnt++;
		}
	}
	label->setText(QString::number(cnt));
}