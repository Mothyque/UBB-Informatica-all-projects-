#include "filmTableModel.h"
FilmTableModel::FilmTableModel(QObject* parent) : QAbstractTableModel(parent) {}

int FilmTableModel::rowCount(const QModelIndex& parent) const
{
	Q_UNUSED(parent)
	return static_cast<int>(filme.size());
}

int FilmTableModel::columnCount(const QModelIndex& parent) const
{
	Q_UNUSED(parent)
	return 4;
}

QVariant FilmTableModel::data(const QModelIndex& index, int role) const
{
	if (role == Qt::DisplayRole)
	{
		const Film& film = filme[index.row()];
		switch (index.column())
		{
		case 0:
			return QString::fromStdString(film.getTitlu());
		case 1:
			return QString::fromStdString(film.getRegizor());
		case 2:
			return QString::number(film.getAn());
		case 3:
			return QString::fromStdString(film.getActor());
		default:
			return QVariant();
		}
	}
	return QVariant();
}

QVariant FilmTableModel::headerData(int section, Qt::Orientation orientation, int role) const
{
	if (role == Qt::DisplayRole)
	{
		if (orientation == Qt::Horizontal)
		{
			switch (section)
			{
			case 0:
				return QString("Titlu");
			case 1:
				return QString("Regizor");
			case 2:
				return QString("An");
			case 3:
				return QString("Actor");
			default:
				return QVariant();
			}
		}
	}
	return QVariant();
}

void FilmTableModel::setFilme(const vector<Film>& newFilme)
{
	beginResetModel();
	filme = newFilme;
	endResetModel();
}

const Film& FilmTableModel::getFilm(int row) const
{
	return filme.at(row);
}