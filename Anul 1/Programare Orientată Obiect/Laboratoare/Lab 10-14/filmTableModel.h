#pragma once
#include <QAbstractTableModel>
#include <vector>

#include "film.h"

using std::vector;

class FilmTableModel: public QAbstractTableModel
{
	
private:
	vector<Film> filme;

public:
	FilmTableModel(QObject* parent = nullptr);
	int rowCount(const QModelIndex& parent = QModelIndex()) const override;
	int columnCount(const QModelIndex& parent = QModelIndex()) const override;
	QVariant data(const QModelIndex& index, int role = Qt::DisplayRole) const override;
	QVariant headerData(int section, Qt::Orientation orientation, int role = Qt::DisplayRole) const override;
	void setFilme(const vector<Film>& newFilme);
	const Film& getFilm(int row) const;
};
