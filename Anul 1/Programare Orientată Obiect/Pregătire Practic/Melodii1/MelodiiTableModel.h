#pragma once
#include <QAbstractTableModel>
#include "Melodie.h"
#include <vector>

using std::vector;

class MelodiiTableModel : public QAbstractTableModel
{
private:
	vector<Melodie> melodii;
public:
	MelodiiTableModel(const vector<Melodie>& meoldii);
	void setMelodii(const vector<Melodie>& m);
	int rowCount(const QModelIndex& parent = QModelIndex()) const override;
	int columnCount(const QModelIndex& parent = QModelIndex()) const override;
	QVariant data(const QModelIndex& index, int role = Qt::DisplayRole) const override;
	QVariant headerData(int section, Qt::Orientation orientation, int role = Qt::DisplayRole) const override;
};

