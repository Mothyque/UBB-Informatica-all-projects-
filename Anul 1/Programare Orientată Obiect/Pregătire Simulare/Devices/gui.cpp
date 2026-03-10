#include "gui.h"
#include <QHBoxLayout>
#include <QListWidgetItem>

Gui::Gui(Service& service) : service(service)
{
	initGui();
	connectSignalsSlots();
	loadTable(service.afiseazaDevices());
}

void Gui::initGui()
{
	setWindowTitle("Devices");
	QHBoxLayout* layoutMain = new QHBoxLayout;
	listaDevices = new QListWidget;
	layoutMain->addWidget(listaDevices);
	QVBoxLayout* layoutDreapta = new QVBoxLayout;
	info = new QLineEdit;
	layoutDreapta->addWidget(info);
	QHBoxLayout* layoutBtn = new QHBoxLayout;
	sortModel = new QPushButton("Sorteaza dupa model");
	sortPret = new QPushButton("Sorteaza dupa pret");
	reload = new QPushButton("Reload");
	layoutBtn->addWidget(sortModel);
	layoutBtn->addWidget(sortPret);
	layoutBtn->addWidget(reload);
	layoutDreapta->addLayout(layoutBtn);
	layoutMain->addLayout(layoutDreapta);
	setLayout(layoutMain);
}

void Gui::loadTable(const vector<Device>& lista) const
{
	listaDevices->clear();
	for (const auto& device : lista)
	{
		QListWidgetItem* item = new QListWidgetItem(QString::fromStdString(device.getModel() + " - " + std::to_string(device.getPret())));
		if (device.getCuloare() == "negru")
		{
			item->setForeground(Qt::black);
		}
		else if (device.getCuloare() == "rosu")
		{
			item->setForeground(Qt::red);
		}
		else if (device.getCuloare() == "galben")
		{
			item->setForeground(Qt::yellow);
		}
		else if (device.getCuloare() == "albastru")
		{
			item->setForeground(Qt::blue);
		}
		listaDevices->addItem(item);
	}
}

void Gui::connectSignalsSlots()
{
	QObject::connect(reload, &QPushButton::clicked, [&]()
		{
			loadTable(service.afiseazaDevices());
		});
	QObject::connect(sortModel, &QPushButton::clicked, [&]()
		{
			loadTable(service.sorteazaDevices("model"));
		});
	QObject::connect(sortPret, &QPushButton::clicked, [&]()
		{
			loadTable(service.sorteazaDevices("pret"));
		});
	QObject::connect(listaDevices, &QListWidget::clicked, [&]()
		{
			QListWidgetItem* item = listaDevices->currentItem();
			QString text = item->text();
			QList lista = text.split(" - ");
			string model = lista.at(0).toStdString();
			string pretS = lista.at(1).toStdString();
			int pret = stoi(pretS);
			for (const auto& device : service.afiseazaDevices())
			{
				if (device.getModel() == model && device.getPret() == pret)
				{
					info->setText(QString::fromStdString(std::to_string(device.getAn()) + ", " + device.getCuloare()));
					break;
				}
			}
			
		});
}

