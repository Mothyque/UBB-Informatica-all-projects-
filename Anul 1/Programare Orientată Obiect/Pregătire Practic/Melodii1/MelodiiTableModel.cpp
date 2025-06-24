#include "MelodiiTableModel.h"

MelodiiTableModel::MelodiiTableModel(const vector<Melodie>& melodii)
{
	this->melodii = melodii;
}

void MelodiiTableModel::setMelodii(const vector<Melodie>& m)
{
	this->melodii = m;
}

int MelodiiTableModel::rowCount(const QModelIndex& parent) const
{
	return static_cast<int>(melodii.size());
}

int MelodiiTableModel::columnCount(const QModelIndex& parent) const
{
	return 5;
}

QVariant MelodiiTableModel::data(const QModelIndex& index, int role) const
{
	if (role == Qt::DisplayRole)
	{
		const Melodie& melodie = melodii.at(index.row());
		switch (index.column())
		{
		case 0: return QString::number(melodie.getId());
		case 1: return QString::fromStdString(melodie.getTitlu());
		case 2: return QString::fromStdString(melodie.getArtist());
		case 3: return QString::number(melodie.getRank());
		case 4:
			{
				int cnt = 0;
				for (const auto& aux : melodii)
				{
					if (melodie.getRank() == aux.getRank())
					{
						cnt++;
					}
				}
				return QString::number(cnt);
			}
		}
	}
	return QVariant();
}

QVariant MelodiiTableModel::headerData(int section, Qt::Orientation orientation, int role) const
{
	if (role == Qt::DisplayRole && orientation == Qt::Horizontal)
	{
		switch (section)
		{
		case 0: return QString("Id");
		case 1: return QString("Titlu");
		case 2: return QString("Artist");
		case 3: return QString("Rank");
		case 4: return QString("Numar de piese cu acelasi rank");
		}
	}
	return QVariant();
}