#pragma once
#include <QAbstractTableModel>
#include "Service.h"
class MelodieTableModel : public QAbstractTableModel
{
private:
	vector<Melodie> melodii;

public:
	MelodieTableModel(const vector<Melodie>& melodii);
	~MelodieTableModel() override = default;
	void setMelodii(const vector<Melodie>& m);
	int rowCount(const QModelIndex& parent = QModelIndex()) const override;
	int columnCount(const QModelIndex& parent = QModelIndex()) const override;
	QVariant data(const QModelIndex& index, int role = Qt::DisplayRole) const override;
	QVariant headerData(int section, Qt::Orientation orientation, int role = Qt::DisplayRole) const override;

};

