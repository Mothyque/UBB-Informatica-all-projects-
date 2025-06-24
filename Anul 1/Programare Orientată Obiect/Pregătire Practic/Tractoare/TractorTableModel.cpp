#include "TractorTableModel.h"
#include <QBrush>

TractorTableModel::TractorTableModel(const vector<Tractor>& tractoare)
{
	this->tractoare = tractoare;
}

void TractorTableModel::setTip(const string& tipNou)
{
	this->tip = tipNou;
}

void TractorTableModel::setTractoare(const vector<Tractor>& t)
{
	tractoare = t;
}

int TractorTableModel::rowCount(const QModelIndex& parent) const
{
	return static_cast<int>(tractoare.size());
}

int TractorTableModel::columnCount(const QModelIndex& parent) const
{
	return 5;
}

QVariant TractorTableModel::data(const QModelIndex& index, int role) const
{
	const Tractor& tractor = tractoare.at(index.row());
	if (role == Qt::DisplayRole)
	{
		switch (index.column())
		{
		case 0: return QString::number(tractor.getId());
		case 1: return QString::fromStdString(tractor.getNume());
		case 2: return QString::fromStdString(tractor.getTip());
		case 3: return QString::number(tractor.getNrRoti());
		case 4: 
		{
			int cnt = 0;
			for (const auto& tractorC : tractoare)
			{
				if (tractorC.getTip() == tractor.getTip())
				{
					cnt++;
				}
			}
			return cnt;
		}
		}
	}
	if (role == Qt::BackgroundRole)
	{
		if (tractor.getTip() == tip)
		{
			return QBrush(Qt::red);
		}
	}
	return QVariant();
}

QVariant TractorTableModel::headerData(int section, Qt::Orientation orientation, int role) const
{ 
	if (role == Qt::DisplayRole && orientation == Qt::Horizontal)
	{
		switch (section)
		{
		case 0: return "Id";
		case 1: return "Nume";
		case 2: return "Tip";
		case 3: return "Numar roti";
		case 4: return "Numar tip";
		}
	}
	return QVariant();
}