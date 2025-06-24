#include "ProdusTableModel.h"
#include <QBrush>

ProdusTableModel::ProdusTableModel(const vector<Produs>& produse)
{
	this->produse = produse;
	this->sliderValue = 0;
}

void ProdusTableModel::setSliderValue(int newValue)
{
	this->sliderValue = newValue;
}

void ProdusTableModel::setProduse(const vector<Produs>& p)
{
	produse = p;
}

int ProdusTableModel::rowCount(const QModelIndex& parent) const 
{
	return static_cast<int>(produse.size());
}

int ProdusTableModel::columnCount(const QModelIndex& parent) const 
{
	return 5;
}

QVariant ProdusTableModel::data(const QModelIndex& index, int role) const 
{
	const Produs& produs = produse.at(index.row());
	if (role == Qt::DisplayRole)
	{
		switch (index.column())
		{
		case 0: return QString::number(produs.getId());
		case 1: return QString::fromStdString(produs.getNume());
		case 2: return QString::fromStdString(produs.getTip());
		case 3: return QString::number(produs.getPret());
		case 4: return QString::number(produs.getNrVocale());
		}
	}
	if (role == Qt::BackgroundRole)
	{
		if (produs.getPret() < sliderValue)
		{
			return QBrush(Qt::yellow);
		}
	}
	return QVariant();
}

QVariant ProdusTableModel::headerData(int section, Qt::Orientation orientation, int role) const 
{
	if (role == Qt::DisplayRole && orientation == Qt::Horizontal)
	{
		switch (section)
		{
		case 0: return QString("Id");
		case 1: return QString("Nume");
		case 2: return QString("Tip");
		case 3: return QString("Pret");
		case 4: return QString("Nr vocale");
		}
	}
	return QVariant();
}