#include "MelodieTableModel.h"

MelodieTableModel::MelodieTableModel(const vector<Melodie>& melodii)
{
	this->melodii = melodii;
}

void MelodieTableModel::setMelodii(const vector<Melodie>& m)
{
	melodii = m;
}

int MelodieTableModel::rowCount(const QModelIndex& parent) const
{
	return static_cast<int>(melodii.size());
}

int MelodieTableModel::columnCount(const QModelIndex& parent) const
{
	return 6;
}

QVariant MelodieTableModel::data(const QModelIndex& index, int role) const
{
	const Melodie& melodie = melodii.at(index.row());
	if (role == Qt::DisplayRole)
	{
		switch (index.column())
		{
		case 0: return QString::number(melodie.getId());
		case 1: return QString::fromStdString(melodie.getTitlu());
		case 2: return QString::fromStdString(melodie.getArtist());
		case 3: return QString::fromStdString(melodie.getGen());
		case 4:	
		{
			int cnt = 0;
			for (const auto& melo : melodii)
			{
				if (melo.getArtist() == melodie.getArtist())
				{
					cnt++;
				}
			}
			return cnt;
		}
		case 5: 
		{
			int cnt = 0;
			for (const auto& melo : melodii)
			{
				if (melo.getGen() == melodie.getGen())
				{
					cnt++;
				}
			}
			return cnt;
		}
		}
	}
	return QVariant();
}

QVariant MelodieTableModel::headerData(int section, Qt::Orientation orientation, int role) const
{
	if (role == Qt::DisplayRole && orientation == Qt::Horizontal)
	{
		switch (section)
		{
		case 0: return "Id";
		case 1: return "Titlu";
		case 2: return "Artist";
		case 3: return "Gen";
		case 4: return "Numar artist";
		case 5: return "Numar gen";
		}
	}
	return QVariant();
}